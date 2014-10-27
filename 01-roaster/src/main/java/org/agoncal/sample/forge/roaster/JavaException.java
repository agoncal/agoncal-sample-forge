package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class JavaException {

    public static void main(String[] args) {
        final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
        javaClassSource.setPackage("org.agoncal.myproj").setName("MyException").setSuperType(Exception.class);
        // Methods
        javaClassSource.addMethod().setPublic().setConstructor(true).setBody("super();");
        javaClassSource.addMethod().setPublic().setConstructor(true).setParameters("String message").setBody("super(message);");

        System.out.println(javaClassSource);

    }
}
