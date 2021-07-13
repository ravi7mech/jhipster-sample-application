package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.IndActivation;
import com.apptium.repository.IndActivationRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link IndActivationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndActivationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final Integer DEFAULT_INDIVIDUAL_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_ID = 2;

    private static final String ENTITY_API_URL = "/api/ind-activations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndActivationRepository indActivationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndActivationMockMvc;

    private IndActivation indActivation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndActivation createEntity(EntityManager em) {
        IndActivation indActivation = new IndActivation()
            .name(DEFAULT_NAME)
            .activity(DEFAULT_ACTIVITY)
            .createdDate(DEFAULT_CREATED_DATE)
            .customerId(DEFAULT_CUSTOMER_ID)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indActivation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndActivation createUpdatedEntity(EntityManager em) {
        IndActivation indActivation = new IndActivation()
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .createdDate(UPDATED_CREATED_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indActivation;
    }

    @BeforeEach
    public void initTest() {
        indActivation = createEntity(em);
    }

    @Test
    @Transactional
    void createIndActivation() throws Exception {
        int databaseSizeBeforeCreate = indActivationRepository.findAll().size();
        // Create the IndActivation
        restIndActivationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isCreated());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeCreate + 1);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testIndActivation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndActivationWithExistingId() throws Exception {
        // Create the IndActivation with an existing ID
        indActivation.setId(1L);

        int databaseSizeBeforeCreate = indActivationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndActivationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setName(null);

        // Create the IndActivation, which fails.

        restIndActivationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setActivity(null);

        // Create the IndActivation, which fails.

        restIndActivationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setCreatedDate(null);

        // Create the IndActivation, which fails.

        restIndActivationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setCustomerId(null);

        // Create the IndActivation, which fails.

        restIndActivationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setIndividualId(null);

        // Create the IndActivation, which fails.

        restIndActivationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndActivations() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indActivation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID)));
    }

    @Test
    @Transactional
    void getIndActivation() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get the indActivation
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL_ID, indActivation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indActivation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID));
    }

    @Test
    @Transactional
    void getNonExistingIndActivation() throws Exception {
        // Get the indActivation
        restIndActivationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndActivation() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();

        // Update the indActivation
        IndActivation updatedIndActivation = indActivationRepository.findById(indActivation.getId()).get();
        // Disconnect from session so that the updates on updatedIndActivation are not directly saved in db
        em.detach(updatedIndActivation);
        updatedIndActivation
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .createdDate(UPDATED_CREATED_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndActivationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndActivation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndActivation))
            )
            .andExpect(status().isOk());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndActivation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indActivation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indActivation))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indActivation))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndActivationWithPatch() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();

        // Update the indActivation using partial update
        IndActivation partialUpdatedIndActivation = new IndActivation();
        partialUpdatedIndActivation.setId(indActivation.getId());

        partialUpdatedIndActivation.name(UPDATED_NAME).individualId(UPDATED_INDIVIDUAL_ID);

        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndActivation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndActivation))
            )
            .andExpect(status().isOk());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testIndActivation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndActivationWithPatch() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();

        // Update the indActivation using partial update
        IndActivation partialUpdatedIndActivation = new IndActivation();
        partialUpdatedIndActivation.setId(indActivation.getId());

        partialUpdatedIndActivation
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .createdDate(UPDATED_CREATED_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndActivation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndActivation))
            )
            .andExpect(status().isOk());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndActivation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indActivation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indActivation))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indActivation))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indActivation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndActivation() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeDelete = indActivationRepository.findAll().size();

        // Delete the indActivation
        restIndActivationMockMvc
            .perform(delete(ENTITY_API_URL_ID, indActivation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
