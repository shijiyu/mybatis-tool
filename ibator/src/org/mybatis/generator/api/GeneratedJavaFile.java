package org.mybatis.generator.api;

import org.mybatis.generator.api.dom.java.CompilationUnit;

public class GeneratedJavaFile extends GeneratedFile {
	private CompilationUnit compilationUnit;

	private IntrospectedTable introspectedTable;

	public GeneratedJavaFile(CompilationUnit compilationUnit, String targetProject) {
		super(targetProject);
		this.compilationUnit = compilationUnit;
	}

	public String getFormattedContent() {
		return this.compilationUnit.getFormattedContent();
	}

	public String getFileName() {
		String shortName = this.compilationUnit.getType().getShortName();

		return shortName + ".java";
	}

	public String getTargetPackage() {
		return this.compilationUnit.getType().getPackageName();
	}

	public CompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}

	public boolean isMergeable() {
		return true;
	}

	public IntrospectedTable getIntrospectedTable() {
		return introspectedTable;
	}

	public void setIntrospectedTable(IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
	}
}