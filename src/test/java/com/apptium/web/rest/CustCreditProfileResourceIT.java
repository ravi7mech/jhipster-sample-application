package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustCreditProfile;
import com.apptium.repository.CustCreditProfileRepository;
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
 * Integration tests for the {@link CustCreditProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustCreditProfileResourceIT {

    private static final String DEFAULT_CUSTOMER_ID_TYPE_1 = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID_TYPE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID_REF_1 = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID_REF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID_TYPE_2 = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID_TYPE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID_REF_2 = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID_REF_2 = "BBBBBBBBBB";

    private static final Long DEFAULT_CREDIT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CREDIT_CARD_NUMBER = 2L;

    private static final Instant DEFAULT_CREDIT_PROFILE_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREDIT_PROFILE_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREDIT_RISK_RATING = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_RISK_RATING = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_RISK_RATING_DESC = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_RISK_RATING_DESC = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_SCORE = 1;
    private static final Integer UPDATED_CREDIT_SCORE = 2;

    private static final Instant DEFAULT_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-credit-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustCreditProfileRepository custCreditProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustCreditProfileMockMvc;

    private CustCreditProfile custCreditProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCreditProfile createEntity(EntityManager em) {
        CustCreditProfile custCreditProfile = new CustCreditProfile()
            .customerIDType1(DEFAULT_CUSTOMER_ID_TYPE_1)
            .customerIDRef1(DEFAULT_CUSTOMER_ID_REF_1)
            .customerIDType2(DEFAULT_CUSTOMER_ID_TYPE_2)
            .customerIDRef2(DEFAULT_CUSTOMER_ID_REF_2)
            .creditCardNumber(DEFAULT_CREDIT_CARD_NUMBER)
            .creditProfileData(DEFAULT_CREDIT_PROFILE_DATA)
            .creditRiskRating(DEFAULT_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(DEFAULT_CREDIT_RISK_RATING_DESC)
            .creditScore(DEFAULT_CREDIT_SCORE)
            .validUntil(DEFAULT_VALID_UNTIL)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custCreditProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCreditProfile createUpdatedEntity(EntityManager em) {
        CustCreditProfile custCreditProfile = new CustCreditProfile()
            .customerIDType1(UPDATED_CUSTOMER_ID_TYPE_1)
            .customerIDRef1(UPDATED_CUSTOMER_ID_REF_1)
            .customerIDType2(UPDATED_CUSTOMER_ID_TYPE_2)
            .customerIDRef2(UPDATED_CUSTOMER_ID_REF_2)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .creditRiskRating(UPDATED_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(UPDATED_CREDIT_RISK_RATING_DESC)
            .creditScore(UPDATED_CREDIT_SCORE)
            .validUntil(UPDATED_VALID_UNTIL)
            .customerId(UPDATED_CUSTOMER_ID);
        return custCreditProfile;
    }

    @BeforeEach
    public void initTest() {
        custCreditProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createCustCreditProfile() throws Exception {
        int databaseSizeBeforeCreate = custCreditProfileRepository.findAll().size();
        // Create the CustCreditProfile
        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isCreated());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustomerIDType1()).isEqualTo(DEFAULT_CUSTOMER_ID_TYPE_1);
        assertThat(testCustCreditProfile.getCustomerIDRef1()).isEqualTo(DEFAULT_CUSTOMER_ID_REF_1);
        assertThat(testCustCreditProfile.getCustomerIDType2()).isEqualTo(DEFAULT_CUSTOMER_ID_TYPE_2);
        assertThat(testCustCreditProfile.getCustomerIDRef2()).isEqualTo(DEFAULT_CUSTOMER_ID_REF_2);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(DEFAULT_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(DEFAULT_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(DEFAULT_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(DEFAULT_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(DEFAULT_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustCreditProfileWithExistingId() throws Exception {
        // Create the CustCreditProfile with an existing ID
        custCreditProfile.setId(1L);

        int databaseSizeBeforeCreate = custCreditProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustomerIDType1IsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustomerIDType1(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIDRef1IsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustomerIDRef1(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIDType2IsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustomerIDType2(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIDRef2IsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustomerIDRef2(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditCardNumber(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditProfileDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditProfileData(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditRiskRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditRiskRating(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditRiskRatingDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditRiskRatingDesc(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditScore(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidUntilIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setValidUntil(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustomerId(null);

        // Create the CustCreditProfile, which fails.

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustCreditProfiles() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custCreditProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerIDType1").value(hasItem(DEFAULT_CUSTOMER_ID_TYPE_1)))
            .andExpect(jsonPath("$.[*].customerIDRef1").value(hasItem(DEFAULT_CUSTOMER_ID_REF_1)))
            .andExpect(jsonPath("$.[*].customerIDType2").value(hasItem(DEFAULT_CUSTOMER_ID_TYPE_2)))
            .andExpect(jsonPath("$.[*].customerIDRef2").value(hasItem(DEFAULT_CUSTOMER_ID_REF_2)))
            .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].creditProfileData").value(hasItem(DEFAULT_CREDIT_PROFILE_DATA.toString())))
            .andExpect(jsonPath("$.[*].creditRiskRating").value(hasItem(DEFAULT_CREDIT_RISK_RATING)))
            .andExpect(jsonPath("$.[*].creditRiskRatingDesc").value(hasItem(DEFAULT_CREDIT_RISK_RATING_DESC)))
            .andExpect(jsonPath("$.[*].creditScore").value(hasItem(DEFAULT_CREDIT_SCORE)))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustCreditProfile() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get the custCreditProfile
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, custCreditProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custCreditProfile.getId().intValue()))
            .andExpect(jsonPath("$.customerIDType1").value(DEFAULT_CUSTOMER_ID_TYPE_1))
            .andExpect(jsonPath("$.customerIDRef1").value(DEFAULT_CUSTOMER_ID_REF_1))
            .andExpect(jsonPath("$.customerIDType2").value(DEFAULT_CUSTOMER_ID_TYPE_2))
            .andExpect(jsonPath("$.customerIDRef2").value(DEFAULT_CUSTOMER_ID_REF_2))
            .andExpect(jsonPath("$.creditCardNumber").value(DEFAULT_CREDIT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.creditProfileData").value(DEFAULT_CREDIT_PROFILE_DATA.toString()))
            .andExpect(jsonPath("$.creditRiskRating").value(DEFAULT_CREDIT_RISK_RATING))
            .andExpect(jsonPath("$.creditRiskRatingDesc").value(DEFAULT_CREDIT_RISK_RATING_DESC))
            .andExpect(jsonPath("$.creditScore").value(DEFAULT_CREDIT_SCORE))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustCreditProfile() throws Exception {
        // Get the custCreditProfile
        restCustCreditProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustCreditProfile() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();

        // Update the custCreditProfile
        CustCreditProfile updatedCustCreditProfile = custCreditProfileRepository.findById(custCreditProfile.getId()).get();
        // Disconnect from session so that the updates on updatedCustCreditProfile are not directly saved in db
        em.detach(updatedCustCreditProfile);
        updatedCustCreditProfile
            .customerIDType1(UPDATED_CUSTOMER_ID_TYPE_1)
            .customerIDRef1(UPDATED_CUSTOMER_ID_REF_1)
            .customerIDType2(UPDATED_CUSTOMER_ID_TYPE_2)
            .customerIDRef2(UPDATED_CUSTOMER_ID_REF_2)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .creditRiskRating(UPDATED_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(UPDATED_CREDIT_RISK_RATING_DESC)
            .creditScore(UPDATED_CREDIT_SCORE)
            .validUntil(UPDATED_VALID_UNTIL)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustCreditProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustCreditProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustomerIDType1()).isEqualTo(UPDATED_CUSTOMER_ID_TYPE_1);
        assertThat(testCustCreditProfile.getCustomerIDRef1()).isEqualTo(UPDATED_CUSTOMER_ID_REF_1);
        assertThat(testCustCreditProfile.getCustomerIDType2()).isEqualTo(UPDATED_CUSTOMER_ID_TYPE_2);
        assertThat(testCustCreditProfile.getCustomerIDRef2()).isEqualTo(UPDATED_CUSTOMER_ID_REF_2);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(UPDATED_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(UPDATED_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(UPDATED_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCreditProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustCreditProfileWithPatch() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();

        // Update the custCreditProfile using partial update
        CustCreditProfile partialUpdatedCustCreditProfile = new CustCreditProfile();
        partialUpdatedCustCreditProfile.setId(custCreditProfile.getId());

        partialUpdatedCustCreditProfile
            .customerIDRef1(UPDATED_CUSTOMER_ID_REF_1)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .validUntil(UPDATED_VALID_UNTIL);

        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCreditProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCreditProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustomerIDType1()).isEqualTo(DEFAULT_CUSTOMER_ID_TYPE_1);
        assertThat(testCustCreditProfile.getCustomerIDRef1()).isEqualTo(UPDATED_CUSTOMER_ID_REF_1);
        assertThat(testCustCreditProfile.getCustomerIDType2()).isEqualTo(DEFAULT_CUSTOMER_ID_TYPE_2);
        assertThat(testCustCreditProfile.getCustomerIDRef2()).isEqualTo(DEFAULT_CUSTOMER_ID_REF_2);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(UPDATED_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(DEFAULT_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(DEFAULT_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(DEFAULT_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustCreditProfileWithPatch() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();

        // Update the custCreditProfile using partial update
        CustCreditProfile partialUpdatedCustCreditProfile = new CustCreditProfile();
        partialUpdatedCustCreditProfile.setId(custCreditProfile.getId());

        partialUpdatedCustCreditProfile
            .customerIDType1(UPDATED_CUSTOMER_ID_TYPE_1)
            .customerIDRef1(UPDATED_CUSTOMER_ID_REF_1)
            .customerIDType2(UPDATED_CUSTOMER_ID_TYPE_2)
            .customerIDRef2(UPDATED_CUSTOMER_ID_REF_2)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .creditRiskRating(UPDATED_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(UPDATED_CREDIT_RISK_RATING_DESC)
            .creditScore(UPDATED_CREDIT_SCORE)
            .validUntil(UPDATED_VALID_UNTIL)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCreditProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCreditProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustomerIDType1()).isEqualTo(UPDATED_CUSTOMER_ID_TYPE_1);
        assertThat(testCustCreditProfile.getCustomerIDRef1()).isEqualTo(UPDATED_CUSTOMER_ID_REF_1);
        assertThat(testCustCreditProfile.getCustomerIDType2()).isEqualTo(UPDATED_CUSTOMER_ID_TYPE_2);
        assertThat(testCustCreditProfile.getCustomerIDRef2()).isEqualTo(UPDATED_CUSTOMER_ID_REF_2);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(UPDATED_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(UPDATED_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(UPDATED_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custCreditProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustCreditProfile() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeDelete = custCreditProfileRepository.findAll().size();

        // Delete the custCreditProfile
        restCustCreditProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, custCreditProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
