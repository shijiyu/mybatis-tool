package ibator.generator;

import org.eclipse.core.resources.IProject;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import ibator.Globar;

public class ServiceImplGenerator extends ServiceInterfaceGenarator {
	public boolean createServiceWithSpring(IProject project, String pojo,IntrospectedTable introspectedTable) {
		String daoName = pojo + Globar.daoName;
		String className = getPackage()+"."+pojo+"ServiceImpl";
		StringBuffer sb = new StringBuffer();
		TopLevelClass topLevelClass = new TopLevelClass(className);
		topLevelClass.addAnnotation("@Service");
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.addImportedType("org.springframework.stereotype.Service");
		topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
		// topLevelClass.addImportedType("java.util.Map");
		// topLevelClass.addImportedType("java.util.HashMap");
		topLevelClass.addImportedType("java.util.List");
		topLevelClass.addImportedType("org.apache.ibatis.session.RowBounds");

		sb.setLength(0);
		sb.append(Globar.daoPath).append(".").append(daoName);
		topLevelClass.addImportedType(sb.toString());

		sb.setLength(0);
		sb.append(Globar.pojoPath).append(".").append("PageBean");
		topLevelClass.addImportedType(sb.toString());

		
	
		//增加实现接口
		FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(super.getPackage()+"."+pojo+"Service");
		if (superInterface != null) {
		     topLevelClass.addSuperInterface(superInterface);
		     topLevelClass.addImportedType(superInterface);
		}

		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		sb.setLength(0);
		sb.append(daoName);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		field.setName(sb.toString());
		sb.setLength(0);
		sb.append(Globar.daoPath).append(".").append(daoName);
		field.setType(new FullyQualifiedJavaType(sb.toString()));
		field.addAnnotation("@Autowired");
		topLevelClass.addField(field);
		
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
		method.addAnnotation("@Override");
		method.setReturnType(new FullyQualifiedJavaType("int"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoWithBLOBs), "record"));

		sb.setLength(0);
		sb.append("return ");
		sb.append(field.getName());
		sb.append(".insertSelective(record);");
		method.addBodyLine(sb.toString());
		topLevelClass.addMethod(method);
		if(introspectedTable.hasPrimaryKeyColumns()){
			//update方法
			method = new Method();
			method.setVisibility(JavaVisibility.PUBLIC);
			sb.setLength(0);
			sb.append("update"+pojoWithBLOBs);
			method.setName(sb.toString());
			method.addAnnotation("@Override");
			method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoWithBLOBs), "record"));
			method.setReturnType(new FullyQualifiedJavaType(pojoWithBLOBs));
			sb.setLength(0);
			sb.append(field.getName());
			sb.append(".updateByPrimaryKeySelective(record);");
			method.addBodyLine(sb.toString());
			method.addBodyLine("return record;");
			topLevelClass.addMethod(method);
			
			
			method = new Method();
			method.setVisibility(JavaVisibility.PUBLIC);
			method.addAnnotation("@Override");
			sb.setLength(0);
			sb.append("delete"+pojoWithBLOBs+"Bykey");
			method.setName(sb.toString());
			method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "id"));
			method.setReturnType(new FullyQualifiedJavaType("int"));
			sb.setLength(0);
			sb.append("return "+field.getName());
			sb.append(".deleteByPrimaryKey(id);");
			method.addBodyLine(sb.toString());
			topLevelClass.addMethod(method);
			
			method = new Method();
			method.setVisibility(JavaVisibility.PUBLIC);
			method.addAnnotation("@Override");
			sb.setLength(0);
			sb.append("get"+pojoWithBLOBs+"ByKey");
			method.setName(sb.toString());
			method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "id"));
			method.setReturnType(new FullyQualifiedJavaType(pojoWithBLOBs));
			sb.setLength(0);
			sb.append("return "+field.getName());
			sb.append(".selectByPrimaryKey(id);");
			method.addBodyLine(sb.toString());
			topLevelClass.addMethod(method);
				
		}

		method = new Method();
		method.setName("queryByPage");
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);

		sb.setLength(0);
		sb.append(Globar.daoPath).append(".").append("PageBean");

		method.setReturnType(new FullyQualifiedJavaType(sb.toString()));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "page"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "size"));
		sb.setLength(0);
		sb.append(Globar.pojoPath).append(".").append(pojo).append(Globar.exampleName);
		method.addParameter(new Parameter(new FullyQualifiedJavaType(sb.toString()), Globar.exampleName.toLowerCase()));
		method.addBodyLine("//record sum");
		method.addBodyLine("int sum = " + field.getName() + ".countBy" + Globar.exampleName + "("+Globar.exampleName.toLowerCase()+");");
		method.addBodyLine("//page count");
		method.addBodyLine("int count = sum%size==0 ? sum/size : sum/size+1;");
		method.addBodyLine("//check page");
		method.addBodyLine("page = page<1 ? 1 : ((page>count)? count : page);");
		method.addBodyLine("//query");
		method.addBodyLine("List<" + pojoWithBLOBs + "> list = " + field.getName() + ".selectBy" + Globar.exampleName+(introspectedTable.hasBLOBColumns()?"WithBLOBs":"")
				+ "AndPage("+Globar.exampleName.toLowerCase()+", new RowBounds((page-1)*size, size));");
		method.addBodyLine("//save to PageBean ");
		method.addBodyLine("PageBean pageBean = new PageBean();");
		method.addBodyLine("pageBean.setCurrentPage(page);");
		method.addBodyLine("pageBean.setPageCount(count);");
		method.addBodyLine("pageBean.setRecordCount(sum);");
		method.addBodyLine("pageBean.setResultList(list);");
		method.addBodyLine("pageBean.setCurrentPage(page);");
		method.addBodyLine("pageBean.setPageSize(size);");
		method.addBodyLine("return pageBean;");
		topLevelClass.addMethod(method);
		return createFile(className,project,topLevelClass);
	}

	public String getPackage() {
		String packageName = Globar.daoPath;
		packageName = packageName.substring(0, packageName.lastIndexOf("."));
		String serviceImplPackageName = packageName + ".service.impl";
		return serviceImplPackageName;
	}

}