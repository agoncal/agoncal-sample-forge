/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.agoncal.sample.forge.roaster;

import java.lang.annotation.Annotation;

/**
 * JPA Entity lifecycle.
 *
 * @author <a href="mailto:lantonio.goncalves@gmail.com">Antonio Goncalves</a>
 */
public enum LifeCycle {

    PRE_PERSIST(javax.persistence.PrePersist.class),
    POST_PERSIST(javax.persistence.PostPersist.class),
    PRE_UPDATE(javax.persistence.PreUpdate.class),
    POST_UPDATE(javax.persistence.PostPersist.class),
    PRE_REMOVE(javax.persistence.PreRemove.class),
    POST_REMOVE(javax.persistence.PostRemove.class),
    POST_LOAD(javax.persistence.PostLoad.class);

    private Class<? extends Annotation> annotation;

    LifeCycle(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }
}
