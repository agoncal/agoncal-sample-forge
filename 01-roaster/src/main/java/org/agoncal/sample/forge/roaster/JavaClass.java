package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class JavaClass {

    public static void main(String[] args) {
        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage("org.agoncal.myproj").setName("MyClass").addTypeVariable().setName("T");;
        javaClass.addInterface(Serializable.class);
        javaClass.addImport("org.agoncal.myproj.constraints.Email");

        // Simple Fields
        javaClass.addField().setPrivate().setName("email").setType(String.class).setFinal(true).addAnnotation("Email");
        javaClass.addField().setPrivate().setName("emails").setType("java.util.List<String>").setLiteralInitializer("new java.util.ArrayList<>()");

        // FieldSource Fields
        FieldSource<?> comments = javaClass.addField().setPrivate().setName("comments");
        comments.setType("java.util.List<String>").setLiteralInitializer("new java.util.ArrayList<String>()");

        // Simple Methods
        javaClass.addMethod().setPublic().setName("doSomething").setReturnTypeVoid().setParameters("String toto").setBody("return null;").addAnnotation(Documented.class);

        // MethodSource Methods
        MethodSource<?> isValidMethod = javaClass.addMethod().setPublic().setName("isValid").setReturnType(boolean.class);
        isValidMethod.addParameter(Number.class, "value");
        isValidMethod.addParameter(ConstraintValidatorContext.class, "context");
        isValidMethod.setBody("return false;").addAnnotation(Override.class);

        System.out.println(javaClass);

    }
}
