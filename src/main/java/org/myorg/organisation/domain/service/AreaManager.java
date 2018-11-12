package org.myorg.organisation.domain.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.myorg.organisation.domain.Area;
import org.myorg.utils.data.checks.PersistenceChecks;
import org.slf4j.Logger;

/**
 * The areaManager implementation is used to find and manage areas
 * 
 * @author jnamla
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class AreaManager {
    
    private EntityManager entityManager;
    private Logger logger;
 
    
    public AreaManager() {
        
    }
    
    @Inject
    public AreaManager(EntityManager entityManager, Logger logger) {
        this.entityManager = entityManager;
        this.logger = logger;
    }
    
    /**
     * Returns the list of all areas in the organisation.
     *
     * @return a collection of areas
     */
    public Collection<Area> findAll() {
        logger.info("Find all areas.");
        TypedQuery<Area> findAll = entityManager.createNamedQuery(Area.FIND_ALL, Area.class);
        return Collections.unmodifiableCollection(findAll.getResultList());
    }

    /**
     * Find the area by its idArea and return a reference to it.
     *
     * @param idArea the idArea
     * @return the area
     */
    public Area findByIdArea(Integer idArea) {
        logger.info("Find area with idArea {}.", idArea);
        return entityManager.find(Area.class, Objects.requireNonNull(idArea));
    }

    /**
     * Creates a new area in the areaManager.
     *
     * @param area a area to create
     */
    public void create(Area area) {
        Objects.requireNonNull(area);
        logger.info("Creating {}.", area);
        PersistenceChecks.isValidCheck(!this.exists(area.getGenericArea().getId(), area.getCity().getId()), "Already exists in database");
        entityManager.persist(area);
    }

    /**
     * Creates a new area in the areaManager.
     *
     * @param idArea id of the area
     * @param area a area to update
     */
    public void update(Integer idArea, Area area) {
        Objects.requireNonNull(area);
        logger.info("Updating {} using idArea {}.", new Object[]{area, idArea});
        PersistenceChecks.isIdConsistentForUpdate(Objects.equals(idArea, area.getId()), Area.class);
        Area currentArea = entityManager.getReference(Area.class, Objects.requireNonNull(idArea));
        PersistenceChecks.checkNotFoundStatus(currentArea, Area.class);
        entityManager.merge(area);
    }

    /**
     * Deletes a area via idArea.
     *
     * @param idArea the idArea
     */
    public void delete(Integer idArea) {
        Objects.requireNonNull(idArea);
        logger.info("Deleting area with idArea {}.", idArea);
        Area reference = entityManager.getReference(Area.class, idArea);
        PersistenceChecks.checkNotFoundStatus(reference, Area.class);
        entityManager.remove(reference);
    }
    
    /**
     * Check if area under given genericArea and city already exists.
     *
     * @param idGenericArea The area 
     * @param idCity the city in which is located
     * @return true of exists, otherwise false
     */
    private boolean exists(Integer idGenericArea, Integer idCity) {
        logger.info("Find area with idGenericArea {} and idCity {}.", new Object[]{idGenericArea, idCity});
        TypedQuery<Area> area = entityManager.createNamedQuery(Area.FIND_BY_CITY_AND_GENERIC_AREA, Area.class)
                .setParameter("idCity", Objects.requireNonNull(idCity))
                .setParameter("idGenericArea", Objects.requireNonNull(idGenericArea));
        return !area.getResultList().isEmpty();
    }
}