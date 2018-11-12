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
import org.myorg.organisation.domain. Personnel;
import org.myorg.utils.data.transformation.Transformer;
import org.slf4j.LoggerFactory;

/**
 * JPA unit test for the personnelManager.
 * @author jnamla
 */
public class PersonnelManagerTest {
    
    private PersonnelManager personnelManager;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public PersonnelManagerTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("organisationTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        personnelManager = new PersonnelManager(entityManager, LoggerFactory.getLogger(PersonnelManagerTest.class));
    }
    
    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Test of findAll method, of class PersonnelManager.
     */
    @Test
    public void testFindAll() {
        Collection< Personnel> result = personnelManager.findAll();
        assertThat(result.isEmpty(), is(false));
    }
    
    /**
     * Test of findById Personnel method, of class PersonnelManager.
     */
    @Test
    public void testFindByIdPersonnel() {
        Personnel expResult;
        expResult = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/personnel/json/entity.json"), Personnel.class);
        Personnel result = personnelManager.findByIdPersonnel(expResult.getId());
        assertThat(result, equalTo(expResult));
    }
	
	
    @Test
    public void create() {
        Personnel toCreate, created;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/personnel/json/entity_create.json"), Personnel.class);
        entityManager.getTransaction().begin();
        personnelManager.create(toCreate);
        entityManager.getTransaction().commit();
        created = personnelManager.findByUniqueFields(toCreate);
        assertThat(created, is(notNullValue()));
    }
    
    @Test (expected = InternalServerErrorException.class)
    public void createExistent() {
        Personnel toCreate;
        toCreate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/personnel/json/entity_create_existent.json"), Personnel.class);
        personnelManager.create(toCreate);
    }
    
    @Test
    public void update() {
        Personnel toUpdate, updated;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/personnel/json/entity_update.json"), Personnel.class);
        personnelManager.update(toUpdate.getId(), toUpdate);
        updated = personnelManager.findByIdPersonnel(toUpdate.getId());
        assertThat(updated.getEmail(), equalTo(toUpdate.getEmail()));
        assertThat(updated.getFirstName(), equalTo(toUpdate.getFirstName()));
        assertThat(updated.getLastName(), equalTo(toUpdate.getLastName()));
        assertThat(updated.getMiddleName(), equalTo(toUpdate.getMiddleName()));
        assertThat(updated.getPassword(), equalTo(toUpdate.getPassword()));
        assertThat(updated.getAdditionalLastNames(), equalTo(toUpdate.getAdditionalLastNames()));
    }
    
    @Test (expected = BadRequestException.class)
    public void updateInconsistentId() {
        Personnel toUpdate;
        toUpdate = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/personnel/json/entity_update.json"), Personnel.class);
        personnelManager.update(toUpdate.getId()+1, toUpdate);
    }
    
    @Test
    public void delete() {
        Personnel toDeleteJson, toDelete, deleted;
        toDeleteJson = Transformer.jsonToObjetFromInputStream(getClass().getResourceAsStream("/data/personnel/json/entity_create.json"), Personnel.class);
        toDelete = personnelManager.findByUniqueFields(toDeleteJson);
        entityManager.getTransaction().begin();
        personnelManager.delete(toDelete.getId());
        entityManager.getTransaction().commit();
        deleted = personnelManager.findByUniqueFields(toDelete);
        assertThat(deleted, is(nullValue()));
    }
    
    @Test (expected = EntityNotFoundException.class)
    public void deleteNonExistent() {
        personnelManager.delete(0);
    }
}
