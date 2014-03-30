package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaAnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.Serializable;
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
public class BeanValidationValidator {

    public static void main(String[] args) {

        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setName("MaxValidatorForString");
        javaClass.addInterface(ConstraintValidator.class);
        javaClass.addInterface("ConstraintValidator<Max, Number>");
        javaClass.addMethod().setPublic().setName("initialize").setReturnTypeVoid().setParameters("Max constraint").setBody("").addAnnotation(Override.class);
        javaClass.addMethod().setPublic().setName("isValid").setReturnType("boolean").setParameters("Number value, ConstraintValidatorContext context").setBody("return false;").addAnnotation(Override.class);
        System.out.println(javaClass);
    }

}
