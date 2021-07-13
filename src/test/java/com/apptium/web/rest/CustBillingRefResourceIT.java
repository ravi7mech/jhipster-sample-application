package com.apptium.web.rest;

import static com.apptium.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustBillingRef;
import com.apptium.repository.CustBillingRefRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CustBillingRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustBillingRefResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT_DUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_DUE = new BigDecimal(2);

    private static final Instant DEFAULT_BILL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BILL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_BILL_NO = 1L;
    private static final Long UPDATED_BILL_NO = 2L;

    private static final Instant DEFAULT_BILLING_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BILLING_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NEXT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEXT_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PAYMENT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAX_EXCLUDED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_EXCLUDED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX_INCLUDED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_INCLUDED_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-billing-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustBillingRefRepository custBillingRefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustBillingRefMockMvc;

    private CustBillingRef custBillingRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingRef createEntity(EntityManager em) {
        CustBillingRef custBillingRef = new CustBillingRef()
            .amountDue(DEFAULT_AMOUNT_DUE)
            .billDate(DEFAULT_BILL_DATE)
            .billNo(DEFAULT_BILL_NO)
            .billingPeriod(DEFAULT_BILLING_PERIOD)
            .category(DEFAULT_CATEGORY)
            .href(DEFAULT_HREF)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .nextUpdatedDate(DEFAULT_NEXT_UPDATED_DATE)
            .paymentDueDate(DEFAULT_PAYMENT_DUE_DATE)
            .state(DEFAULT_STATE)
            .taxExcludedAmount(DEFAULT_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(DEFAULT_TAX_INCLUDED_AMOUNT)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custBillingRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingRef createUpdatedEntity(EntityManager em) {
        CustBillingRef custBillingRef = new CustBillingRef()
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .billingPeriod(UPDATED_BILLING_PERIOD)
            .category(UPDATED_CATEGORY)
            .href(UPDATED_HREF)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .nextUpdatedDate(UPDATED_NEXT_UPDATED_DATE)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE)
            .taxExcludedAmount(UPDATED_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID);
        return custBillingRef;
    }

    @BeforeEach
    public void initTest() {
        custBillingRef = createEntity(em);
    }

    @Test
    @Transactional
    void createCustBillingRef() throws Exception {
        int databaseSizeBeforeCreate = custBillingRefRepository.findAll().size();
        // Create the CustBillingRef
        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isCreated());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeCreate + 1);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualByComparingTo(DEFAULT_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(DEFAULT_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(DEFAULT_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(DEFAULT_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualByComparingTo(DEFAULT_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualByComparingTo(DEFAULT_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustBillingRefWithExistingId() throws Exception {
        // Create the CustBillingRef with an existing ID
        custBillingRef.setId(1L);

        int databaseSizeBeforeCreate = custBillingRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountDueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setAmountDue(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setBillDate(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setBillNo(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillingPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setBillingPeriod(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setCategory(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHrefIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setHref(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setLastUpdatedDate(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNextUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setNextUpdatedDate(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentDueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setPaymentDueDate(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setState(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxExcludedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setTaxExcludedAmount(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxIncludedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setTaxIncludedAmount(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setCustomerId(null);

        // Create the CustBillingRef, which fails.

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustBillingRefs() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillingRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountDue").value(hasItem(sameNumber(DEFAULT_AMOUNT_DUE))))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.intValue())))
            .andExpect(jsonPath("$.[*].billingPeriod").value(hasItem(DEFAULT_BILLING_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextUpdatedDate").value(hasItem(DEFAULT_NEXT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDueDate").value(hasItem(DEFAULT_PAYMENT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].taxExcludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_EXCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].taxIncludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustBillingRef() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get the custBillingRef
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL_ID, custBillingRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custBillingRef.getId().intValue()))
            .andExpect(jsonPath("$.amountDue").value(sameNumber(DEFAULT_AMOUNT_DUE)))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.intValue()))
            .andExpect(jsonPath("$.billingPeriod").value(DEFAULT_BILLING_PERIOD.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.nextUpdatedDate").value(DEFAULT_NEXT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.paymentDueDate").value(DEFAULT_PAYMENT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.taxExcludedAmount").value(sameNumber(DEFAULT_TAX_EXCLUDED_AMOUNT)))
            .andExpect(jsonPath("$.taxIncludedAmount").value(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT)))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustBillingRef() throws Exception {
        // Get the custBillingRef
        restCustBillingRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustBillingRef() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();

        // Update the custBillingRef
        CustBillingRef updatedCustBillingRef = custBillingRefRepository.findById(custBillingRef.getId()).get();
        // Disconnect from session so that the updates on updatedCustBillingRef are not directly saved in db
        em.detach(updatedCustBillingRef);
        updatedCustBillingRef
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .billingPeriod(UPDATED_BILLING_PERIOD)
            .category(UPDATED_CATEGORY)
            .href(UPDATED_HREF)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .nextUpdatedDate(UPDATED_NEXT_UPDATED_DATE)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE)
            .taxExcludedAmount(UPDATED_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustBillingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustBillingRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustBillingRef))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualTo(UPDATED_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(UPDATED_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(UPDATED_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(UPDATED_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualTo(UPDATED_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualTo(UPDATED_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillingRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRef)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustBillingRefWithPatch() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();

        // Update the custBillingRef using partial update
        CustBillingRef partialUpdatedCustBillingRef = new CustBillingRef();
        partialUpdatedCustBillingRef.setId(custBillingRef.getId());

        partialUpdatedCustBillingRef
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .category(UPDATED_CATEGORY)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE);

        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingRef))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualByComparingTo(UPDATED_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(DEFAULT_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(DEFAULT_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(UPDATED_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualByComparingTo(DEFAULT_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualByComparingTo(DEFAULT_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustBillingRefWithPatch() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();

        // Update the custBillingRef using partial update
        CustBillingRef partialUpdatedCustBillingRef = new CustBillingRef();
        partialUpdatedCustBillingRef.setId(custBillingRef.getId());

        partialUpdatedCustBillingRef
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .billingPeriod(UPDATED_BILLING_PERIOD)
            .category(UPDATED_CATEGORY)
            .href(UPDATED_HREF)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .nextUpdatedDate(UPDATED_NEXT_UPDATED_DATE)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE)
            .taxExcludedAmount(UPDATED_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingRef))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualByComparingTo(UPDATED_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(UPDATED_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(UPDATED_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(UPDATED_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualByComparingTo(UPDATED_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualByComparingTo(UPDATED_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custBillingRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custBillingRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustBillingRef() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeDelete = custBillingRefRepository.findAll().size();

        // Delete the custBillingRef
        restCustBillingRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, custBillingRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
