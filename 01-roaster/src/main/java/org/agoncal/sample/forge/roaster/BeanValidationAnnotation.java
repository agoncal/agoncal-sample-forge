package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaAnnotationSource;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class BeanValidationAnnotation {

    public static void main(String[] args) {

        // This is the inner annotation List
        final JavaAnnotationSource listNestedAnnotation = Roaster.create(JavaAnnotationSource.class);
        listNestedAnnotation.setName("List");
        listNestedAnnotation.addAnnotation(Retention.class).setEnumValue(RUNTIME);
        listNestedAnnotation.addAnnotation(Target.class).setEnumValue(METHOD, FIELD, PARAMETER, TYPE);
        listNestedAnnotation.addAnnotationElement("Email[] value()");

        // This is the annotation @Email
        final JavaAnnotationSource emailAnnotation = Roaster.create(JavaAnnotationSource.class);
        emailAnnotation.setPackage("org.agoncal.proj.constraints").setName("Email");
        emailAnnotation.addImport(Payload.class);
        emailAnnotation.addAnnotation(Documented.class);
        emailAnnotation.addAnnotation(Retention.class).setEnumValue(RUNTIME);
        emailAnnotation.addAnnotation(Target.class).setEnumValue(METHOD, FIELD, PARAMETER, TYPE);
        emailAnnotation.addAnnotation(Constraint.class).setLiteralValue("validatedBy", "{}");
        emailAnnotation.addAnnotationElement("String message() default \"wrong email address\"");
        emailAnnotation.addAnnotationElement("Class<?>[] groups() default {}");
        emailAnnotation.addAnnotationElement("Class<? extends Payload>[] payload() default {}");
        emailAnnotation.addNestedType(listNestedAnnotation);

        // I was expecting to have a method like that so I could add annotation inside another one
        // emailAnnotation.addAnnotationElement(emailAnnotation);

        System.out.println(emailAnnotation);

    }
}
