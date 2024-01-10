//package io.github.architers.context.autocode;
//
//import com.google.auto.service.AutoService;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.*;
//import javax.lang.model.type.TypeMirror;
//import javax.tools.Diagnostic;
//import java.io.IOException;
//import java.io.Writer;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @author luyi
// */
//@Slf4j
//@SupportedAnnotationTypes("io.github.architers.context.autocode.*")
//@SupportedSourceVersion(SourceVersion.RELEASE_11)
//@AutoService(Processor.class)
//public class AutoSqlFieldProcessor extends AbstractProcessor {
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        //获取所有被CustomAnnotation修饰的代码元素
//        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(AutoSqlField.class);
//        for (Element element : elements) {
//            try {
//                this.generateFile(element);
//            } catch (IOException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return false;
//    }
//
//    private void generateFile(Element element) throws IOException, ClassNotFoundException {
//        TypeMirror typeMirror = element.asType();
//        String sourceClass = typeMirror.toString();
//        int index = sourceClass.lastIndexOf(".");
//        String className = sourceClass.substring(index + 1) + "Fields";
//        String packageName = sourceClass.substring(0, index);
//        StringBuilder builder = new StringBuilder()
//                .append("package ").append(packageName).append(";\n\n")
//                .append("/**\n")
//                .append(" * ").append(element.getSimpleName()).append("属性字段\n ")
//                .append(" *\n")
//                .append(" * @author luyi\n")
//                .append(" * @version 1.0.0\n")
//                .append(" * @see ").append(sourceClass).append("\n")
//                .append(" */\n")
//                .append("public interface ").append(className).append(" {").append("\n\n");
//
//        for (Element enclosedElement : element.getEnclosedElements()) {
//            if (enclosedElement.getKind() == ElementKind.FIELD) {
//                builder.append("    String ").append(enclosedElement.getSimpleName()).append(" = \"")
//                        .append(this.getReturnValue(enclosedElement.getSimpleName())).append("\";\n");
//            }
//        }
//        builder.append("}");
//        String name = packageName + "." + className + "";
//        messager.printMessage(Diagnostic.Kind.NOTE, "generateFile:" + name);
//        Writer writer = filer.createSourceFile(name).openWriter();
//        writer.append(builder);
//        writer.flush();
//        writer.close();
//
//
//    }
//
//    private String getReturnValue(Name simpleName) {
//        Pattern compile = Pattern.compile("[A-Z]");
//        Matcher matcher = compile.matcher(simpleName);
//        StringBuilder sb = new StringBuilder();
//        while (matcher.find()) {
//            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
//        }
//        matcher.appendTail(sb);
//        return sb.toString();
//    }
//
//
//    @Override
//    public Set<String> getSupportedOptions() {
//        return super.getSupportedOptions();
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return super.getSupportedAnnotationTypes();
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.RELEASE_11;
//    }
//
//    private Messager messager;
//
//    private Filer filer;
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        messager = processingEnv.getMessager();
//        filer = processingEnv.getFiler();
//
//    }
//
//    @Override
//    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
//        return super.getCompletions(element, annotation, member, userText);
//    }
//
//}
