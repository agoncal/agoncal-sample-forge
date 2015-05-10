package org.agoncal.sample.forge.roaster;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
public class Servlet
{

   public static void main(String[] args)
   {
       String[] urlPatterns = {"myServlet", "myPattern2"};

      final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
      javaClassSource.setPackage("org.agoncal.myproj").setName("MyServlet").setSuperType(HttpServlet.class)
               .addAnnotation(WebServlet.class).setStringArrayValue("urlPatterns", urlPatterns);

      createMethod(javaClassSource, "doPut");
      createMethod(javaClassSource, "doGet");

      System.out.println(javaClassSource);
   }

   private static void createMethod(JavaClassSource javaClassSource, String methodName)
   {
      MethodSource<?> doGet = javaClassSource.addMethod().setProtected().setName(methodName).setReturnTypeVoid();
      doGet.addParameter(HttpServletRequest.class, "request");
      doGet.addParameter(HttpServletResponse.class, "response");
      doGet.addThrows(ServletException.class).addThrows(IOException.class);
      doGet.setBody("response.getWriter().println(\"" + methodName + "\");").addAnnotation(Override.class);
   }
}
