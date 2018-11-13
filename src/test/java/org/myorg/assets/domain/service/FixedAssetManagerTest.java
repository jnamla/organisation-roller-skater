package org.myorg.assets.domain.service;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.myorg.assets.domain.FixedAsset;
import org.myorg.utils.data.transformation.Transformer;
import org.slf4j.LoggerFactory;

/**
 * JPA unit test for the fixedAssetManager.
 * @author jnamla
 */
public class FixedAssetManagerTest {
    
    private FixedAssetsManager fixedAssetManager;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public FixedAssetManagerTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("organisationTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        fixedAssetManager = new FixedAssetsManager(entityManager, LoggerFactory.getLogger(FixedAssetManagerTest.class));
    }
    
    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
    
    /**
     * Test of findAll method, of class FixedAssetManager.
     */
    @Test
    public void testFindAll() {
        Collection<FixedAsset> result = fixedAssetManager.findAll();
        assertThat(result.isEmpty(), is(false));
    }
    
    /**
     * Test of findByIdFixedAsset method, of class FixedAssetManager.
     */
    @Test
    public void testFindByIdFixedAsset() {
        FixedAsset expResult;
        expResult = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/fixedAsset/json/entity.json"), FixedAsset.class);
        FixedAsset result = fixedAssetManager.findByIdFixedAsset(expResult.getId());
        assertThat(result, equalTo(expResult));
    }
    
    @Test
    public void create() {
        FixedAsset toCreate, created;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/fixedAsset/json/entity_create.json"), FixedAsset.class);
        entityManager.getTransaction().begin();
        fixedAssetManager.create(toCreate);
        entityManager.getTransaction().commit();
        created = fixedAssetManager.findByUniqueFields(toCreate);
        assertThat(created, is(notNullValue()));
    }
    
    @Test (expected = InternalServerErrorException.class)
    public void createExistent() {
        FixedAsset toCreate;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/fixedAsset/json/entity_create_existent.json"), FixedAsset.class);
        fixedAssetManager.create(toCreate);
    }
    
    @Test
    public void update() {
        FixedAsset toUpdate, updated;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/fixedAsset/json/entity_update.json"), FixedAsset.class);
        fixedAssetManager.update(toUpdate.getId(), toUpdate);
        updated = fixedAssetManager.findByIdFixedAsset(toUpdate.getId());
        assertThat(updated.getDescription(), equalTo(toUpdate.getDescription()));
        assertThat(updated.getSerialNumber(), equalTo(toUpdate.getSerialNumber()));
    }
    
    @Test (expected = BadRequestException.class)
    public void updateInconsistentId() {
        FixedAsset toUpdate;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/fixedAsset/json/entity_update.json"), FixedAsset.class);
        fixedAssetManager.update(toUpdate.getId()+1, toUpdate);
    }
    
    @Test (expected = InternalServerErrorException.class)
    public void updateInvalidOutOfStockDate() {
        FixedAsset toUpdate;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/fixedAsset/json/entity_update_invalid_date.json"), FixedAsset.class);
        fixedAssetManager.update(toUpdate.getId(), toUpdate);
    }
    
    @Test
    public void delete() {
        FixedAsset toDeleteJson, toDelete, deleted;
        toDeleteJson = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/fixedAsset/json/entity_delete.json"), FixedAsset.class);
        toDelete = fixedAssetManager.findByUniqueFields(toDeleteJson);
        entityManager.getTransaction().begin();
        fixedAssetManager.delete(toDelete.getId());
        entityManager.getTransaction().commit();
        deleted = fixedAssetManager.findByUniqueFields(toDelete);
        assertThat(deleted, is(nullValue()));
    }
    
    @Test (expected = EntityNotFoundException.class)
    public void deleteNonExistent() {
        fixedAssetManager.delete(0);
    }
}
