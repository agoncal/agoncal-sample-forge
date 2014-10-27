package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.PrePersist;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class JPAEntityListener {

    public static void main(String[] args) {

        List<LifeCycle> lifeCyclesList = new ArrayList<>();
        Collections.addAll(lifeCyclesList, LifeCycle.POST_LOAD, LifeCycle.POST_REMOVE, LifeCycle.PRE_UPDATE);


        final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
        javaClassSource.setPackage("org.agoncal.myproj").setName("MyEntityListener");

        // Methods
        for (LifeCycle lifeCycle : lifeCyclesList) {
            javaClassSource.addMethod().setPublic().setName(className2FieldName(lifeCycle.getAnnotation())).setReturnTypeVoid().setParameters("Object object").setBody("").addAnnotation(lifeCycle.getAnnotation());
        }

        System.out.println(javaClassSource);
    }


    private static String className2FieldName(Class<? extends Annotation> clazz) {
        String className = clazz.getSimpleName();
        return className.replaceFirst(Character.toString(className.charAt(0)), Character.toString(className.charAt(0)).toLowerCase());
    }
}
