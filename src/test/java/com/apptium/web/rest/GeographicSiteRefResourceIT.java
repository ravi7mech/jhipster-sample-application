package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.GeographicSiteRef;
import com.apptium.repository.GeographicSiteRefRepository;
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
 * Integration tests for the {@link GeographicSiteRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeographicSiteRefResourceIT {

    private static final String DEFAULT_SITE_REF = "AAAAAAAAAA";
    private static final String UPDATED_SITE_REF = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_CONTACT_ID = 1;
    private static final Integer UPDATED_CUSTOMER_CONTACT_ID = 2;

    private static final String ENTITY_API_URL = "/api/geographic-site-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GeographicSiteRefRepository geographicSiteRefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeographicSiteRefMockMvc;

    private GeographicSiteRef geographicSiteRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicSiteRef createEntity(EntityManager em) {
        GeographicSiteRef geographicSiteRef = new GeographicSiteRef()
            .siteRef(DEFAULT_SITE_REF)
            .location(DEFAULT_LOCATION)
            .customerContactId(DEFAULT_CUSTOMER_CONTACT_ID);
        return geographicSiteRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicSiteRef createUpdatedEntity(EntityManager em) {
        GeographicSiteRef geographicSiteRef = new GeographicSiteRef()
            .siteRef(UPDATED_SITE_REF)
            .location(UPDATED_LOCATION)
            .customerContactId(UPDATED_CUSTOMER_CONTACT_ID);
        return geographicSiteRef;
    }

    @BeforeEach
    public void initTest() {
        geographicSiteRef = createEntity(em);
    }

    @Test
    @Transactional
    void createGeographicSiteRef() throws Exception {
        int databaseSizeBeforeCreate = geographicSiteRefRepository.findAll().size();
        // Create the GeographicSiteRef
        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isCreated());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeCreate + 1);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(DEFAULT_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testGeographicSiteRef.getCustomerContactId()).isEqualTo(DEFAULT_CUSTOMER_CONTACT_ID);
    }

    @Test
    @Transactional
    void createGeographicSiteRefWithExistingId() throws Exception {
        // Create the GeographicSiteRef with an existing ID
        geographicSiteRef.setId(1L);

        int databaseSizeBeforeCreate = geographicSiteRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSiteRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicSiteRefRepository.findAll().size();
        // set the field null
        geographicSiteRef.setSiteRef(null);

        // Create the GeographicSiteRef, which fails.

        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicSiteRefRepository.findAll().size();
        // set the field null
        geographicSiteRef.setLocation(null);

        // Create the GeographicSiteRef, which fails.

        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicSiteRefRepository.findAll().size();
        // set the field null
        geographicSiteRef.setCustomerContactId(null);

        // Create the GeographicSiteRef, which fails.

        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefs() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicSiteRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteRef").value(hasItem(DEFAULT_SITE_REF)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].customerContactId").value(hasItem(DEFAULT_CUSTOMER_CONTACT_ID)));
    }

    @Test
    @Transactional
    void getGeographicSiteRef() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get the geographicSiteRef
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL_ID, geographicSiteRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geographicSiteRef.getId().intValue()))
            .andExpect(jsonPath("$.siteRef").value(DEFAULT_SITE_REF))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.customerContactId").value(DEFAULT_CUSTOMER_CONTACT_ID));
    }

    @Test
    @Transactional
    void getNonExistingGeographicSiteRef() throws Exception {
        // Get the geographicSiteRef
        restGeographicSiteRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGeographicSiteRef() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();

        // Update the geographicSiteRef
        GeographicSiteRef updatedGeographicSiteRef = geographicSiteRefRepository.findById(geographicSiteRef.getId()).get();
        // Disconnect from session so that the updates on updatedGeographicSiteRef are not directly saved in db
        em.detach(updatedGeographicSiteRef);
        updatedGeographicSiteRef.siteRef(UPDATED_SITE_REF).location(UPDATED_LOCATION).customerContactId(UPDATED_CUSTOMER_CONTACT_ID);

        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeographicSiteRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGeographicSiteRef))
            )
            .andExpect(status().isOk());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(UPDATED_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testGeographicSiteRef.getCustomerContactId()).isEqualTo(UPDATED_CUSTOMER_CONTACT_ID);
    }

    @Test
    @Transactional
    void putNonExistingGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geographicSiteRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeographicSiteRefWithPatch() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();

        // Update the geographicSiteRef using partial update
        GeographicSiteRef partialUpdatedGeographicSiteRef = new GeographicSiteRef();
        partialUpdatedGeographicSiteRef.setId(geographicSiteRef.getId());

        partialUpdatedGeographicSiteRef.siteRef(UPDATED_SITE_REF).location(UPDATED_LOCATION);

        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeographicSiteRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeographicSiteRef))
            )
            .andExpect(status().isOk());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(UPDATED_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testGeographicSiteRef.getCustomerContactId()).isEqualTo(DEFAULT_CUSTOMER_CONTACT_ID);
    }

    @Test
    @Transactional
    void fullUpdateGeographicSiteRefWithPatch() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();

        // Update the geographicSiteRef using partial update
        GeographicSiteRef partialUpdatedGeographicSiteRef = new GeographicSiteRef();
        partialUpdatedGeographicSiteRef.setId(geographicSiteRef.getId());

        partialUpdatedGeographicSiteRef.siteRef(UPDATED_SITE_REF).location(UPDATED_LOCATION).customerContactId(UPDATED_CUSTOMER_CONTACT_ID);

        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeographicSiteRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeographicSiteRef))
            )
            .andExpect(status().isOk());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(UPDATED_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testGeographicSiteRef.getCustomerContactId()).isEqualTo(UPDATED_CUSTOMER_CONTACT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, geographicSiteRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeographicSiteRef() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeDelete = geographicSiteRefRepository.findAll().size();

        // Delete the geographicSiteRef
        restGeographicSiteRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, geographicSiteRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
