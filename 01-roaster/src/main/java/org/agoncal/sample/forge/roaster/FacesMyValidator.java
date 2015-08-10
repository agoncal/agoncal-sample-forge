package org.agoncal.sample.forge.roaster;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
public class FacesMyValidator
{

   public static void main(String[] args)
   {

      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
      javaClass.setPackage("org.agoncal.myproj").setName("MyJSFValidator3").addInterface(Validator.class);
      javaClass.addImport(FacesMessage.class);
      javaClass.addAnnotation(FacesValidator.class).setStringValue("org.agoncal.myproj" + "." + "MyJSFValidator2");

      MethodSource<?> validateMethod = javaClass.addMethod().setPublic().setName("validate").setReturnTypeVoid();
      validateMethod.addThrows(ValidatorException.class);
      validateMethod.addParameter(FacesContext.class, "context").setFinal(true);
      validateMethod.addParameter(UIComponent.class, "component").setFinal(true);
      validateMethod.addParameter(Object.class, "value").setFinal(true);
      validateMethod.setBody("throw new ValidatorException(new FacesMessage(\"Validator not yet implemented.\"));")
               .addAnnotation(Override.class);

      System.out.println(javaClass);
   }
}
