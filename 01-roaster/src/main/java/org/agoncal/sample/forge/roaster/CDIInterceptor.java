package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class CDIInterceptor {

    public static void main(String[] args) {

        String interceptorBinding = "java.io.Serializable";

        final JavaClassSource interceptor = Roaster.create(JavaClassSource.class);
        interceptor.addImport(interceptorBinding);
        interceptor.addAnnotation(interceptorBinding);
        interceptor.addAnnotation(Interceptor.class);
        interceptor.addImport(InvocationContext.class);
        interceptor.addMethod().setName("intercept").setParameters("InvocationContext ic").setReturnType(Object.class)
                .setPrivate()
                .addThrows(Exception.class).setBody(
                "try {\n" +
                        "            return ic.proceed();\n" +
                        "        } finally {\n" +
                        "        }").addAnnotation(AroundInvoke.class);

        System.out.println(interceptor);
    }
}
