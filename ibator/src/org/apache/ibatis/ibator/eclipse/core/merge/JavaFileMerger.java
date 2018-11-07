package org.apache.ibatis.ibator.eclipse.core.merge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

public class JavaFileMerger
{
  private String newJavaSource;
  private String existingFilePath;
  private String[] javaDocTags;

  public JavaFileMerger(String newJavaSource, String existingFilePath, String[] javaDocTags)
  {
    this.newJavaSource = newJavaSource;
    this.existingFilePath = existingFilePath;
    this.javaDocTags = javaDocTags;
  }

  @SuppressWarnings("unchecked")
public String getMergedSource() throws RuntimeException
  {
    ASTParser astParser = ASTParser.newParser(3);
    NewJavaFileVisitor newJavaFileVisitor = visitNewJavaFile(astParser);

    String existingFile = getExistingFileContents();
    IDocument document = new Document(existingFile);

    ExistingJavaFileVisitor visitor = new ExistingJavaFileVisitor(this.javaDocTags);

    astParser.setSource(existingFile.toCharArray());
    CompilationUnit cu = (CompilationUnit)astParser.createAST(null);
    AST ast = cu.getAST();
    cu.recordModifications();
    cu.accept(visitor);

    TypeDeclaration typeDeclaration = visitor.getTypeDeclaration();
    if (typeDeclaration == null) {
      StringBuffer sb = new StringBuffer();
      sb.append("No types defined in the file ");
      sb.append(this.existingFilePath);

      throw new RuntimeException(sb.toString());
    }

    List<Type> newSuperInterfaces = getNewSuperInterfaces(
      typeDeclaration.superInterfaceTypes(), newJavaFileVisitor);
    for (Type newSuperInterface : newSuperInterfaces)
      if (newSuperInterface.isSimpleType()) {
        SimpleType st = (SimpleType)newSuperInterface;
        Name name = ast.newName(st.getName().getFullyQualifiedName());
        SimpleType newSt = ast.newSimpleType(name);
        typeDeclaration.superInterfaceTypes().add(newSt);
      }
      else {
        throw new RuntimeException("The Java file merger only supports simple types as super interfaces");
      }
    SimpleType newSt;
    if (newJavaFileVisitor.getSuperclass() != null) {
      if (newJavaFileVisitor.getSuperclass().isSimpleType()) {
        SimpleType st = (SimpleType)newJavaFileVisitor.getSuperclass();
        Name name = ast.newName(st.getName().getFullyQualifiedName());
        newSt = ast.newSimpleType(name);
        typeDeclaration.setSuperclassType(newSt);
      }
      else {
        throw new RuntimeException("The Java file merger only supports simple types as super classes");
      }
    }
    else typeDeclaration.setSuperclassType(null);

    if (newJavaFileVisitor.isInterface())
      typeDeclaration.setInterface(true);
    else {
      typeDeclaration.setInterface(false);
    }

    List<ImportDeclaration> newImports = getNewImports(cu.imports(), newJavaFileVisitor);
    for (ImportDeclaration newImport : newImports) {
      Name name = ast.newName(newImport.getName().getFullyQualifiedName());
      ImportDeclaration newId = ast.newImportDeclaration();
      newId.setName(name);
      cu.imports().add(newId);
    }

    TextEdit textEdit = cu.rewrite(document, null);
    try {
      textEdit.apply(document);
    } catch (BadLocationException e) {
      throw new RuntimeException(
        "BadLocationException removing prior fields and methods");
    }

    astParser.setSource(document.get().toCharArray());
    CompilationUnit strippedCu = (CompilationUnit)astParser.createAST(null);

    TypeDeclaration topLevelType = null;
    Iterator<?> iter = strippedCu.types().iterator();
    while (iter.hasNext()) {
      TypeDeclaration td = (TypeDeclaration)iter.next();
      if ((td.getParent().equals(strippedCu)) && 
        ((td.getModifiers() & 0x1) > 0)) {
        topLevelType = td;
        break;
      }

    }

    ASTRewrite rewrite = ASTRewrite.create(topLevelType.getRoot().getAST());
    ListRewrite listRewrite = rewrite.getListRewrite(topLevelType, 
      TypeDeclaration.BODY_DECLARATIONS_PROPERTY);

    Iterator<?> astIter = newJavaFileVisitor.getNewNodes().iterator();
    int i = 0;
    while (astIter.hasNext()) {
      listRewrite.insertAt((ASTNode)astIter.next(), i++, null);
    }

    textEdit = rewrite.rewriteAST(document, JavaCore.getOptions());
    try {
      textEdit.apply(document);
    } catch (BadLocationException e) {
      throw new RuntimeException(
        "BadLocationException adding new fields and methods");
    }

    String newSource = document.get();
    return newSource;
  }

