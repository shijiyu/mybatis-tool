package org.apache.ibatis.ibator.eclipse.core.merge;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ExistingJavaFileVisitor extends ASTVisitor
{
  private TypeDeclaration typeDeclaration;
  private String[] javadocTags;

  public ExistingJavaFileVisitor(String[] javadocTags)
  {
    this.javadocTags = javadocTags;
  }

  public boolean visit(FieldDeclaration node)
  {
    if (isIbatorGenerated(node)) {
      node.delete();
    }

    return false;
  }

  public boolean visit(MethodDeclaration node)
  {
    if (isIbatorGenerated(node)) {
      node.delete();
    }

    return false;
  }

  public boolean visit(TypeDeclaration node)
  {
    if (node.getParent().getNodeType() == 15) {
      this.typeDeclaration = node;
      return true;
    }

    if (isIbatorGenerated(node)) {
      node.delete();
    }

    return false;
  }

  public TypeDeclaration getTypeDeclaration()
  {
    return this.typeDeclaration;
  }

  private boolean isIbatorGenerated(BodyDeclaration node)
  {
    boolean rc = false;
    Javadoc jd = node.getJavadoc();
    if (jd != null) {
      @SuppressWarnings("unchecked")
	List<TagElement> tags = jd.tags();
      for (TagElement tag : tags) {
        String tagName = tag.getTagName();
        if (tagName != null)
        {
          for (String javadocTag : this.javadocTags) {
            if (tagName.equals(javadocTag)) {
              rc = true;
              break;
            }
          }
        }
      }
    }
    return rc;
  }
}