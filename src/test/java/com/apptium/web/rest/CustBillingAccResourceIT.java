package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustBillingAcc;
import com.apptium.repository.CustBillingAccRepository;
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
 * Integration tests for the {@link CustBillingAccResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustBillingAccResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_BILLING_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_BILLING_ACCOUNT_NUMBER = 2L;

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final Integer DEFAULT_CURRENCY_CODE = 1;
    private static final Integer UPDATED_CURRENCY_CODE = 2;

    private static final String ENTITY_API_URL = "/api/cust-billing-accs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustBillingAccRepository custBillingAccRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustBillingAccMockMvc;

    private CustBillingAcc custBillingAcc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingAcc createEntity(EntityManager em) {
        CustBillingAcc custBillingAcc = new CustBillingAcc()
            .name(DEFAULT_NAME)
            .href(DEFAULT_HREF)
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .billingAccountNumber(DEFAULT_BILLING_ACCOUNT_NUMBER)
            .customerId(DEFAULT_CUSTOMER_ID)
            .currencyCode(DEFAULT_CURRENCY_CODE);
        return custBillingAcc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingAcc createUpdatedEntity(EntityManager em) {
        CustBillingAcc custBillingAcc = new CustBillingAcc()
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .billingAccountNumber(UPDATED_BILLING_ACCOUNT_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .currencyCode(UPDATED_CURRENCY_CODE);
        return custBillingAcc;
    }

    @BeforeEach
    public void initTest() {
        custBillingAcc = createEntity(em);
    }

    @Test
    @Transactional
    void createCustBillingAcc() throws Exception {
        int databaseSizeBeforeCreate = custBillingAccRepository.findAll().size();
        // Create the CustBillingAcc
        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isCreated());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeCreate + 1);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(DEFAULT_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void createCustBillingAccWithExistingId() throws Exception {
        // Create the CustBillingAcc with an existing ID
        custBillingAcc.setId(1L);

        int databaseSizeBeforeCreate = custBillingAccRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setName(null);

        // Create the CustBillingAcc, which fails.

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHrefIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setHref(null);

        // Create the CustBillingAcc, which fails.

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setStatus(null);

        // Create the CustBillingAcc, which fails.

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillingAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setBillingAccountNumber(null);

        // Create the CustBillingAcc, which fails.

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setCustomerId(null);

        // Create the CustBillingAcc, which fails.

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setCurrencyCode(null);

        // Create the CustBillingAcc, which fails.

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustBillingAccs() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillingAcc.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].billingAccountNumber").value(hasItem(DEFAULT_BILLING_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)));
    }

    @Test
    @Transactional
    void getCustBillingAcc() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get the custBillingAcc
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL_ID, custBillingAcc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custBillingAcc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.billingAccountNumber").value(DEFAULT_BILLING_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCustBillingAcc() throws Exception {
        // Get the custBillingAcc
        restCustBillingAccMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustBillingAcc() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();

        // Update the custBillingAcc
        CustBillingAcc updatedCustBillingAcc = custBillingAccRepository.findById(custBillingAcc.getId()).get();
        // Disconnect from session so that the updates on updatedCustBillingAcc are not directly saved in db
        em.detach(updatedCustBillingAcc);
        updatedCustBillingAcc
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .billingAccountNumber(UPDATED_BILLING_ACCOUNT_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .currencyCode(UPDATED_CURRENCY_CODE);

        restCustBillingAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustBillingAcc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustBillingAcc))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(UPDATED_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillingAcc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAcc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustBillingAccWithPatch() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();

        // Update the custBillingAcc using partial update
        CustBillingAcc partialUpdatedCustBillingAcc = new CustBillingAcc();
        partialUpdatedCustBillingAcc.setId(custBillingAcc.getId());

        partialUpdatedCustBillingAcc.description(UPDATED_DESCRIPTION).customerId(UPDATED_CUSTOMER_ID).currencyCode(UPDATED_CURRENCY_CODE);

        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingAcc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingAcc))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(DEFAULT_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCustBillingAccWithPatch() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();

        // Update the custBillingAcc using partial update
        CustBillingAcc partialUpdatedCustBillingAcc = new CustBillingAcc();
        partialUpdatedCustBillingAcc.setId(custBillingAcc.getId());

        partialUpdatedCustBillingAcc
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .billingAccountNumber(UPDATED_BILLING_ACCOUNT_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .currencyCode(UPDATED_CURRENCY_CODE);

        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingAcc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingAcc))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(UPDATED_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custBillingAcc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custBillingAcc))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustBillingAcc() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeDelete = custBillingAccRepository.findAll().size();

        // Delete the custBillingAcc
        restCustBillingAccMockMvc
            .perform(delete(ENTITY_API_URL_ID, custBillingAcc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
