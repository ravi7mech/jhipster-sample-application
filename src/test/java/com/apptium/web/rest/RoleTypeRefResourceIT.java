package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.RoleTypeRef;
import com.apptium.repository.RoleTypeRefRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RoleTypeRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleTypeRefResourceIT {

    private static final String DEFAULT_ROLENAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLENAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLETYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROLETYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/role-type-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleTypeRefRepository roleTypeRefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleTypeRefMockMvc;

    private RoleTypeRef roleTypeRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleTypeRef createEntity(EntityManager em) {
        RoleTypeRef roleTypeRef = new RoleTypeRef().rolename(DEFAULT_ROLENAME).roletype(DEFAULT_ROLETYPE);
        return roleTypeRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleTypeRef createUpdatedEntity(EntityManager em) {
        RoleTypeRef roleTypeRef = new RoleTypeRef().rolename(UPDATED_ROLENAME).roletype(UPDATED_ROLETYPE);
        return roleTypeRef;
    }

    @BeforeEach
    public void initTest() {
        roleTypeRef = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleTypeRef() throws Exception {
        int databaseSizeBeforeCreate = roleTypeRefRepository.findAll().size();
        // Create the RoleTypeRef
        restRoleTypeRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRef)))
            .andExpect(status().isCreated());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeCreate + 1);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRolename()).isEqualTo(DEFAULT_ROLENAME);
        assertThat(testRoleTypeRef.getRoletype()).isEqualTo(DEFAULT_ROLETYPE);
    }

    @Test
    @Transactional
    void createRoleTypeRefWithExistingId() throws Exception {
        // Create the RoleTypeRef with an existing ID
        roleTypeRef.setId(1L);

        int databaseSizeBeforeCreate = roleTypeRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleTypeRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRef)))
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRolenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleTypeRefRepository.findAll().size();
        // set the field null
        roleTypeRef.setRolename(null);

        // Create the RoleTypeRef, which fails.

        restRoleTypeRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRef)))
            .andExpect(status().isBadRequest());

        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoletypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleTypeRefRepository.findAll().size();
        // set the field null
        roleTypeRef.setRoletype(null);

        // Create the RoleTypeRef, which fails.

        restRoleTypeRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRef)))
            .andExpect(status().isBadRequest());

        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefs() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleTypeRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].rolename").value(hasItem(DEFAULT_ROLENAME)))
            .andExpect(jsonPath("$.[*].roletype").value(hasItem(DEFAULT_ROLETYPE)));
    }

    @Test
    @Transactional
    void getRoleTypeRef() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get the roleTypeRef
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL_ID, roleTypeRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleTypeRef.getId().intValue()))
            .andExpect(jsonPath("$.rolename").value(DEFAULT_ROLENAME))
            .andExpect(jsonPath("$.roletype").value(DEFAULT_ROLETYPE));
    }

    @Test
    @Transactional
    void getNonExistingRoleTypeRef() throws Exception {
        // Get the roleTypeRef
        restRoleTypeRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoleTypeRef() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();

        // Update the roleTypeRef
        RoleTypeRef updatedRoleTypeRef = roleTypeRefRepository.findById(roleTypeRef.getId()).get();
        // Disconnect from session so that the updates on updatedRoleTypeRef are not directly saved in db
        em.detach(updatedRoleTypeRef);
        updatedRoleTypeRef.rolename(UPDATED_ROLENAME).roletype(UPDATED_ROLETYPE);

        restRoleTypeRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoleTypeRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoleTypeRef))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRolename()).isEqualTo(UPDATED_ROLENAME);
        assertThat(testRoleTypeRef.getRoletype()).isEqualTo(UPDATED_ROLETYPE);
    }

    @Test
    @Transactional
    void putNonExistingRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleTypeRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRef)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleTypeRefWithPatch() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();

        // Update the roleTypeRef using partial update
        RoleTypeRef partialUpdatedRoleTypeRef = new RoleTypeRef();
        partialUpdatedRoleTypeRef.setId(roleTypeRef.getId());

        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleTypeRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleTypeRef))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRolename()).isEqualTo(DEFAULT_ROLENAME);
        assertThat(testRoleTypeRef.getRoletype()).isEqualTo(DEFAULT_ROLETYPE);
    }

    @Test
    @Transactional
    void fullUpdateRoleTypeRefWithPatch() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();

        // Update the roleTypeRef using partial update
        RoleTypeRef partialUpdatedRoleTypeRef = new RoleTypeRef();
        partialUpdatedRoleTypeRef.setId(roleTypeRef.getId());

        partialUpdatedRoleTypeRef.rolename(UPDATED_ROLENAME).roletype(UPDATED_ROLETYPE);

        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleTypeRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleTypeRef))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRolename()).isEqualTo(UPDATED_ROLENAME);
        assertThat(testRoleTypeRef.getRoletype()).isEqualTo(UPDATED_ROLETYPE);
    }

    @Test
    @Transactional
    void patchNonExistingRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleTypeRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roleTypeRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleTypeRef() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeDelete = roleTypeRefRepository.findAll().size();

        // Delete the roleTypeRef
        restRoleTypeRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleTypeRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
