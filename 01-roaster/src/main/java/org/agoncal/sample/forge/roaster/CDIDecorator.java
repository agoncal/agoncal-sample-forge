package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class CDIDecorator {

    public static void main(String[] args) {

        String interf = "java.io.Serializable";

        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage("org.agoncal.myproj").setName("MyClass").setAbstract(true).addInterface(interf).addAnnotation(Decorator.class);
        // Fields
        FieldSource<?> comments = javaClass.addField().setPrivate().setName("delegate").setType(interf);
        comments.addAnnotation(Inject.class);
        comments.addAnnotation(Delegate.class);

        System.out.println(javaClass);
    }
}
