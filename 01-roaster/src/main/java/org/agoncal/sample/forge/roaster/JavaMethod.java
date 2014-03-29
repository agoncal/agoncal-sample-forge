package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.io.Serializable;
import java.lang.annotation.Documented;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class JavaMethod {

    public static void main(String[] args) {
        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage("org.agoncal.myproj").setName("MyClass");
        javaClass.addInterface(Serializable.class);
        javaClass.addImport("org.agoncal.myproj.constraints.Email");
        javaClass.addField().setPrivate().setName("email").setType(String.class).setFinal(true).addAnnotation("Email");
        javaClass.addMethod().setPublic().setName("doSomething").setReturnTypeVoid().setParameters("String toto").setBody("return null;").addAnnotation(Documented.class);
        System.out.println(javaClass);

    }
}
