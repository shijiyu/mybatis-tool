package org.mybatis.generator.codegen.mybatis3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.internal.ObjectFactory;

import ibator.Globar;

public class IntrospectedTableMyBatis3Impl extends IntrospectedTable
{
  protected List<AbstractJavaGenerator> javaModelGenerators;
  protected List<AbstractJavaGenerator> clientGenerators;
  protected AbstractXmlGenerator xmlMapperGenerator;

  public IntrospectedTableMyBatis3Impl()
  {
    super(IntrospectedTable.TargetRuntime.MYBATIS3);
    this.javaModelGenerators = new ArrayList<>();
    this.clientGenerators = new ArrayList<>();
  }

  public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback)
  {
    calculateJavaModelGenerators(warnings, progressCallback);

    AbstractJavaClientGenerator javaClientGenerator = 
      calculateClientGenerators(warnings, progressCallback);

    calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
  }

  protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback)
  {
    if (javaClientGenerator == null)
      this.xmlMapperGenerator = new XMLMapperGenerator();
    else {
      this.xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
    }

    initializeAbstractGenerator(this.xmlMapperGenerator, warnings, 
      progressCallback);
  }

  protected AbstractJavaClientGenerator calculateClientGenerators(List<String> warnings, ProgressCallback progressCallback)
  {
    AbstractJavaClientGenerator javaGenerator = createJavaClientGenerator();
    if (javaGenerator == null) {
      return null;
    }

    initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
    this.clientGenerators.add(javaGenerator);

    return javaGenerator;
  }

  private AbstractJavaClientGenerator createJavaClientGenerator() {
    if (this.context.getJavaClientGeneratorConfiguration() == null) {
      return null;
    }

    String type = this.context.getJavaClientGeneratorConfiguration()
      .getConfigurationType();
    AbstractJavaClientGenerator javaGenerator;
    if ("XMLMAPPER".equalsIgnoreCase(type)) {
      javaGenerator = new JavaMapperGenerator();
    }
    else
    {
      if ("MIXEDMAPPER".equalsIgnoreCase(type)) {
        javaGenerator = new MixedClientGenerator();
      }
      else
      {
        if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) {
          javaGenerator = new AnnotatedClientGenerator();
        }
        else
        {
          if ("MAPPER".equalsIgnoreCase(type))
            javaGenerator = new JavaMapperGenerator();
          else
            javaGenerator = (AbstractJavaClientGenerator)
              ObjectFactory.createInternalObject(type); 
        }
      }
    }
    return javaGenerator;
  }

  protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback)
  {
    if ((!Globar.ISSIMPLE) && (getRules().generateExampleClass())) {
      AbstractJavaGenerator javaGenerator = new ExampleGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, 
        progressCallback);
      this.javaModelGenerators.add(javaGenerator);
    }

    if (getRules().generatePrimaryKeyClass()) {
      AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, 
        progressCallback);
      this.javaModelGenerators.add(javaGenerator);
    }

    if (getRules().generateBaseRecordClass()) {
      AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, 
        progressCallback);
      this.javaModelGenerators.add(javaGenerator);
    }

    if (getRules().generateRecordWithBLOBsClass()) {
      AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, 
        progressCallback);
      this.javaModelGenerators.add(javaGenerator);
    }
  }

  protected void initializeAbstractGenerator(AbstractGenerator abstractGenerator, List<String> warnings, ProgressCallback progressCallback)
  {
    if (abstractGenerator == null) {
      return;
    }

    abstractGenerator.setContext(this.context);
    abstractGenerator.setIntrospectedTable(this);
    abstractGenerator.setProgressCallback(progressCallback);
    abstractGenerator.setWarnings(warnings);
  }

  public List<GeneratedJavaFile> getGeneratedJavaFiles()
  { //FIXME 修改过的地方 后续来做修改
    List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();
    Iterator<CompilationUnit> localIterator2;
    for (Iterator<AbstractJavaGenerator> localIterator1 = this.javaModelGenerators.iterator(); localIterator1.hasNext(); )
    {
      AbstractJavaGenerator javaGenerator = (AbstractJavaGenerator)localIterator1.next();
      List<CompilationUnit> compilationUnits = javaGenerator
        .getCompilationUnits();
      localIterator2 = compilationUnits.iterator(); 
      for(;localIterator2.hasNext();)
      {
				CompilationUnit compilationUnit = (CompilationUnit) localIterator2.next();
				GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
						this.context.getJavaModelGeneratorConfiguration().getTargetProject());
				gjf.setIntrospectedTable(this);
				answer.add(gjf);
      }
    }
    Iterator<AbstractJavaGenerator> localIterator1;
    for (localIterator1 = this.clientGenerators.iterator(); localIterator1.hasNext(); )
    {
      AbstractJavaGenerator javaGenerator = (AbstractJavaGenerator)localIterator1.next();
      List<CompilationUnit> compilationUnits = javaGenerator
        .getCompilationUnits();
       
      for(localIterator2 = compilationUnits.iterator();localIterator2.hasNext();){
				CompilationUnit compilationUnit = (CompilationUnit) localIterator2.next();
				GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
						this.context.getJavaClientGeneratorConfiguration().getTargetProject());
				gjf.setIntrospectedTable(this);
				answer.add(gjf);
      }
    }

    return answer;
  }

  public List<GeneratedXmlFile> getGeneratedXmlFiles()
  {
    List<GeneratedXmlFile> answer = new ArrayList<>();

    if (this.xmlMapperGenerator != null) {
      Document document = this.xmlMapperGenerator.getDocument();
      GeneratedXmlFile gxf = new GeneratedXmlFile(document, 
        getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(), 
        this.context.getSqlMapGeneratorConfiguration().getTargetProject(), 
        true);
      if (this.context.getPlugins().sqlMapGenerated(gxf, this)) {
        answer.add(gxf);
      }
    }

    return answer;
  }

  public int getGenerationSteps()
  {
    return this.javaModelGenerators.size() + this.clientGenerators.size() + (
      this.xmlMapperGenerator == null ? 0 : 1);
  }

  public boolean isJava5Targeted()
  {
    return true;
  }

  public boolean requiresXMLGenerator()
  {
    AbstractJavaClientGenerator javaClientGenerator = 
      createJavaClientGenerator();

    if (javaClientGenerator == null) {
      return true;
    }
    return javaClientGenerator.requiresXMLGenerator();
  }
}