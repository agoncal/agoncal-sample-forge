package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
public class ServletFilter
{

   public static void main(String[] args)
   {
      final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
      javaClassSource.setPackage("org.agoncal.myproj").setName("MyFilter").addInterface(Filter.class)
               .addAnnotation(WebFilter.class);

      javaClassSource.addField("private static final long serialVersionUID = -1L;");



      MethodSource<?> init = javaClassSource.addMethod().setPublic().setName("init").setReturnTypeVoid();
      init.addParameter(FilterConfig.class, "filterConfig");
      init.addThrows(ServletException.class);
      init.setBody("").addAnnotation(Override.class);

      MethodSource<?> doFilter = javaClassSource.addMethod().setPublic().setName("doFilter").setReturnTypeVoid();
      doFilter.addParameter(ServletRequest.class, "request");
      doFilter.addParameter(ServletResponse.class, "response");
      doFilter.addParameter(FilterChain.class, "chain");
      doFilter.addThrows(IOException.class);
      doFilter.addThrows(ServletException.class);
      doFilter.setBody("").addAnnotation(Override.class);


      MethodSource<?> destroy = javaClassSource.addMethod().setPublic().setName("destroy").setReturnTypeVoid();
      destroy.setBody("").addAnnotation(Override.class);
      System.out.println(javaClassSource);
   }

}
