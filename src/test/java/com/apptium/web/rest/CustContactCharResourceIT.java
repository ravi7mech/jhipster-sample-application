package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustContactChar;
import com.apptium.repository.CustContactCharRepository;
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
 * Integration tests for the {@link CustContactCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustContactCharResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_1 = "AAAAAAAAAA";
    private static final String UPDATED_STREET_1 = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_2 = "AAAAAAAAAA";
    private static final String UPDATED_STREET_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_OR_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_OR_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_CODE = 1L;
    private static final Long UPDATED_POST_CODE = 2L;

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_FAX_NUMBER = 1L;
    private static final Long UPDATED_FAX_NUMBER = 2L;

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Integer DEFAULT_SV_CONTACT_ID = 1;
    private static final Integer UPDATED_SV_CONTACT_ID = 2;

    private static final Integer DEFAULT_IS_EMAIL_VALID = 1;
    private static final Integer UPDATED_IS_EMAIL_VALID = 2;

    private static final Boolean DEFAULT_IS_ADDRESS_VALID = false;
    private static final Boolean UPDATED_IS_ADDRESS_VALID = true;

    private static final Integer DEFAULT_CUST_CONTACT_MEDIUM_ID = 1;
    private static final Integer UPDATED_CUST_CONTACT_MEDIUM_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-contact-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustContactCharRepository custContactCharRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustContactCharMockMvc;

    private CustContactChar custContactChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContactChar createEntity(EntityManager em) {
        CustContactChar custContactChar = new CustContactChar()
            .type(DEFAULT_TYPE)
            .street1(DEFAULT_STREET_1)
            .street2(DEFAULT_STREET_2)
            .city(DEFAULT_CITY)
            .stateOrProvince(DEFAULT_STATE_OR_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .postCode(DEFAULT_POST_CODE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .svContactId(DEFAULT_SV_CONTACT_ID)
            .isEmailValid(DEFAULT_IS_EMAIL_VALID)
            .isAddressValid(DEFAULT_IS_ADDRESS_VALID)
            .custContactMediumId(DEFAULT_CUST_CONTACT_MEDIUM_ID);
        return custContactChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContactChar createUpdatedEntity(EntityManager em) {
        CustContactChar custContactChar = new CustContactChar()
            .type(UPDATED_TYPE)
            .street1(UPDATED_STREET_1)
            .street2(UPDATED_STREET_2)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .svContactId(UPDATED_SV_CONTACT_ID)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .custContactMediumId(UPDATED_CUST_CONTACT_MEDIUM_ID);
        return custContactChar;
    }

    @BeforeEach
    public void initTest() {
        custContactChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustContactChar() throws Exception {
        int databaseSizeBeforeCreate = custContactCharRepository.findAll().size();
        // Create the CustContactChar
        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isCreated());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContactChar.getStreet1()).isEqualTo(DEFAULT_STREET_1);
        assertThat(testCustContactChar.getStreet2()).isEqualTo(DEFAULT_STREET_2);
        assertThat(testCustContactChar.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(DEFAULT_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(DEFAULT_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(DEFAULT_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustContactMediumId()).isEqualTo(DEFAULT_CUST_CONTACT_MEDIUM_ID);
    }

    @Test
    @Transactional
    void createCustContactCharWithExistingId() throws Exception {
        // Create the CustContactChar with an existing ID
        custContactChar.setId(1L);

        int databaseSizeBeforeCreate = custContactCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setType(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreet1IsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setStreet1(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreet2IsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setStreet2(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setCity(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateOrProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setStateOrProvince(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setCountry(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setPostCode(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setPhoneNumber(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setEmailAddress(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFaxNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setFaxNumber(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setLatitude(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setLongitude(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSvContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setSvContactId(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsAddressValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setIsAddressValid(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustContactMediumIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setCustContactMediumId(null);

        // Create the CustContactChar, which fails.

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustContactChars() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custContactChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].street1").value(hasItem(DEFAULT_STREET_1)))
            .andExpect(jsonPath("$.[*].street2").value(hasItem(DEFAULT_STREET_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateOrProvince").value(hasItem(DEFAULT_STATE_OR_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].svContactId").value(hasItem(DEFAULT_SV_CONTACT_ID)))
            .andExpect(jsonPath("$.[*].isEmailValid").value(hasItem(DEFAULT_IS_EMAIL_VALID)))
            .andExpect(jsonPath("$.[*].isAddressValid").value(hasItem(DEFAULT_IS_ADDRESS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].custContactMediumId").value(hasItem(DEFAULT_CUST_CONTACT_MEDIUM_ID)));
    }

    @Test
    @Transactional
    void getCustContactChar() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get the custContactChar
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custContactChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custContactChar.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.street1").value(DEFAULT_STREET_1))
            .andExpect(jsonPath("$.street2").value(DEFAULT_STREET_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateOrProvince").value(DEFAULT_STATE_OR_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER.intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.svContactId").value(DEFAULT_SV_CONTACT_ID))
            .andExpect(jsonPath("$.isEmailValid").value(DEFAULT_IS_EMAIL_VALID))
            .andExpect(jsonPath("$.isAddressValid").value(DEFAULT_IS_ADDRESS_VALID.booleanValue()))
            .andExpect(jsonPath("$.custContactMediumId").value(DEFAULT_CUST_CONTACT_MEDIUM_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustContactChar() throws Exception {
        // Get the custContactChar
        restCustContactCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustContactChar() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();

        // Update the custContactChar
        CustContactChar updatedCustContactChar = custContactCharRepository.findById(custContactChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustContactChar are not directly saved in db
        em.detach(updatedCustContactChar);
        updatedCustContactChar
            .type(UPDATED_TYPE)
            .street1(UPDATED_STREET_1)
            .street2(UPDATED_STREET_2)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .svContactId(UPDATED_SV_CONTACT_ID)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .custContactMediumId(UPDATED_CUST_CONTACT_MEDIUM_ID);

        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustContactChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustContactChar))
            )
            .andExpect(status().isOk());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContactChar.getStreet1()).isEqualTo(UPDATED_STREET_1);
        assertThat(testCustContactChar.getStreet2()).isEqualTo(UPDATED_STREET_2);
        assertThat(testCustContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustContactMediumId()).isEqualTo(UPDATED_CUST_CONTACT_MEDIUM_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custContactChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustContactCharWithPatch() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();

        // Update the custContactChar using partial update
        CustContactChar partialUpdatedCustContactChar = new CustContactChar();
        partialUpdatedCustContactChar.setId(custContactChar.getId());

        partialUpdatedCustContactChar
            .street1(UPDATED_STREET_1)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .custContactMediumId(UPDATED_CUST_CONTACT_MEDIUM_ID);

        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContactChar))
            )
            .andExpect(status().isOk());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContactChar.getStreet1()).isEqualTo(UPDATED_STREET_1);
        assertThat(testCustContactChar.getStreet2()).isEqualTo(DEFAULT_STREET_2);
        assertThat(testCustContactChar.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(DEFAULT_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(DEFAULT_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(DEFAULT_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustContactMediumId()).isEqualTo(UPDATED_CUST_CONTACT_MEDIUM_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustContactCharWithPatch() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();

        // Update the custContactChar using partial update
        CustContactChar partialUpdatedCustContactChar = new CustContactChar();
        partialUpdatedCustContactChar.setId(custContactChar.getId());

        partialUpdatedCustContactChar
            .type(UPDATED_TYPE)
            .street1(UPDATED_STREET_1)
            .street2(UPDATED_STREET_2)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .svContactId(UPDATED_SV_CONTACT_ID)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .custContactMediumId(UPDATED_CUST_CONTACT_MEDIUM_ID);

        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContactChar))
            )
            .andExpect(status().isOk());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContactChar.getStreet1()).isEqualTo(UPDATED_STREET_1);
        assertThat(testCustContactChar.getStreet2()).isEqualTo(UPDATED_STREET_2);
        assertThat(testCustContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustContactMediumId()).isEqualTo(UPDATED_CUST_CONTACT_MEDIUM_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustContactChar() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeDelete = custContactCharRepository.findAll().size();

        // Delete the custContactChar
        restCustContactCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custContactChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
