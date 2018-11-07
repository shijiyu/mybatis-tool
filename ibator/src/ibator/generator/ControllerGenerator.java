package ibator.generator;

import org.eclipse.core.resources.IProject;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import ibator.Globar;

public class ControllerGenerator {
      public boolean createServiceWithSpring(IProject project, String pojo,ServiceInterfaceGenarator serviceInterfaceGenarator){
  		String className = getPackage()+"."+pojo+"Controller";
  		StringBuffer sb = new StringBuffer();
  		TopLevelClass topLevelClass = new TopLevelClass(className);
  		topLevelClass.addAnnotation("@Controller()");
  		topLevelClass.addAnnotation("@RequestMapping(value=\"/"+pojo.toLowerCase()+"\")");
  		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
  		
  		//topLevelClass.addImportedType("org.springframework.stereotype.Service");
  		topLevelClass.addImportedType("org.springframework.stereotype.Controller");
  		topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
  		topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMethod");
  		topLevelClass.addImportedType("org.springframework.web.bind.annotation.ResponseBody");
  		topLevelClass.addImportedType("javax.annotation.Resource");
  		//topLevelClass.addImportedType("java.util.List");
  		//topLevelClass.addImportedType("org.apache.ibatis.session.RowBounds");

  		//sb.setLength(0);
  		//sb.append(Globar.daoPath).append(".").append(daoName);
  		//topLevelClass.addImportedType(sb.toString());
  		
  		
  		String resultVo = "ResultVo";
  		sb.setLength(0);
  		sb.append(Globar.pojoPath).append(".").append("PageBean");
  		topLevelClass.addImportedType(sb.toString());
  		sb.setLength(0);
  		sb.append(Globar.pojoPath).append(".").append(resultVo);
  		topLevelClass.addImportedType(sb.toString());
  		
  	    sb.setLength(0);
	    sb.append(Globar.pojoPath).append(".").append(pojo);
	    sb.append(Globar.exampleName);
	    topLevelClass.addImportedType(sb.toString());
  		
  		String pojoWithBLOBs = serviceInterfaceGenarator.getIntrospectedTable().hasBLOBColumns()?(pojo+"WithBLOBs"):pojo;
  		if(serviceInterfaceGenarator.getIntrospectedTable().hasBLOBColumns())
	     {     
  			  sb.setLength(0);
		      sb.append(Globar.pojoPath).append(".").append(pojoWithBLOBs);
		      topLevelClass.addImportedType(sb.toString());
	     }else{
	    	 sb.setLength(0);
	   		 sb.append(Globar.pojoPath).append(".").append(pojo);
	   		 topLevelClass.addImportedType(sb.toString());
	     }
  		//增加service引用
  		topLevelClass.addImportedType(serviceInterfaceGenarator.getServiceClassName());
  		
  		String filedName;
  		
  		Field field = new Field();
  		field.setVisibility(JavaVisibility.PRIVATE);
  		sb.setLength(0);
  		sb.append(pojo+"Service");
  		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
  		field.setName(sb.toString());
  		filedName = field.getName();
  		//sb.setLength(0);
  		//sb.append(Globar.daoPath).append(".").append(daoName);
  		field.setType(new FullyQualifiedJavaType(serviceInterfaceGenarator.getServiceClassName()));
  		field.addAnnotation("@Resource");
  		topLevelClass.addField(field);


	
  		Method method = new Method();
  		method.setVisibility(JavaVisibility.PUBLIC);
  		sb.setLength(0);
  		sb.append("insert").append(pojoWithBLOBs);
  		method.setName(sb.toString());
  		method.addAnnotation("@RequestMapping(value=\"/doAdd\",method=RequestMethod.POST)");
  		method.addAnnotation("@ResponseBody");
  		method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoWithBLOBs), "record"));
  		sb.setLength(0);
  		sb.append(Globar.daoPath).append(".").append(resultVo);
  		method.setReturnType(new FullyQualifiedJavaType(sb.toString()));
  		
  		setResutlVo(method,resultVo);
  		
  		sb.setLength(0);
  		sb.append("vo.setResult(");
  		sb.append(filedName);
  		//sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
  		sb.append(".insert"+pojoWithBLOBs+"(record));");
  		method.addBodyLine(sb.toString());
  		sb.setLength(0);
  		sb.append("return vo;");
  		method.addBodyLine(sb.toString());
  		
  		topLevelClass.addMethod(method);
  		if(serviceInterfaceGenarator.getIntrospectedTable().hasPrimaryKeyColumns()){
  		   //update方法
			method = new Method();
			method.setVisibility(JavaVisibility.PUBLIC);
			method.addAnnotation("@RequestMapping(value=\"/doUpdate\",method=RequestMethod.POST)");
	  		method.addAnnotation("@ResponseBody");
			sb.setLength(0);
			sb.append("update"+pojoWithBLOBs);
			method.setName(sb.toString());
			method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoWithBLOBs), "record"));
			sb.setLength(0);
	  		sb.append(Globar.daoPath).append(".").append(resultVo);
	  		method.setReturnType(new FullyQualifiedJavaType(sb.toString()));
			setResutlVo(method,resultVo);
			
			sb.setLength(0);
			sb.append(field.getName());
			sb.append(".update"+pojoWithBLOBs+"(record);");
			method.addBodyLine(sb.toString());
			method.addBodyLine("return vo;");
			topLevelClass.addMethod(method);
			
			method = new Method();
			method.setVisibility(JavaVisibility.PUBLIC);
			method.addAnnotation("@RequestMapping(value=\"/doDelete\",method=RequestMethod.POST)");
	  		method.addAnnotation("@ResponseBody");
			sb.setLength(0);
			sb.append("delete"+pojoWithBLOBs+"Bykey");
			method.setName(sb.toString());
			method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "id"));
			sb.setLength(0);
	  		sb.append(Globar.daoPath).append(".").append(resultVo);
	  		method.setReturnType(new FullyQualifiedJavaType(sb.toString()));
	  		
			setResutlVo(method,resultVo);
	  		
			sb.setLength(0);
			sb.append("vo.setResult(").append(field.getName());
			sb.append(".delete"+pojoWithBLOBs+"Bykey(id));");
			method.addBodyLine(sb.toString());
			method.addBodyLine("return vo;");
			topLevelClass.addMethod(method);
			
			method = new Method();
			method.setVisibility(JavaVisibility.PUBLIC);
			method.addAnnotation("@RequestMapping(value=\"/doDetail\",method=RequestMethod.GET)");
	  		method.addAnnotation("@ResponseBody");
			sb.setLength(0);
			sb.append("get"+pojoWithBLOBs+"ByKey");
			method.setName(sb.toString());
			method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "id"));
			sb.setLength(0);
	  		sb.append(Globar.daoPath).append(".").append(resultVo);
	  		method.setReturnType(new FullyQualifiedJavaType(sb.toString()));
	  		
			setResutlVo(method,resultVo);
	  
			sb.setLength(0);
			sb.append("vo.setResult(").append(field.getName());
			sb.append(".get"+pojoWithBLOBs+"ByKey(id));");
			method.addBodyLine(sb.toString());
			method.addBodyLine("return vo;");
			topLevelClass.addMethod(method);	
  		}
  		
  		method = new Method();
  		method.setName("queryByPage");
  		method.addAnnotation("@RequestMapping(value=\"/doSearch\",method=RequestMethod.GET)");
  		method.addAnnotation("@ResponseBody");
  		method.setVisibility(JavaVisibility.PUBLIC);
  		sb.setLength(0);
  		sb.append(Globar.daoPath).append(".").append(resultVo);
  		method.setReturnType(new FullyQualifiedJavaType(sb.toString()));
  		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "page"));
  		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "size"));
  		sb.setLength(0);
  		sb.append(Globar.pojoPath).append(".").append(pojo).append(Globar.exampleName);
  		method.addParameter(new Parameter(new FullyQualifiedJavaType(sb.toString()), Globar.exampleName.toLowerCase()));
		setResutlVo(method,resultVo);
  		sb.setLength(0);
  		sb.append(filedName);
  		//sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
  		sb.append(".queryByPage(page,size,"+Globar.exampleName.toLowerCase()+");");
  		method.addBodyLine("PageBean pageBean = "+ sb.toString());
  		method.addBodyLine("vo.setResult(pageBean);");
		method.addBodyLine("return vo;");
		
  		topLevelClass.addMethod(method);
  		
  		return serviceInterfaceGenarator.createFile(className, project, topLevelClass);
      }
      private void setResutlVo(	Method method,String resultVo){
    		StringBuffer sb = new StringBuffer();
    		sb.append(resultVo);
    		sb.append(" vo = new ");
    		sb.append(resultVo).append("();");
    		method.addBodyLine(sb.toString());
    		sb.setLength(0);
    		sb.append("vo.setCode(").append(resultVo).append(".SUCCESSFUL);");
    		method.addBodyLine(sb.toString());
      }
  	public String getPackage() {
		String packageName = Globar.daoPath;
		packageName = packageName.substring(0, packageName.lastIndexOf("."));
		String servicePackageName = packageName + ".controller";
		return servicePackageName;
	}
}
