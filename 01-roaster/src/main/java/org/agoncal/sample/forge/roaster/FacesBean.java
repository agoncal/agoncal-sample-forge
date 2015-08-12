package org.agoncal.sample.forge.roaster;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
public class FacesBean
{

   public static void main(String[] args)
   {

      final JavaClassSource source = Roaster.create(JavaClassSource.class);
      source.setPackage("org.agoncal.myproj").setName("MyJSFBean").addAnnotation(Named.class);

      BeanScope scopedValue = BeanScope.REQUEST;

      if (BeanScope.DEPENDENT != scopedValue)
      {
         source.addAnnotation(scopedValue.getAnnotation());
         if (scopedValue.isSerializable())
         {
            source.addInterface(Serializable.class);
            source.addField().setPrivate().setStatic(true).setFinal(true).setName("serialVersionUID").setType("long")
                     .setLiteralInitializer("1L");

            if (BeanScope.CONVERSATION == scopedValue)
            {
               // Field
               source.addField().setName("conversation").setType(Conversation.class).addAnnotation(Inject.class);
               // Methods
               source.addMethod().setPublic().setName("begin").setReturnType(String.class)
                        .setBody("conversation.begin();\n" + "return null;");
               source.addMethod().setPublic().setName("end").setReturnType(String.class)
                        .setBody("conversation.end();\n" + "return null;");
            }

         }
      }

      System.out.println(source);
   }
}