  private List<Type> getNewSuperInterfaces(List<Type> existingSuperInterfaces, NewJavaFileVisitor newJavaFileVisitor)
  {
    List<Type> answer = new ArrayList<>();

    for (Type newSuperInterface : newJavaFileVisitor.getSuperInterfaceTypes()) {
      if (newSuperInterface.isSimpleType()) {
        SimpleType newSimpleType = (SimpleType)newSuperInterface;
        String newName = newSimpleType.getName().getFullyQualifiedName();

        boolean found = false;
        for (Type existingSuperInterface : existingSuperInterfaces) {
          if (existingSuperInterface.isSimpleType()) {
            SimpleType existingSimpleType = (SimpleType)existingSuperInterface;

            String existingName = existingSimpleType.getName().getFullyQualifiedName();

            if (newName.equals(existingName)) {
              found = true;
              break;
            }
          }
        }

        if (!found) {
          answer.add(newSuperInterface);
        }
      }
    }

    return answer;
  }

  private List<ImportDeclaration> getNewImports(List<ImportDeclaration> existingImports, NewJavaFileVisitor newJavaFileVisitor)
  {
    List<ImportDeclaration> answer = new ArrayList<>();

    for (ImportDeclaration newImport : newJavaFileVisitor.getImports()) {
      String newName = newImport.getName().getFullyQualifiedName();
      boolean found = false;
      for (ImportDeclaration existingImport : existingImports) {
        String existingName = existingImport.getName().getFullyQualifiedName();

        if (newName.equals(existingName)) {
          found = true;
          break;
        }
      }

      if (!found) {
        answer.add(newImport);
      }
    }

    return answer;
  }

  private NewJavaFileVisitor visitNewJavaFile(ASTParser astParser)
  {
    astParser.setSource(this.newJavaSource.toCharArray());
    CompilationUnit cu = (CompilationUnit)astParser.createAST(null);
    NewJavaFileVisitor newVisitor = new NewJavaFileVisitor();
    cu.accept(newVisitor);

    return newVisitor;
  }

  private String getExistingFileContents() throws RuntimeException {
    File file = new File(this.existingFilePath);

    if (!file.exists())
    {
      StringBuilder sb = new StringBuilder();
      sb.append("The file ");
      sb.append(this.existingFilePath);
      sb.append(" does not exist");
      throw new RuntimeException(sb.toString());
    }
    try
    {
      StringBuilder sb = new StringBuilder();
      BufferedReader br = new BufferedReader(new FileReader(file));
      char[] buffer = new char[1024];
      int returnedBytes = br.read(buffer);
      while (returnedBytes != -1) {
        sb.append(buffer, 0, returnedBytes);
        returnedBytes = br.read(buffer);
      }

      br.close();
      return sb.toString();
    } catch (IOException e) {
      StringBuilder sb = new StringBuilder();
      sb.append("IOException reading the file ");
      sb.append(this.existingFilePath);
      throw new RuntimeException(sb.toString(), e);
    }
  }
}