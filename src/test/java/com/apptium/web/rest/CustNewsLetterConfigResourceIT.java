package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustNewsLetterConfig;
import com.apptium.repository.CustNewsLetterConfigRepository;
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
 * Integration tests for the {@link CustNewsLetterConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustNewsLetterConfigResourceIT {

    private static final Integer DEFAULT_NEW_LETTER_TYPE_ID = 1;
    private static final Integer UPDATED_NEW_LETTER_TYPE_ID = 2;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-news-letter-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustNewsLetterConfigRepository custNewsLetterConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustNewsLetterConfigMockMvc;

    private CustNewsLetterConfig custNewsLetterConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustNewsLetterConfig createEntity(EntityManager em) {
        CustNewsLetterConfig custNewsLetterConfig = new CustNewsLetterConfig()
            .newLetterTypeId(DEFAULT_NEW_LETTER_TYPE_ID)
            .value(DEFAULT_VALUE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custNewsLetterConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustNewsLetterConfig createUpdatedEntity(EntityManager em) {
        CustNewsLetterConfig custNewsLetterConfig = new CustNewsLetterConfig()
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custNewsLetterConfig;
    }

    @BeforeEach
    public void initTest() {
        custNewsLetterConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeCreate = custNewsLetterConfigRepository.findAll().size();
        // Create the CustNewsLetterConfig
        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isCreated());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeCreate + 1);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(DEFAULT_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustNewsLetterConfigWithExistingId() throws Exception {
        // Create the CustNewsLetterConfig with an existing ID
        custNewsLetterConfig.setId(1L);

        int databaseSizeBeforeCreate = custNewsLetterConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNewLetterTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custNewsLetterConfigRepository.findAll().size();
        // set the field null
        custNewsLetterConfig.setNewLetterTypeId(null);

        // Create the CustNewsLetterConfig, which fails.

        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custNewsLetterConfigRepository.findAll().size();
        // set the field null
        custNewsLetterConfig.setValue(null);

        // Create the CustNewsLetterConfig, which fails.

        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custNewsLetterConfigRepository.findAll().size();
        // set the field null
        custNewsLetterConfig.setCustomerId(null);

        // Create the CustNewsLetterConfig, which fails.

        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigs() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custNewsLetterConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterTypeId").value(hasItem(DEFAULT_NEW_LETTER_TYPE_ID)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustNewsLetterConfig() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get the custNewsLetterConfig
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, custNewsLetterConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custNewsLetterConfig.getId().intValue()))
            .andExpect(jsonPath("$.newLetterTypeId").value(DEFAULT_NEW_LETTER_TYPE_ID))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustNewsLetterConfig() throws Exception {
        // Get the custNewsLetterConfig
        restCustNewsLetterConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustNewsLetterConfig() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();

        // Update the custNewsLetterConfig
        CustNewsLetterConfig updatedCustNewsLetterConfig = custNewsLetterConfigRepository.findById(custNewsLetterConfig.getId()).get();
        // Disconnect from session so that the updates on updatedCustNewsLetterConfig are not directly saved in db
        em.detach(updatedCustNewsLetterConfig);
        updatedCustNewsLetterConfig.newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID).value(UPDATED_VALUE).customerId(UPDATED_CUSTOMER_ID);

        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustNewsLetterConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustNewsLetterConfig))
            )
            .andExpect(status().isOk());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custNewsLetterConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustNewsLetterConfigWithPatch() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();

        // Update the custNewsLetterConfig using partial update
        CustNewsLetterConfig partialUpdatedCustNewsLetterConfig = new CustNewsLetterConfig();
        partialUpdatedCustNewsLetterConfig.setId(custNewsLetterConfig.getId());

        partialUpdatedCustNewsLetterConfig.value(UPDATED_VALUE);

        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustNewsLetterConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustNewsLetterConfig))
            )
            .andExpect(status().isOk());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(DEFAULT_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustNewsLetterConfigWithPatch() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();

        // Update the custNewsLetterConfig using partial update
        CustNewsLetterConfig partialUpdatedCustNewsLetterConfig = new CustNewsLetterConfig();
        partialUpdatedCustNewsLetterConfig.setId(custNewsLetterConfig.getId());

        partialUpdatedCustNewsLetterConfig.newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID).value(UPDATED_VALUE).customerId(UPDATED_CUSTOMER_ID);

        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustNewsLetterConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustNewsLetterConfig))
            )
            .andExpect(status().isOk());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custNewsLetterConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfig))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustNewsLetterConfig() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeDelete = custNewsLetterConfigRepository.findAll().size();

        // Delete the custNewsLetterConfig
        restCustNewsLetterConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, custNewsLetterConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
