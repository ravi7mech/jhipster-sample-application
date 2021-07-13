package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.IndContactChar;
import com.apptium.repository.IndContactCharRepository;
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
 * Integration tests for the {@link IndContactCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndContactCharResourceIT {

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

    private static final Boolean DEFAULT_IS_EMAIL_VALID = false;
    private static final Boolean UPDATED_IS_EMAIL_VALID = true;

    private static final Boolean DEFAULT_IS_ADDRESS_VALID = false;
    private static final Boolean UPDATED_IS_ADDRESS_VALID = true;

    private static final Integer DEFAULT_INDIVIDUAL_CONTACT_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_CONTACT_ID = 2;

    private static final String ENTITY_API_URL = "/api/ind-contact-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndContactCharRepository indContactCharRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndContactCharMockMvc;

    private IndContactChar indContactChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContactChar createEntity(EntityManager em) {
        IndContactChar indContactChar = new IndContactChar()
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
            .individualContactId(DEFAULT_INDIVIDUAL_CONTACT_ID);
        return indContactChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContactChar createUpdatedEntity(EntityManager em) {
        IndContactChar indContactChar = new IndContactChar()
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
            .individualContactId(UPDATED_INDIVIDUAL_CONTACT_ID);
        return indContactChar;
    }

    @BeforeEach
    public void initTest() {
        indContactChar = createEntity(em);
    }

    @Test
    @Transactional
    void createIndContactChar() throws Exception {
        int databaseSizeBeforeCreate = indContactCharRepository.findAll().size();
        // Create the IndContactChar
        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isCreated());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeCreate + 1);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIndContactChar.getStreet1()).isEqualTo(DEFAULT_STREET_1);
        assertThat(testIndContactChar.getStreet2()).isEqualTo(DEFAULT_STREET_2);
        assertThat(testIndContactChar.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(DEFAULT_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(DEFAULT_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(DEFAULT_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndividualContactId()).isEqualTo(DEFAULT_INDIVIDUAL_CONTACT_ID);
    }

    @Test
    @Transactional
    void createIndContactCharWithExistingId() throws Exception {
        // Create the IndContactChar with an existing ID
        indContactChar.setId(1L);

        int databaseSizeBeforeCreate = indContactCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setType(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreet1IsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setStreet1(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreet2IsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setStreet2(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setCity(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateOrProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setStateOrProvince(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setCountry(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setPostCode(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setPhoneNumber(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setEmailAddress(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFaxNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setFaxNumber(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setLatitude(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setLongitude(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSvContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setSvContactId(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsAddressValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setIsAddressValid(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setIndividualContactId(null);

        // Create the IndContactChar, which fails.

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndContactChars() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indContactChar.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].isEmailValid").value(hasItem(DEFAULT_IS_EMAIL_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].isAddressValid").value(hasItem(DEFAULT_IS_ADDRESS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].individualContactId").value(hasItem(DEFAULT_INDIVIDUAL_CONTACT_ID)));
    }

    @Test
    @Transactional
    void getIndContactChar() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get the indContactChar
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL_ID, indContactChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indContactChar.getId().intValue()))
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
            .andExpect(jsonPath("$.isEmailValid").value(DEFAULT_IS_EMAIL_VALID.booleanValue()))
            .andExpect(jsonPath("$.isAddressValid").value(DEFAULT_IS_ADDRESS_VALID.booleanValue()))
            .andExpect(jsonPath("$.individualContactId").value(DEFAULT_INDIVIDUAL_CONTACT_ID));
    }

    @Test
    @Transactional
    void getNonExistingIndContactChar() throws Exception {
        // Get the indContactChar
        restIndContactCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndContactChar() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();

        // Update the indContactChar
        IndContactChar updatedIndContactChar = indContactCharRepository.findById(indContactChar.getId()).get();
        // Disconnect from session so that the updates on updatedIndContactChar are not directly saved in db
        em.detach(updatedIndContactChar);
        updatedIndContactChar
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
            .individualContactId(UPDATED_INDIVIDUAL_CONTACT_ID);

        restIndContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndContactChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndContactChar))
            )
            .andExpect(status().isOk());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContactChar.getStreet1()).isEqualTo(UPDATED_STREET_1);
        assertThat(testIndContactChar.getStreet2()).isEqualTo(UPDATED_STREET_2);
        assertThat(testIndContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndividualContactId()).isEqualTo(UPDATED_INDIVIDUAL_CONTACT_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indContactChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactChar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndContactCharWithPatch() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();

        // Update the indContactChar using partial update
        IndContactChar partialUpdatedIndContactChar = new IndContactChar();
        partialUpdatedIndContactChar.setId(indContactChar.getId());

        partialUpdatedIndContactChar
            .type(UPDATED_TYPE)
            .street2(UPDATED_STREET_2)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .longitude(UPDATED_LONGITUDE)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .individualContactId(UPDATED_INDIVIDUAL_CONTACT_ID);

        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContactChar))
            )
            .andExpect(status().isOk());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContactChar.getStreet1()).isEqualTo(DEFAULT_STREET_1);
        assertThat(testIndContactChar.getStreet2()).isEqualTo(UPDATED_STREET_2);
        assertThat(testIndContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndividualContactId()).isEqualTo(UPDATED_INDIVIDUAL_CONTACT_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndContactCharWithPatch() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();

        // Update the indContactChar using partial update
        IndContactChar partialUpdatedIndContactChar = new IndContactChar();
        partialUpdatedIndContactChar.setId(indContactChar.getId());

        partialUpdatedIndContactChar
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
            .individualContactId(UPDATED_INDIVIDUAL_CONTACT_ID);

        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContactChar))
            )
            .andExpect(status().isOk());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContactChar.getStreet1()).isEqualTo(UPDATED_STREET_1);
        assertThat(testIndContactChar.getStreet2()).isEqualTo(UPDATED_STREET_2);
        assertThat(testIndContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndividualContactId()).isEqualTo(UPDATED_INDIVIDUAL_CONTACT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indContactChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndContactChar() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeDelete = indContactCharRepository.findAll().size();

        // Delete the indContactChar
        restIndContactCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, indContactChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
