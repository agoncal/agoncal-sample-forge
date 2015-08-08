package org.agoncal.sample.forge.roaster;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
public class FacesMyConverter
{

   public static void main(String[] args)
   {

      final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
      javaClassSource.setPackage("org.agoncal.myproj").setName("MyJSFConverter2").addInterface(Converter.class)
               .addAnnotation(FacesConverter.class);
      // Methods

      MethodSource<?> getAsObject = javaClassSource.addMethod().setPublic().setName("getAsObject")
               .setReturnType(Object.class);
      getAsObject.addParameter(FacesContext.class, "context").setFinal(true);
      getAsObject.addParameter(UIComponent.class, "component").setFinal(true);
      getAsObject.addParameter(String.class, "value").setFinal(true);
      getAsObject.setBody("throw new UnsupportedOperationException(\"not yet implemented\");")
               .addAnnotation(Override.class);

      MethodSource<?> getAsString = javaClassSource.addMethod().setPublic().setName("getAsString")
               .setReturnType(String.class);
      getAsString.addParameter(FacesContext.class, "context").setFinal(true);
      getAsString.addParameter(UIComponent.class, "component").setFinal(true);
      getAsString.addParameter(Object.class, "value").setFinal(true);
      getAsString.setBody("return value.toString();")
               .addAnnotation(Override.class);

      System.out.println(javaClassSource);
   }
}
