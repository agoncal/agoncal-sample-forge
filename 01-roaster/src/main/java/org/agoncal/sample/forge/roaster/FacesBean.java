package org.agoncal.sample.forge.roaster;

import javax.inject.Inject;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
public class FacesBean
{

   public static void main(String[] args)
   {

      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
      javaClass.setPackage("org.agoncal.myproj").setName("MyJSFBean").addAnnotation(Inject.class);

      System.out.println(javaClass);
   }
}
