package org.myorg.assets.domain.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.myorg.assets.domain.FixedAsset;
import org.myorg.organisation.domain.Area;
import org.myorg.organisation.domain.Personnel;
import org.myorg.utils.data.checks.PersistenceChecks;
import org.slf4j.Logger;

/**
 * The fixedAssetsHolder implementation is used to find and manage fixed fixedAssets
 * 
 * @author jnamla
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class FixedAssetsManager {
    
    private EntityManager entityManager;
   
    private Logger logger;
    
    public FixedAssetsManager() {
        
    }
    
    @Inject
    public FixedAssetsManager(EntityManager entityManager, Logger logger) {
        this.entityManager = entityManager;
        this.logger = logger;
    }
    
    /**
     * Returns the list of all fixedAssets registered in the company.
     *
     * @return a collection of fixedAssets
     */
    public Collection<FixedAsset> findAll() {
        logger.info("Find all fixedAssets.");
        TypedQuery<FixedAsset> findAll = entityManager.createNamedQuery(FixedAsset.FIND_ALL, FixedAsset.class);
        Collection<FixedAsset> results = Collections.unmodifiableCollection(findAll.getResultList());
        PersistenceChecks.checkNotFoundStatus(results, FixedAsset.class);
        return results;
    }
    
    /**
     * Returns the list of all fixedAssets registered in the company.
     *
     * @return a collection of fixedAssets
     */
    public Collection<Area> findAllAreas() {
        logger.info("Find all areas that have fixed assets assigned.");
        TypedQuery<Area> findAll = entityManager.createNamedQuery(FixedAsset.FIND_AREAS, Area.class);
        Collection<Area> results = Collections.unmodifiableCollection(findAll.getResultList());
        PersistenceChecks.checkNotFoundStatus(results, Area.class);
        return results;
    }
    
    /**
     * Returns the list of all fixedAssets registered in the company.
     *
     * @return a collection of fixedAssets
     */
    public Collection<Personnel> findAllPersonnel() {
        logger.info("Find all areas who have fixed assets assigned.");
        TypedQuery<Personnel> findAll = entityManager.createNamedQuery(FixedAsset.FIND_PERSONNEL, Personnel.class);
        Collection<Personnel> results = Collections.unmodifiableCollection(findAll.getResultList());
        PersistenceChecks.checkNotFoundStatus(results, Personnel.class);
        return results;
    }

    /**
     * Find the fixedAsset by its idFixedAsset and return a reference to it.
     *
     * @param idFixedAsset the idFixedAsset
     * @return the fixedAsset
     */
    public FixedAsset findByIdFixedAsset(Integer idFixedAsset) {
        logger.info("Find fixedAsset with idFixedAsset {}.", idFixedAsset);
        FixedAsset reference = entityManager.getReference(FixedAsset.class, idFixedAsset);
        PersistenceChecks.checkNotFoundStatus(reference, FixedAsset.class);
        return reference;
    }
    
    /**
     * Returns fixedAsset under given SerialNumber and internal number.
     *
     * @param fixedAsset Parameters to check 
     * @return the fixed asset or null if it couldn't be found
     */
    public FixedAsset findByUniqueFields(FixedAsset fixedAsset) {
        logger.info("Find fixedAsset with serialNumber {} and internalNumber.", new Object[]{fixedAsset.getSerialNumber(), fixedAsset.getInternalStockNumber()});
        TypedQuery<FixedAsset> query = entityManager.createNamedQuery(FixedAsset.FIND_BY_UNIQUE_FIELDS, FixedAsset.class)
                .setParameter("serialNumber", Objects.requireNonNull(fixedAsset.getSerialNumber()))
                .setParameter("internalStockNumber", Objects.requireNonNull(fixedAsset.getInternalStockNumber()));
        try {
           return query.getSingleResult(); 
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Find the fixedAsset by its fixedAssetTypeId and return a reference to it.
     *
     * @param fixedAssetTypeId the identifier of the fixedAssetType
     * @return the fixedAsset
     */
    public Collection<FixedAsset> findByAssetTypeId(Integer fixedAssetTypeId) {
        logger.info("Find Asset with type {}.", fixedAssetTypeId);
        TypedQuery<FixedAsset> findAll = entityManager.createNamedQuery(FixedAsset.FIND_BY_TYPE, FixedAsset.class)
                .setParameter("id", Objects.requireNonNull(fixedAssetTypeId));
        Collection<FixedAsset> results = Collections.unmodifiableCollection(findAll.getResultList());
        PersistenceChecks.checkNotFoundStatus(results, FixedAsset.class);
        return results;
    }
    
    /**
     * Find the fixedAsset by its purchaseDate and return a reference to it.
     *
     * @param purchaseDate The date the fixedAsset was purchased
     * @return the fixedAsset
     */
    public Collection<FixedAsset> findByPurchaseDate(String purchaseDate) {
        logger.info("Find Asset with purchase date {}.", purchaseDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate theDate = LocalDate.parse(Objects.requireNonNull(purchaseDate), formatter);
        TypedQuery<FixedAsset> findAll = entityManager.createNamedQuery(FixedAsset.FIND_BY_PURCHASE_DATE, FixedAsset.class)
                .setParameter("date", theDate);
        Collection<FixedAsset> results = Collections.unmodifiableCollection(findAll.getResultList());
        PersistenceChecks.checkNotFoundStatus(results, FixedAsset.class);
        return results;
    }
    
    /**
     * Find the fixedAsset by its serialNumber and return a reference to it.
     *
     * @param serialNumber the serialNumber
     * @return the fixedAssets
     */
    public Collection<FixedAsset> findBySerialNumber(String serialNumber) {
        logger.info("Find fixedAsset with serialNumber {}.", serialNumber);
        TypedQuery<FixedAsset> findAll = entityManager.createNamedQuery(FixedAsset.FIND_BY_SERIAL_NUMBER, FixedAsset.class)
                .setParameter("number", "%" + Objects.requireNonNull(serialNumber) + "%");
        Collection<FixedAsset> results = Collections.unmodifiableCollection(findAll.getResultList());
        PersistenceChecks.checkNotFoundStatus(results, FixedAsset.class);
        return results;
    }
    
    /**
     * Creates a new fixedAsset in the fixedAssetsHolder.
     *
     * @param fixedAsset a fixedAsset to create
     */
    public void create(FixedAsset fixedAsset) {
        Objects.requireNonNull(fixedAsset);
        logger.info("Creating {}.", fixedAsset);
        PersistenceChecks.isValidCheck(!this.exists(fixedAsset), "Already exists a fixed asset with that information");
        PersistenceChecks.isValidCheck(fixedAsset.isOutOfStockDateValid(), "Out of Stock date is not valid.");
        entityManager.persist(fixedAsset);
    }

    /**
     * Update a new fixedAsset given its id, as a requirement only internalStockNumber and outOfStockDate can be modified.
     *
     * @param idFixedAsset id of the fixedAsset
     * @param fixedAsset a fixedAsset to update
     */
    public void update(Integer idFixedAsset, FixedAsset fixedAsset) {
        Objects.requireNonNull(fixedAsset);
        logger.info("Updating {} using idFixedAsset {}.", new Object[]{fixedAsset, idFixedAsset});
        PersistenceChecks.isIdConsistentForUpdate(Objects.equals(idFixedAsset, fixedAsset.getId()), FixedAsset.class);
        FixedAsset currentFixedAsset = entityManager.getReference(FixedAsset.class, Objects.requireNonNull(idFixedAsset));
        PersistenceChecks.checkNotFoundStatus(currentFixedAsset, FixedAsset.class);
        PersistenceChecks.isValidCheck(fixedAsset.isOutOfStockDateValid(), "Out of Stock date is not valid.");
        entityManager.merge(fixedAsset);
    }

    /**
     * Deletes a fixedAsset via idFixedAsset.
     *
     * @param idFixedAsset the idFixedAsset
     */
    public void delete(Integer idFixedAsset) {
        Objects.requireNonNull(idFixedAsset);
        logger.info("Deleting fixedAsset with idFixedAsset {}.", idFixedAsset);
        FixedAsset reference = entityManager.getReference(FixedAsset.class, idFixedAsset);
        PersistenceChecks.checkNotFoundStatus(reference, FixedAsset.class);
        entityManager.remove(reference);
    }
    
    /**
     * Check if fixedAsset under given SerialNumber and InternalNumber already exists.
     *
     * @param fixedAsset fixedAsset parameters to check
     * @return true of exists, otherwise false
     */
    public boolean exists(FixedAsset fixedAsset) {
        logger.info("Find fixedAsset with serialNumber {} and internalNumber {}.", new Object[]{fixedAsset.getSerialNumber(), fixedAsset.getInternalStockNumber()});
        TypedQuery<FixedAsset> result = entityManager.createNamedQuery(FixedAsset.FIND_BY_UNIQUE_FIELDS, FixedAsset.class)
                .setParameter("serialNumber", Objects.requireNonNull(fixedAsset.getSerialNumber()))
                .setParameter("internalStockNumber", Objects.requireNonNull(fixedAsset.getInternalStockNumber()));
        return !result.getResultList().isEmpty();
    }
 
}
