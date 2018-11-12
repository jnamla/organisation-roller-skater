/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg.organisation.domain.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.slf4j.Logger;

import org.myorg.organisation.domain.GenericArea;
import org.myorg.utils.data.checks.PersistenceChecks;

/**
 * The genericAreaManager implementation is used to find and manage genericAreas
 * 
 * @author jnamla
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class GenericAreaManager {

    private EntityManager entityManager;
    private Logger logger;
    
    public GenericAreaManager() {
        
    }
    
    @Inject
    public GenericAreaManager(EntityManager entityManager, Logger logger) {
        this.entityManager = entityManager;
        this.logger = logger;
    }
    
    /**
     * Returns the list of all genericAreas in the organisation.
     *
     * @return a collection of genericAreas
     */
    public Collection<GenericArea> findAll() {
        logger.info("Find all genericAreas.");
        TypedQuery<GenericArea> findAll = entityManager.createNamedQuery(GenericArea.FIND_ALL, GenericArea.class);
        Collection<GenericArea> results = Collections.unmodifiableCollection(findAll.getResultList());
        PersistenceChecks.checkNotFoundStatus(results, GenericArea.class);
        return Collections.unmodifiableCollection(findAll.getResultList());
    }

    /**
     * Find the genericArea by its idGenericArea and return a reference to it.
     *
     * @param idGenericArea the idGenericArea
     * @return the genericArea
     */
    public GenericArea findByIdGenericArea(Integer idGenericArea) {
        logger.info("Find genericArea with idGenericArea {}.", idGenericArea);
        GenericArea result = entityManager.find(GenericArea.class, Objects.requireNonNull(idGenericArea));
        PersistenceChecks.checkNotFoundStatus(result, GenericArea.class);
        return result;
    }

    /**
     * Find the area by its genericArea and city  and returns a reference to it.
     *
     * @param genericAreaParams the genericArea parameters to search for
     * @return the genericArea
     */
    public GenericArea findByUniqueFields(GenericArea genericAreaParams) {
        logger.info("Find area with params {}.", genericAreaParams);
         TypedQuery<GenericArea> query = entityManager.createNamedQuery(GenericArea.FIND_BY_NAME, GenericArea.class)
                .setParameter("name", Objects.requireNonNull(genericAreaParams.getName()).toUpperCase());
        try {
           return query.getSingleResult(); 
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Creates a new genericArea in the genericAreaManager.
     *
     * @param genericArea a genericArea to create
     */
    public void create(GenericArea genericArea) {
        Objects.requireNonNull(genericArea);
        logger.info("Creating {}.", genericArea);
        PersistenceChecks.isValidCheck(!this.exists(genericArea.getName()), "Already exists in database");
        entityManager.persist(genericArea);
    }

    /**
     * Creates a new genericArea in the genericAreaManager.
     *
     * @param idGenericArea id of the genericArea
     * @param genericArea a genericArea to update
     */
    public void update(Integer idGenericArea, GenericArea genericArea) {
        Objects.requireNonNull(genericArea);
        logger.info("Updating {} using idGenericArea {}.", new Object[]{genericArea, idGenericArea});
        PersistenceChecks.isIdConsistentForUpdate(Objects.equals(idGenericArea, genericArea.getId()), GenericArea.class);
        GenericArea currentGenericArea = entityManager.getReference(GenericArea.class, Objects.requireNonNull(idGenericArea));
        PersistenceChecks.checkNotFoundStatus(currentGenericArea, GenericArea.class);
        entityManager.merge(genericArea);
    }

    /**
     * Deletes a genericArea via idGenericArea.
     *
     * @param idGenericArea the idGenericArea
     */
    public void delete(Integer idGenericArea) {
        Objects.requireNonNull(idGenericArea);
        logger.info("Deleting genericArea with idGenericArea {}.", idGenericArea);
        GenericArea reference = entityManager.getReference(GenericArea.class, idGenericArea);
        PersistenceChecks.checkNotFoundStatus(reference, GenericArea.class);
        entityManager.remove(reference);
    }
    
    /**
     * Check if genericArea under given name already exists.
     *
     * @param name The name of the Generic Area
     * @return true of exists, otherwise false
     */
    private boolean exists(String name) {
        logger.info("Find GenericArea with name {}.", name);
        TypedQuery<GenericArea> genericArea = entityManager.createNamedQuery(GenericArea.FIND_BY_NAME, GenericArea.class)
                .setParameter("name", Objects.requireNonNull(name).toUpperCase());
        return !genericArea.getResultList().isEmpty();
    }
}
