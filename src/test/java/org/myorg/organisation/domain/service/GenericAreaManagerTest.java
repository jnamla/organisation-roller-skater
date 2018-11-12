package org.myorg.organisation.domain.service;

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
import org.myorg.organisation.domain.GenericArea;
import org.myorg.utils.data.transformation.Transformer;
import org.slf4j.LoggerFactory;

/**
 * JPA unit test for the genericAreaManager.
 * @author jnamla
 */
public class GenericAreaManagerTest {
    
    private GenericAreaManager genericAreaManager;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public GenericAreaManagerTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("organisationTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        genericAreaManager = new GenericAreaManager(entityManager, LoggerFactory.getLogger(GenericAreaManagerTest.class));
    }
    
    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Test of findAll method, of class GenericAreaManager.
     */
    @Test
    public void testFindAll() {
        Collection<GenericArea> result = genericAreaManager.findAll();
        assertThat(result.isEmpty(), is(false));
    }
    
    /**
     * Test of findByIdArea method, of class GenericAreaManager.
     */
    @Test
    public void testFindByIdArea() {
        GenericArea expResult;
        expResult = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/genericArea/json/entity.json"), GenericArea.class);
        GenericArea result = genericAreaManager.findByIdGenericArea(expResult.getId());
        assertThat(result, equalTo(expResult));
    }
    
    @Test
    public void create() {
        GenericArea toCreate, created;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/genericArea/json/entity_create.json"), GenericArea.class);
        entityManager.getTransaction().begin();
        genericAreaManager.create(toCreate);
        entityManager.getTransaction().commit();
        created = genericAreaManager.findByUniqueFields(toCreate);
        assertThat(created, is(notNullValue()));
    }
    
    @Test (expected = InternalServerErrorException.class)
    public void createExistent() {
        GenericArea toCreate;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/genericArea/json/entity_create_existent.json"), GenericArea.class);
        genericAreaManager.create(toCreate);
    }
    
    @Test
    public void update() {
        GenericArea toUpdate, updated;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/genericArea/json/entity_update.json"), GenericArea.class);
        genericAreaManager.update(toUpdate.getId(), toUpdate);
        updated = genericAreaManager.findByIdGenericArea(toUpdate.getId());
        assertThat(updated.getName(), equalTo(toUpdate.getName()));
        assertThat(updated.getDescription(), equalTo(toUpdate.getDescription()));
    }
    
    @Test (expected = BadRequestException.class)
    public void updateInconsistentId() {
        GenericArea toUpdate;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/genericArea/json/entity_update.json"), GenericArea.class);
        genericAreaManager.update(toUpdate.getId()+1, toUpdate);
    }
    
    @Test
    public void delete() {
        GenericArea toDeleteJson, toDelete, deleted;
        toDeleteJson = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/genericArea/json/entity_create.json"), GenericArea.class);
        toDelete = genericAreaManager.findByUniqueFields(toDeleteJson);
        entityManager.getTransaction().begin();
        genericAreaManager.delete(toDelete.getId());
        entityManager.getTransaction().commit();
        deleted = genericAreaManager.findByUniqueFields(toDelete);
        assertThat(deleted, is(nullValue()));
    }
    
    @Test (expected = EntityNotFoundException.class)
    public void deleteNonExistent() {
        genericAreaManager.delete(0);
    }
}
