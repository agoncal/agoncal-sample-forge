package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class BeanValidationValidator {

    public static void main(String[] args) {

        String constraintAnnotation = "Max";
        String dataType = "Number";

        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setName("MaxValidatorForString");
        javaClass.addImport(ConstraintValidator.class);
        javaClass.addInterface("ConstraintValidator<" + constraintAnnotation + ", " + dataType + ">");

        MethodSource<?> initializeMethod = javaClass.addMethod().setPublic().setName("initialize").setReturnTypeVoid();
        initializeMethod.addParameter(Max.class, "constraint");
        initializeMethod.setBody("").addAnnotation(Override.class);

        MethodSource<?> isValidMethod = javaClass.addMethod().setPublic().setName("isValid").setReturnType(boolean.class);
        isValidMethod.addParameter(Number.class, "value");
        isValidMethod.addParameter(ConstraintValidatorContext.class, "context");
        isValidMethod.setBody("return false;").addAnnotation(Override.class);

        System.out.println(javaClass);
    }

}
