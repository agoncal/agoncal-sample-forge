package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class JPAEntity {

    public static void main(String[] args) {
        final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
        javaClassSource.setPackage("org.agoncal.myproj").setName("MyEntity");
        javaClassSource.addAnnotation(Entity.class);
        // Entity Listener
        javaClassSource.addAnnotation(EntityListeners.class).setClassArrayValue(String.class, Integer.class);
        // Fields
        javaClassSource.addField().setPrivate().setName("email").setType(String.class).setFinal(true).addAnnotation(Column.class);
        FieldSource<?> comments = javaClassSource.addField().setPrivate().setName("comments");
        comments.setType(List.class).setLiteralInitializer("ArrayList");

        System.out.println(javaClassSource);

    }
}
