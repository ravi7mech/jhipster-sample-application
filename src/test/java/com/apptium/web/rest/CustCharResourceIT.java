package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustChar;
import com.apptium.repository.CustCharRepository;
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
 * Integration tests for the {@link CustCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUETYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUETYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustCharRepository custCharRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustCharMockMvc;

    private CustChar custChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustChar createEntity(EntityManager em) {
        CustChar custChar = new CustChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valuetype(DEFAULT_VALUETYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustChar createUpdatedEntity(EntityManager em) {
        CustChar custChar = new CustChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custChar;
    }

    @BeforeEach
    public void initTest() {
        custChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustChar() throws Exception {
        int databaseSizeBeforeCreate = custCharRepository.findAll().size();
        // Create the CustChar
        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isCreated());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustCharWithExistingId() throws Exception {
        // Create the CustChar with an existing ID
        custChar.setId(1L);

        int databaseSizeBeforeCreate = custCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setName(null);

        // Create the CustChar, which fails.

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setValue(null);

        // Create the CustChar, which fails.

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValuetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setValuetype(null);

        // Create the CustChar, which fails.

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setCustomerId(null);

        // Create the CustChar, which fails.

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustChars() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustChar() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get the custChar
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valuetype").value(DEFAULT_VALUETYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustChar() throws Exception {
        // Get the custChar
        restCustCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustChar() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();

        // Update the custChar
        CustChar updatedCustChar = custCharRepository.findById(custChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustChar are not directly saved in db
        em.detach(updatedCustChar);
        updatedCustChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustChar))
            )
            .andExpect(status().isOk());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustCharWithPatch() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();

        // Update the custChar using partial update
        CustChar partialUpdatedCustChar = new CustChar();
        partialUpdatedCustChar.setId(custChar.getId());

        partialUpdatedCustChar.name(UPDATED_NAME).value(UPDATED_VALUE);

        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustChar))
            )
            .andExpect(status().isOk());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustCharWithPatch() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();

        // Update the custChar using partial update
        CustChar partialUpdatedCustChar = new CustChar();
        partialUpdatedCustChar.setId(custChar.getId());

        partialUpdatedCustChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustChar))
            )
            .andExpect(status().isOk());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custChar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustChar() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeDelete = custCharRepository.findAll().size();

        // Delete the custChar
        restCustCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
