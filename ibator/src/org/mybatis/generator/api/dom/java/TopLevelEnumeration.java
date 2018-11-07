package org.mybatis.generator.api.dom.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.internal.util.messages.Messages;

public class TopLevelEnumeration extends InnerEnum
  implements CompilationUnit
{
  private Set<FullyQualifiedJavaType> importedTypes;
  private Set<String> staticImports;
  private List<String> fileCommentLines;

  public TopLevelEnumeration(FullyQualifiedJavaType type)
  {
    super(type);
    this.importedTypes = new TreeSet<>();
    this.fileCommentLines = new ArrayList<>();
    this.staticImports = new TreeSet<>();
  }

  public String getFormattedContent() {
    StringBuilder sb = new StringBuilder();

    for (String fileCommentLine : this.fileCommentLines) {
      sb.append(fileCommentLine);
      OutputUtilities.newLine(sb);
    }

    if ((getType().getPackageName() != null) && 
      (getType().getPackageName().length() > 0)) {
      sb.append("package ");
      sb.append(getType().getPackageName());
      sb.append(';');
      OutputUtilities.newLine(sb);
      OutputUtilities.newLine(sb);
    }

    for (String staticImport : this.staticImports) {
      sb.append("import static ");
      sb.append(staticImport);
      sb.append(';');
      OutputUtilities.newLine(sb);
    }

    if (this.staticImports.size() > 0) {
      OutputUtilities.newLine(sb);
    }

    Set<String> importStrings = OutputUtilities.calculateImports(this.importedTypes);
    for (String importString : importStrings) {
      sb.append(importString);
      OutputUtilities.newLine(sb);
    }

    if (importStrings.size() > 0) {
      OutputUtilities.newLine(sb);
    }

    sb.append(super.getFormattedContent(0));

    return sb.toString();
  }

  public Set<FullyQualifiedJavaType> getImportedTypes() {
    return Collections.unmodifiableSet(this.importedTypes);
  }

  public FullyQualifiedJavaType getSuperClass() {
    throw new UnsupportedOperationException(Messages.getString("RuntimeError.11"));
  }

  public boolean isJavaInterface() {
    return false;
  }

  public boolean isJavaEnumeration() {
    return true;
  }

  public void addImportedType(FullyQualifiedJavaType importedType) {
    if ((importedType.isExplicitlyImported()) && 
      (!importedType.getPackageName().equals(
      getType().getPackageName())))
      this.importedTypes.add(importedType);
  }

  public void addFileCommentLine(String commentLine)
  {
    this.fileCommentLines.add(commentLine);
  }

  public List<String> getFileCommentLines() {
    return this.fileCommentLines;
  }

  public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {
    this.importedTypes.addAll(importedTypes);
  }

  public Set<String> getStaticImports() {
    return this.staticImports;
  }

  public void addStaticImport(String staticImport) {
    this.staticImports.add(staticImport);
  }

  public void addStaticImports(Set<String> staticImports) {
    this.staticImports.addAll(staticImports);
  }
}