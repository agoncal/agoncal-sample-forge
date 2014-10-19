package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class CDIProducerField {

    public static void main(String[] args) {

        String type = "javax.persistence.EntityManager";
        String qualifier = "javax.inject.Named";

        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage("org.agoncal.myproj").setName("MyClass");
        // Fields
        FieldSource<?> em = javaClass.addField().setPrivate().setName("em").setType(type);
        em.addAnnotation(Produces.class);
        em.addAnnotation(qualifier);
        em.addAnnotation(Alternative.class);
        em.addAnnotation(RequestScoped.class);

        System.out.println(javaClass);
    }
}
