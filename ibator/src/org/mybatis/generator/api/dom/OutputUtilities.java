package org.mybatis.generator.api.dom;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class OutputUtilities {

	private static String ls;
	static {
		ls = System.getProperty("line.separator");
		if (ls == null)
			ls = "\n";

	}
	private static final String lineSeparator = ls;

	public static void javaIndent(StringBuilder sb, int indentLevel) {
		for (int i = 0; i < indentLevel; i++)
			sb.append("    ");
	}

	public static void xmlIndent(StringBuilder sb, int indentLevel) {
		for (int i = 0; i < indentLevel; i++)
			sb.append("  ");
	}

	public static void newLine(StringBuilder sb) {
		sb.append(lineSeparator);
	}

	public static Set<String> calculateImports(Set<FullyQualifiedJavaType> importedTypes) {
		StringBuilder sb = new StringBuilder();
		Set<String> importStrings = new TreeSet<>();
		Iterator<String> localIterator2;
		for (Iterator<FullyQualifiedJavaType> localIterator1 = importedTypes.iterator(); localIterator1.hasNext();) {
			FullyQualifiedJavaType fqjt = (FullyQualifiedJavaType) localIterator1.next();
			localIterator2 = fqjt.getImportList().iterator();
			for (; localIterator2.hasNext();) {

				String importString = (String) localIterator2.next();
				sb.setLength(0);
				sb.append("import ");
				sb.append(importString);
				sb.append(';');
				importStrings.add(sb.toString());
			}
		}

		return importStrings;
	}
}