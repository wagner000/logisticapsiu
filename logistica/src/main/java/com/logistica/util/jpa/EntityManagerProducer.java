package com.logistica.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

@ApplicationScoped
public class EntityManagerProducer {

    private EntityManagerFactory factory;

    public EntityManagerProducer() {
        this.factory = Persistence.createEntityManagerFactory("logisticaPU");
    }

    @Produces
    @RequestScoped
    public Session createEntityManager() {
        return (Session) this.factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes Session manager) {
        manager.close();
    }
}