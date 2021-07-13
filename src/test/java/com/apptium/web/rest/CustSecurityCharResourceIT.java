package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustSecurityChar;
import com.apptium.repository.CustSecurityCharRepository;
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
 * Integration tests for the {@link CustSecurityCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustSecurityCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUETYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUETYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-security-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustSecurityCharRepository custSecurityCharRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustSecurityCharMockMvc;

    private CustSecurityChar custSecurityChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustSecurityChar createEntity(EntityManager em) {
        CustSecurityChar custSecurityChar = new CustSecurityChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valuetype(DEFAULT_VALUETYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custSecurityChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustSecurityChar createUpdatedEntity(EntityManager em) {
        CustSecurityChar custSecurityChar = new CustSecurityChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custSecurityChar;
    }

    @BeforeEach
    public void initTest() {
        custSecurityChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustSecurityChar() throws Exception {
        int databaseSizeBeforeCreate = custSecurityCharRepository.findAll().size();
        // Create the CustSecurityChar
        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isCreated());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustSecurityChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustSecurityCharWithExistingId() throws Exception {
        // Create the CustSecurityChar with an existing ID
        custSecurityChar.setId(1L);

        int databaseSizeBeforeCreate = custSecurityCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setName(null);

        // Create the CustSecurityChar, which fails.

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setValue(null);

        // Create the CustSecurityChar, which fails.

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValuetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setValuetype(null);

        // Create the CustSecurityChar, which fails.

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setCustomerId(null);

        // Create the CustSecurityChar, which fails.

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustSecurityChars() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custSecurityChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustSecurityChar() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get the custSecurityChar
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custSecurityChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custSecurityChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valuetype").value(DEFAULT_VALUETYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustSecurityChar() throws Exception {
        // Get the custSecurityChar
        restCustSecurityCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustSecurityChar() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();

        // Update the custSecurityChar
        CustSecurityChar updatedCustSecurityChar = custSecurityCharRepository.findById(custSecurityChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustSecurityChar are not directly saved in db
        em.detach(updatedCustSecurityChar);
        updatedCustSecurityChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustSecurityChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustSecurityChar))
            )
            .andExpect(status().isOk());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustSecurityChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custSecurityChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustSecurityCharWithPatch() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();

        // Update the custSecurityChar using partial update
        CustSecurityChar partialUpdatedCustSecurityChar = new CustSecurityChar();
        partialUpdatedCustSecurityChar.setId(custSecurityChar.getId());

        partialUpdatedCustSecurityChar.value(UPDATED_VALUE).customerId(UPDATED_CUSTOMER_ID);

        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustSecurityChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustSecurityChar))
            )
            .andExpect(status().isOk());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustSecurityChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustSecurityCharWithPatch() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();

        // Update the custSecurityChar using partial update
        CustSecurityChar partialUpdatedCustSecurityChar = new CustSecurityChar();
        partialUpdatedCustSecurityChar.setId(custSecurityChar.getId());

        partialUpdatedCustSecurityChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustSecurityChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustSecurityChar))
            )
            .andExpect(status().isOk());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustSecurityChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custSecurityChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustSecurityChar() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeDelete = custSecurityCharRepository.findAll().size();

        // Delete the custSecurityChar
        restCustSecurityCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custSecurityChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
