package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.AutoPay;
import com.apptium.repository.AutoPayRepository;
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
 * Integration tests for the {@link AutoPayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutoPayResourceIT {

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTO_PAY_ID = 1;
    private static final Integer UPDATED_AUTO_PAY_ID = 2;

    private static final Instant DEFAULT_DEBIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEBIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/auto-pays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AutoPayRepository autoPayRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutoPayMockMvc;

    private AutoPay autoPay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoPay createEntity(EntityManager em) {
        AutoPay autoPay = new AutoPay()
            .channel(DEFAULT_CHANNEL)
            .autoPayId(DEFAULT_AUTO_PAY_ID)
            .debitDate(DEFAULT_DEBIT_DATE)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .customerId(DEFAULT_CUSTOMER_ID);
        return autoPay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoPay createUpdatedEntity(EntityManager em) {
        AutoPay autoPay = new AutoPay()
            .channel(UPDATED_CHANNEL)
            .autoPayId(UPDATED_AUTO_PAY_ID)
            .debitDate(UPDATED_DEBIT_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .customerId(UPDATED_CUSTOMER_ID);
        return autoPay;
    }

    @BeforeEach
    public void initTest() {
        autoPay = createEntity(em);
    }

    @Test
    @Transactional
    void createAutoPay() throws Exception {
        int databaseSizeBeforeCreate = autoPayRepository.findAll().size();
        // Create the AutoPay
        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isCreated());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeCreate + 1);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(DEFAULT_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(DEFAULT_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAutoPay.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAutoPay.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAutoPay.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testAutoPay.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createAutoPayWithExistingId() throws Exception {
        // Create the AutoPay with an existing ID
        autoPay.setId(1L);

        int databaseSizeBeforeCreate = autoPayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setChannel(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAutoPayIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setAutoPayId(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDebitDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setDebitDate(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setStatus(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setCreatedDate(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setCreatedBy(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setUpdatedDate(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setUpdatedBy(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setCustomerId(null);

        // Create the AutoPay, which fails.

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAutoPays() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoPay.getId().intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].autoPayId").value(hasItem(DEFAULT_AUTO_PAY_ID)))
            .andExpect(jsonPath("$.[*].debitDate").value(hasItem(DEFAULT_DEBIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getAutoPay() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get the autoPay
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL_ID, autoPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autoPay.getId().intValue()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.autoPayId").value(DEFAULT_AUTO_PAY_ID))
            .andExpect(jsonPath("$.debitDate").value(DEFAULT_DEBIT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingAutoPay() throws Exception {
        // Get the autoPay
        restAutoPayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAutoPay() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();

        // Update the autoPay
        AutoPay updatedAutoPay = autoPayRepository.findById(autoPay.getId()).get();
        // Disconnect from session so that the updates on updatedAutoPay are not directly saved in db
        em.detach(updatedAutoPay);
        updatedAutoPay
            .channel(UPDATED_CHANNEL)
            .autoPayId(UPDATED_AUTO_PAY_ID)
            .debitDate(UPDATED_DEBIT_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .customerId(UPDATED_CUSTOMER_ID);

        restAutoPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutoPay.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAutoPay))
            )
            .andExpect(status().isOk());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(UPDATED_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(UPDATED_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAutoPay.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAutoPay.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAutoPay.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testAutoPay.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autoPay.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPay))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPay))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutoPayWithPatch() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();

        // Update the autoPay using partial update
        AutoPay partialUpdatedAutoPay = new AutoPay();
        partialUpdatedAutoPay.setId(autoPay.getId());

        partialUpdatedAutoPay
            .autoPayId(UPDATED_AUTO_PAY_ID)
            .debitDate(UPDATED_DEBIT_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutoPay))
            )
            .andExpect(status().isOk());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(UPDATED_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(UPDATED_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAutoPay.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAutoPay.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAutoPay.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testAutoPay.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateAutoPayWithPatch() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();

        // Update the autoPay using partial update
        AutoPay partialUpdatedAutoPay = new AutoPay();
        partialUpdatedAutoPay.setId(autoPay.getId());

        partialUpdatedAutoPay
            .channel(UPDATED_CHANNEL)
            .autoPayId(UPDATED_AUTO_PAY_ID)
            .debitDate(UPDATED_DEBIT_DATE)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .customerId(UPDATED_CUSTOMER_ID);

        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutoPay))
            )
            .andExpect(status().isOk());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(UPDATED_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(UPDATED_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAutoPay.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAutoPay.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAutoPay.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testAutoPay.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autoPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autoPay))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autoPay))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(autoPay)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutoPay() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeDelete = autoPayRepository.findAll().size();

        // Delete the autoPay
        restAutoPayMockMvc
            .perform(delete(ENTITY_API_URL_ID, autoPay.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
