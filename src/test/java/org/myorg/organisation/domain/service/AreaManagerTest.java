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
import org.myorg.organisation.domain.Area;
import org.myorg.utils.data.transformation.Transformer;
import org.slf4j.LoggerFactory;

/**
 * JPA unit test for the areaManager.
 * @author jnamla
 */
public class AreaManagerTest {
    
    private AreaManager areaManager;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public AreaManagerTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("organisationTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        areaManager = new AreaManager(entityManager, LoggerFactory.getLogger(AreaManagerTest.class));
    }
    
    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Test of findAll method, of class AreaManager.
     */
    @Test
    public void testFindAll() {
        Collection<Area> result = areaManager.findAll();
        assertThat(result.isEmpty(), is(false));
    }
    
    /**
     * Test of findByIdArea method, of class AreaManager.
     */
    @Test
    public void testFindByIdArea() {
        Area expResult;
        expResult = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/area/json/entity.json"), Area.class);
        Area result = areaManager.findByIdArea(expResult.getId());
        assertThat(result, equalTo(expResult));
    }
    
    @Test
    public void create() {
        Area toCreate, created;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/area/json/entity_create.json"), Area.class);
        entityManager.getTransaction().begin();
        areaManager.create(toCreate);
        entityManager.getTransaction().commit();
        created = areaManager.findByUniqueFields(toCreate);
        assertThat(created, is(notNullValue()));
    }
    
    @Test (expected = InternalServerErrorException.class)
    public void createExistent() {
        Area toCreate;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/area/json/entity_create_existent.json"), Area.class);
        areaManager.create(toCreate);
    }
    
    @Test
    public void update() {
        Area toUpdate, updated;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/area/json/entity_update.json"), Area.class);
        areaManager.update(toUpdate.getId(), toUpdate);
        updated = areaManager.findByIdArea(toUpdate.getId());
        assertThat(updated.getGenericArea().getId(), equalTo(toUpdate.getGenericArea().getId()));
        assertThat(updated.getCity().getId(), equalTo(toUpdate.getCity().getId()));
    }
    
    @Test (expected = BadRequestException.class)
    public void updateInconsistentId() {
        Area toUpdate;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/area/json/entity_update.json"), Area.class);
        areaManager.update(toUpdate.getId()+1, toUpdate);
    }
    
    @Test
    public void delete() {
        Area toDeleteJson, toDelete, deleted;
        toDeleteJson = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/area/json/entity_delete.json"), Area.class);
        toDelete = areaManager.findByUniqueFields(toDeleteJson);
        entityManager.getTransaction().begin();
        areaManager.delete(toDelete.getId());
        entityManager.getTransaction().commit();
        deleted = areaManager.findByUniqueFields(toDelete);
        assertThat(deleted, is(nullValue()));
    }
    
    @Test (expected = EntityNotFoundException.class)
    public void deleteNonExistent() {
        areaManager.delete(0);
    }
}
