package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class JPAConverter {

    public static void main(String[] args) {

        final Class fromAttribute = LifeCycle.class;
        final Class toColumn = String.class;

        final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
        javaClassSource.setPackage("org.agoncal.myproj").setName("MyConverter").addInterface(AttributeConverter.class).addAnnotation(Converter.class).setLiteralValue("autoApply", "true");
        // Methods
        javaClassSource.addMethod().setPublic().setName("convertToDatabaseColumn").setReturnType(toColumn).setParameters(fromAttribute.getSimpleName() + " entityAttribute").setBody("return null;").addAnnotation(Override.class);
        javaClassSource.addMethod().setPublic().setName("convertToEntityAttribute").setReturnType(fromAttribute).setParameters(toColumn.getSimpleName() + " databaseColumn").setBody("return null;").addAnnotation(Override.class);

        System.out.println(javaClassSource);
    }
}
