package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.IndAuditTrial;
import com.apptium.repository.IndAuditTrialRepository;
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
 * Integration tests for the {@link IndAuditTrialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndAuditTrialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final Integer DEFAULT_INDIVIDUAL_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_ID = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ind-audit-trials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndAuditTrialRepository indAuditTrialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndAuditTrialMockMvc;

    private IndAuditTrial indAuditTrial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndAuditTrial createEntity(EntityManager em) {
        IndAuditTrial indAuditTrial = new IndAuditTrial()
            .name(DEFAULT_NAME)
            .activity(DEFAULT_ACTIVITY)
            .customerId(DEFAULT_CUSTOMER_ID)
            .individualId(DEFAULT_INDIVIDUAL_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return indAuditTrial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndAuditTrial createUpdatedEntity(EntityManager em) {
        IndAuditTrial indAuditTrial = new IndAuditTrial()
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return indAuditTrial;
    }

    @BeforeEach
    public void initTest() {
        indAuditTrial = createEntity(em);
    }

    @Test
    @Transactional
    void createIndAuditTrial() throws Exception {
        int databaseSizeBeforeCreate = indAuditTrialRepository.findAll().size();
        // Create the IndAuditTrial
        restIndAuditTrialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isCreated());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeCreate + 1);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createIndAuditTrialWithExistingId() throws Exception {
        // Create the IndAuditTrial with an existing ID
        indAuditTrial.setId(1L);

        int databaseSizeBeforeCreate = indAuditTrialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndAuditTrialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setName(null);

        // Create the IndAuditTrial, which fails.

        restIndAuditTrialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setActivity(null);

        // Create the IndAuditTrial, which fails.

        restIndAuditTrialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setCustomerId(null);

        // Create the IndAuditTrial, which fails.

        restIndAuditTrialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setIndividualId(null);

        // Create the IndAuditTrial, which fails.

        restIndAuditTrialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setCreatedDate(null);

        // Create the IndAuditTrial, which fails.

        restIndAuditTrialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndAuditTrials() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indAuditTrial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get the indAuditTrial
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL_ID, indAuditTrial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indAuditTrial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIndAuditTrial() throws Exception {
        // Get the indAuditTrial
        restIndAuditTrialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial
        IndAuditTrial updatedIndAuditTrial = indAuditTrialRepository.findById(indAuditTrial.getId()).get();
        // Disconnect from session so that the updates on updatedIndAuditTrial are not directly saved in db
        em.detach(updatedIndAuditTrial);
        updatedIndAuditTrial
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndAuditTrial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndAuditTrial))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indAuditTrial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrial))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrial))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndAuditTrialWithPatch() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial using partial update
        IndAuditTrial partialUpdatedIndAuditTrial = new IndAuditTrial();
        partialUpdatedIndAuditTrial.setId(indAuditTrial.getId());

        partialUpdatedIndAuditTrial.activity(UPDATED_ACTIVITY).individualId(UPDATED_INDIVIDUAL_ID);

        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndAuditTrial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndAuditTrial))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateIndAuditTrialWithPatch() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial using partial update
        IndAuditTrial partialUpdatedIndAuditTrial = new IndAuditTrial();
        partialUpdatedIndAuditTrial.setId(indAuditTrial.getId());

        partialUpdatedIndAuditTrial
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndAuditTrial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndAuditTrial))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
        assertThat(testIndAuditTrial.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indAuditTrial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrial))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrial))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indAuditTrial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeDelete = indAuditTrialRepository.findAll().size();

        // Delete the indAuditTrial
        restIndAuditTrialMockMvc
            .perform(delete(ENTITY_API_URL_ID, indAuditTrial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
