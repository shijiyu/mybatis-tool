package ibator.generator;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import ibator.Globar;
import ibator.util.ProjectFileWriteUtil;
public class ServiceInterfaceGenarator {
	private String serviceClassName;
	private IntrospectedTable introspectedTable;
	private String srcDir;
	
	public boolean createServiceWithSpring(IProject project, String pojo,IntrospectedTable introspectedTable) {
		//String daoName = pojo + Globar.daoName;
		String className = getPackage()+"."+pojo+"Service";
		StringBuffer sb = new StringBuffer();
		TopLevelClass topLevelClass = new TopLevelClass(className);
		// topLevelClass.addAnnotation("@Service");
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setJavaInterface(true);
        this.serviceClassName = className;
        this.introspectedTable = introspectedTable;
       
		//topLevelClass.addImportedType("org.springframework.stereotype.Service");
		//topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
		// topLevelClass.addImportedType("java.util.Map");
		// topLevelClass.addImportedType("java.util.HashMap");
		//topLevelClass.addImportedType("java.util.List");
		//topLevelClass.addImportedType("org.apache.ibatis.session.RowBounds");

	/*	sb.setLength(0);
		sb.append(Globar.daoPath).append(".").append(daoName);
		topLevelClass.addImportedType(sb.toString());*/

		sb.setLength(0);
		sb.append(Globar.pojoPath).append(".").append("PageBean");
		topLevelClass.addImportedType(sb.toString());

		/*Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		sb.setLength(0);
		sb.append(daoName);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		field.setName(sb.toString());
		sb.setLength(0);
		sb.append(Globar.daoPath).append(".").append(daoName);
		field.setType(new FullyQualifiedJavaType(sb.toString()));
		field.addAnnotation("@Autowired");
		topLevelClass.addField(field);*/
		sb.setLength(0);
		sb.append(Globar.pojoPath).append(".").append(pojo);
		sb.append(Globar.exampleName);
		topLevelClass.addImportedType(sb.toString());

		String pojoWithBLOBs = introspectedTable.hasBLOBColumns() ? (pojo + "WithBLOBs") : pojo;
		if (introspectedTable.hasBLOBColumns()) {
			sb.setLength(0);
			sb.append(Globar.pojoPath).append(".").append(pojoWithBLOBs);
			topLevelClass.addImportedType(sb.toString());
		} else {
			sb.setLength(0);
			sb.append(Globar.pojoPath).append(".").append(pojo);
			topLevelClass.addImportedType(sb.toString());
		}
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		sb.setLength(0);
		sb.append("insert"+pojoWithBLOBs);
		method.setName(sb.toString());
		method.setReturnType(new FullyQualifiedJavaType("int"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoWithBLOBs), "record"));

		
		/*sb.setLength(0);
		sb.append(daoName);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		sb.append(".insertSelective(record);");
		method.addBodyLine(sb.toString());*/
		topLevelClass.addMethod(method);
		
		//update方法
		if(introspectedTable.hasPrimaryKeyColumns()){
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		sb.setLength(0);
		sb.append("update"+pojoWithBLOBs);
		method.setName(sb.toString());
		method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoWithBLOBs), "record"));
		method.setReturnType(new FullyQualifiedJavaType(pojoWithBLOBs));
		topLevelClass.addMethod(method);
		
		
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		sb.setLength(0);
		sb.append("delete"+pojoWithBLOBs+"Bykey");
		method.setName(sb.toString());
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "id"));
		method.setReturnType(new FullyQualifiedJavaType("int"));
		topLevelClass.addMethod(method);
		
		
		
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		sb.setLength(0);
		sb.append("get"+pojoWithBLOBs+"ByKey");
		method.setName(sb.toString());
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "id"));
		method.setReturnType(new FullyQualifiedJavaType(pojoWithBLOBs));
		topLevelClass.addMethod(method);
		}

		method = new Method();
		method.setName("queryByPage");
		method.setVisibility(JavaVisibility.PUBLIC);

		sb.setLength(0);
		sb.append(Globar.daoPath).append(".").append("PageBean");

		method.setReturnType(new FullyQualifiedJavaType(sb.toString()));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "page"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "size"));
		sb.setLength(0);
		sb.append(Globar.pojoPath).append(".").append(pojo).append(Globar.exampleName);
		method.addParameter(new Parameter(new FullyQualifiedJavaType(sb.toString()), Globar.exampleName.toLowerCase()));
		/*
		method.addBodyLine("//record sum");
		method.addBodyLine("int sum = " + field.getName() + ".countBy" + Globar.exampleName + "(example);");
		method.addBodyLine("//page count");
		method.addBodyLine("int count = sum%size==0 ? sum/size : sum/size+1;");
		method.addBodyLine("//check page");
		method.addBodyLine("page = page<1 ? 1 : ((page>count)? count : page);");
		method.addBodyLine("//query");
		method.addBodyLine("List<" + pojo + "> list = " + field.getName() + ".selectBy" + Globar.exampleName
				+ "AndPage(example, new RowBounds((page-1)*size, size));");
		method.addBodyLine("//save to PageBean ");
		method.addBodyLine("PageBean pageBean = new PageBean();");
		method.addBodyLine("pageBean.setCurrentPage(page);");
		method.addBodyLine("pageBean.setPageCount(count);");
		method.addBodyLine("pageBean.setRecordCount(sum);");
		method.addBodyLine("pageBean.setResultList(list);");
		method.addBodyLine("pageBean.setCurrentPage(page);");
		method.addBodyLine("pageBean.setPageSize(size);");
		method.addBodyLine("return pageBean;");*/
		topLevelClass.addMethod(method);
		return createFile(className,project,topLevelClass);
	}

	public boolean createFile(String className,IProject project,TopLevelClass topLevelClass){
		String filename = srcDir+ File.separator + className.replaceAll("\\.", "/") + ".java";
		return ProjectFileWriteUtil.write(project, topLevelClass, filename);
	}

	public String getPackage() {
		String packageName = Globar.daoPath;
		packageName = packageName.substring(0, packageName.lastIndexOf("."));
		String servicePackageName = packageName + ".service";
		return servicePackageName;
	}
	public String getServiceClassName() {
		return serviceClassName;
	}

	public IntrospectedTable getIntrospectedTable() {
		return introspectedTable;
	}

	public void setIntrospectedTable(IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
	}

	public String getSrcDir() {
		return srcDir;
	}

	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}


}