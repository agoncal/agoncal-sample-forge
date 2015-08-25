package org.agoncal.sample.forge.roaster;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.server.ServerEndpoint;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 * 
 *         https://issues.jboss.org/browse/FORGE-2397
 */
public class WebSocketServerEndpoint
{

   public static void main(String[] args)
   {

      List<WebSocketMethods> methods = new ArrayList<>();
      Collections.addAll(methods, WebSocketMethods.ON_CLOSE, WebSocketMethods.ON_MESSAGE, WebSocketMethods.ON_OPEN,
               WebSocketMethods.ON_ERROR);

      String uri = "/myWebSocket";

      final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
      javaClassSource.setPackage("org.agoncal.myproj").setName("MyWebSocketEndpoint");

      javaClassSource.addAnnotation(ServerEndpoint.class).setStringValue(uri);

      // Methods
      for (WebSocketMethods method : methods)
      {
         MethodSource<?> wsMethod = javaClassSource.addMethod().setPublic()
                  .setName(className2FieldName(method.getAnnotation()))
                  .setReturnTypeVoid().setBody("");
         wsMethod.addAnnotation(method.getAnnotation());

         Class[] parameters = method.getParameters();
         for (Class parameter : parameters)
         {
            wsMethod.addParameter(parameter, className2FieldName(parameter));
         }
      }

      System.out.println(javaClassSource);
   }

   private static String className2FieldName(Class<? extends Annotation> clazz)
   {
      String className = clazz.getSimpleName();
      return className.replaceFirst(Character.toString(className.charAt(0)),
               Character.toString(className.charAt(0)).toLowerCase());
   }
}
