package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustPasswordChar;
import com.apptium.repository.CustPasswordCharRepository;
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
 * Integration tests for the {@link CustPasswordCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustPasswordCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUETYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUETYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-password-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustPasswordCharRepository custPasswordCharRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustPasswordCharMockMvc;

    private CustPasswordChar custPasswordChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPasswordChar createEntity(EntityManager em) {
        CustPasswordChar custPasswordChar = new CustPasswordChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valuetype(DEFAULT_VALUETYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custPasswordChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPasswordChar createUpdatedEntity(EntityManager em) {
        CustPasswordChar custPasswordChar = new CustPasswordChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custPasswordChar;
    }

    @BeforeEach
    public void initTest() {
        custPasswordChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustPasswordChar() throws Exception {
        int databaseSizeBeforeCreate = custPasswordCharRepository.findAll().size();
        // Create the CustPasswordChar
        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isCreated());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustPasswordChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustPasswordCharWithExistingId() throws Exception {
        // Create the CustPasswordChar with an existing ID
        custPasswordChar.setId(1L);

        int databaseSizeBeforeCreate = custPasswordCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setName(null);

        // Create the CustPasswordChar, which fails.

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setValue(null);

        // Create the CustPasswordChar, which fails.

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValuetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setValuetype(null);

        // Create the CustPasswordChar, which fails.

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setCustomerId(null);

        // Create the CustPasswordChar, which fails.

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustPasswordChars() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custPasswordChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustPasswordChar() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get the custPasswordChar
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custPasswordChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custPasswordChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valuetype").value(DEFAULT_VALUETYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustPasswordChar() throws Exception {
        // Get the custPasswordChar
        restCustPasswordCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustPasswordChar() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();

        // Update the custPasswordChar
        CustPasswordChar updatedCustPasswordChar = custPasswordCharRepository.findById(custPasswordChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustPasswordChar are not directly saved in db
        em.detach(updatedCustPasswordChar);
        updatedCustPasswordChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustPasswordChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustPasswordChar))
            )
            .andExpect(status().isOk());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustPasswordChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custPasswordChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustPasswordCharWithPatch() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();

        // Update the custPasswordChar using partial update
        CustPasswordChar partialUpdatedCustPasswordChar = new CustPasswordChar();
        partialUpdatedCustPasswordChar.setId(custPasswordChar.getId());

        partialUpdatedCustPasswordChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE);

        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPasswordChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPasswordChar))
            )
            .andExpect(status().isOk());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustPasswordChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustPasswordCharWithPatch() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();

        // Update the custPasswordChar using partial update
        CustPasswordChar partialUpdatedCustPasswordChar = new CustPasswordChar();
        partialUpdatedCustPasswordChar.setId(custPasswordChar.getId());

        partialUpdatedCustPasswordChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPasswordChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPasswordChar))
            )
            .andExpect(status().isOk());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustPasswordChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custPasswordChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustPasswordChar() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeDelete = custPasswordCharRepository.findAll().size();

        // Delete the custPasswordChar
        restCustPasswordCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custPasswordChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
