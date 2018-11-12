package org.myorg.organisation.domain.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.myorg.organisation.domain.Personnel;
import org.myorg.utils.data.checks.PersistenceChecks;
import org.slf4j.Logger;

/**
 * The personnelManager implementation is used to find and manage personnel
 * 
 * @author jnamla
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class PersonnelManager {
    
    private EntityManager entityManager;
    
    private Logger logger;
 
    public PersonnelManager() {
        
    }
    
    @Inject
    public PersonnelManager(EntityManager entityManager, Logger logger) {
        this.entityManager = entityManager;
        this.logger = logger;
    }
    
    /**
     * Returns the list of all personnel in the organisation.
     *
     * @return a collection of personnel
     */
    public Collection<Personnel> findAll() {
        logger.info("Find all personnel.");
        TypedQuery<Personnel> findAll = entityManager.createNamedQuery(Personnel.FIND_ALL, Personnel.class);
        return Collections.unmodifiableCollection(findAll.getResultList());
    }

    /**
     * Find the personnel by its idPersonnel and return a reference to it.
     *
     * @param idPersonnel the idPersonnel
     * @return the personnel
     */
    public Personnel findByIdPersonnel(Integer idPersonnel) {
        logger.info("Find personnel with idPersonnel {}.", idPersonnel);
        return entityManager.find(Personnel.class, Objects.requireNonNull(idPersonnel));
    }

    /**
     * Creates a new personnel in the personnelManager.
     *
     * @param personnel a personnel to create
     */
    public void create(Personnel personnel) {
        Objects.requireNonNull(personnel);
        logger.info("Creating {}.", personnel);
        PersistenceChecks.isValidCheck(!this.exists(personnel.getEmail()), "Already exists in database");
        entityManager.persist(personnel);
    }

    /**
     * Creates a new personnel in the personnelManager.
     *
     * @param idPersonnel id of the personnel
     * @param personnel a personnel to update
     */
    public void update(Integer idPersonnel, Personnel personnel) {
        Objects.requireNonNull(personnel);
        logger.info("Updating {} using idPersonnel {}.", new Object[]{personnel, idPersonnel});
        PersistenceChecks.isIdConsistentForUpdate(Objects.equals(idPersonnel, personnel.getId()), Personnel.class);
        Personnel currentPersonnel = entityManager.getReference(Personnel.class, Objects.requireNonNull(idPersonnel));
        PersistenceChecks.checkNotFoundStatus(currentPersonnel, Personnel.class);
        entityManager.merge(personnel);
    }

    /**
     * Deletes a personnel via idPersonnel.
     *
     * @param idPersonnel the idPersonnel
     */
    public void delete(Integer idPersonnel) {
        Objects.requireNonNull(idPersonnel);
        logger.info("Deleting personnel with idPersonnel {}.", idPersonnel);
        Personnel reference = entityManager.getReference(Personnel.class, idPersonnel);
        PersistenceChecks.checkNotFoundStatus(reference, Personnel.class);
        entityManager.remove(reference);
    }
    
    /**
     * Check if personnel with given email already exists.
     *
     * @param email The personnel email 
     * @return true of exists, otherwise false
     */
    private boolean exists(String email) {
        logger.info("Find personnel with email {}.", email);
        TypedQuery<Personnel> personnel = entityManager.createNamedQuery(Personnel.FIND_BY_EMAIL, Personnel.class)
                .setParameter("email", Objects.requireNonNull(email).toUpperCase());
        return !personnel.getResultList().isEmpty();
    }
}
