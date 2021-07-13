package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustContact;
import com.apptium.repository.CustContactRepository;
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
 * Integration tests for the {@link CustContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustContactResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PREFERRED = false;
    private static final Boolean UPDATED_PREFERRED = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustContactRepository custContactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustContactMockMvc;

    private CustContact custContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContact createEntity(EntityManager em) {
        CustContact custContact = new CustContact()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .preferred(DEFAULT_PREFERRED)
            .type(DEFAULT_TYPE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContact createUpdatedEntity(EntityManager em) {
        CustContact custContact = new CustContact()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);
        return custContact;
    }

    @BeforeEach
    public void initTest() {
        custContact = createEntity(em);
    }

    @Test
    @Transactional
    void createCustContact() throws Exception {
        int databaseSizeBeforeCreate = custContactRepository.findAll().size();
        // Create the CustContact
        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isCreated());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeCreate + 1);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustContactWithExistingId() throws Exception {
        // Create the CustContact with an existing ID
        custContact.setId(1L);

        int databaseSizeBeforeCreate = custContactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setName(null);

        // Create the CustContact, which fails.

        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPreferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setPreferred(null);

        // Create the CustContact, which fails.

        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setType(null);

        // Create the CustContact, which fails.

        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setValidFrom(null);

        // Create the CustContact, which fails.

        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setValidTo(null);

        // Create the CustContact, which fails.

        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setCustomerId(null);

        // Create the CustContact, which fails.

        restCustContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustContacts() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get the custContact
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL_ID, custContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custContact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.preferred").value(DEFAULT_PREFERRED.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustContact() throws Exception {
        // Get the custContact
        restCustContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Update the custContact
        CustContact updatedCustContact = custContactRepository.findById(custContact.getId()).get();
        // Disconnect from session so that the updates on updatedCustContact are not directly saved in db
        em.detach(updatedCustContact);
        updatedCustContact
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustContact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustContact))
            )
            .andExpect(status().isOk());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custContact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustContactWithPatch() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Update the custContact using partial update
        CustContact partialUpdatedCustContact = new CustContact();
        partialUpdatedCustContact.setId(custContact.getId());

        partialUpdatedCustContact
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContact))
            )
            .andExpect(status().isOk());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustContactWithPatch() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Update the custContact using partial update
        CustContact partialUpdatedCustContact = new CustContact();
        partialUpdatedCustContact.setId(custContact.getId());

        partialUpdatedCustContact
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContact))
            )
            .andExpect(status().isOk());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custContact))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeDelete = custContactRepository.findAll().size();

        // Delete the custContact
        restCustContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, custContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
