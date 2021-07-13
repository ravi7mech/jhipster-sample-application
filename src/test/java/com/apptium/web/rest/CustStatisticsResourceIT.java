package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustStatistics;
import com.apptium.repository.CustStatisticsRepository;
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
 * Integration tests for the {@link CustStatisticsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustStatisticsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUETYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUETYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-statistics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustStatisticsRepository custStatisticsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustStatisticsMockMvc;

    private CustStatistics custStatistics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustStatistics createEntity(EntityManager em) {
        CustStatistics custStatistics = new CustStatistics()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valuetype(DEFAULT_VALUETYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custStatistics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustStatistics createUpdatedEntity(EntityManager em) {
        CustStatistics custStatistics = new CustStatistics()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custStatistics;
    }

    @BeforeEach
    public void initTest() {
        custStatistics = createEntity(em);
    }

    @Test
    @Transactional
    void createCustStatistics() throws Exception {
        int databaseSizeBeforeCreate = custStatisticsRepository.findAll().size();
        // Create the CustStatistics
        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isCreated());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeCreate + 1);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustStatisticsWithExistingId() throws Exception {
        // Create the CustStatistics with an existing ID
        custStatistics.setId(1L);

        int databaseSizeBeforeCreate = custStatisticsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setName(null);

        // Create the CustStatistics, which fails.

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setValue(null);

        // Create the CustStatistics, which fails.

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValuetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setValuetype(null);

        // Create the CustStatistics, which fails.

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setCustomerId(null);

        // Create the CustStatistics, which fails.

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custStatistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get the custStatistics
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL_ID, custStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custStatistics.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valuetype").value(DEFAULT_VALUETYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustStatistics() throws Exception {
        // Get the custStatistics
        restCustStatisticsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();

        // Update the custStatistics
        CustStatistics updatedCustStatistics = custStatisticsRepository.findById(custStatistics.getId()).get();
        // Disconnect from session so that the updates on updatedCustStatistics are not directly saved in db
        em.detach(updatedCustStatistics);
        updatedCustStatistics.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustStatistics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustStatistics))
            )
            .andExpect(status().isOk());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custStatistics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatistics)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustStatisticsWithPatch() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();

        // Update the custStatistics using partial update
        CustStatistics partialUpdatedCustStatistics = new CustStatistics();
        partialUpdatedCustStatistics.setId(custStatistics.getId());

        partialUpdatedCustStatistics.value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE);

        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustStatistics))
            )
            .andExpect(status().isOk());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustStatisticsWithPatch() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();

        // Update the custStatistics using partial update
        CustStatistics partialUpdatedCustStatistics = new CustStatistics();
        partialUpdatedCustStatistics.setId(custStatistics.getId());

        partialUpdatedCustStatistics.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustStatistics))
            )
            .andExpect(status().isOk());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custStatistics))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeDelete = custStatisticsRepository.findAll().size();

        // Delete the custStatistics
        restCustStatisticsMockMvc
            .perform(delete(ENTITY_API_URL_ID, custStatistics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
