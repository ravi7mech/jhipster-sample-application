package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustISVChar;
import com.apptium.repository.CustISVCharRepository;
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
 * Integration tests for the {@link CustISVCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustISVCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    private static final String DEFAULT_VALUETYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUETYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ISV_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ISV_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-isv-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustISVCharRepository custISVCharRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustISVCharMockMvc;

    private CustISVChar custISVChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVChar createEntity(EntityManager em) {
        CustISVChar custISVChar = new CustISVChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valuetype(DEFAULT_VALUETYPE)
            .customerISVId(DEFAULT_CUSTOMER_ISV_ID);
        return custISVChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVChar createUpdatedEntity(EntityManager em) {
        CustISVChar custISVChar = new CustISVChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .customerISVId(UPDATED_CUSTOMER_ISV_ID);
        return custISVChar;
    }

    @BeforeEach
    public void initTest() {
        custISVChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustISVChar() throws Exception {
        int databaseSizeBeforeCreate = custISVCharRepository.findAll().size();
        // Create the CustISVChar
        restCustISVCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVChar)))
            .andExpect(status().isCreated());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustISVChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustISVChar.getCustomerISVId()).isEqualTo(DEFAULT_CUSTOMER_ISV_ID);
    }

    @Test
    @Transactional
    void createCustISVCharWithExistingId() throws Exception {
        // Create the CustISVChar with an existing ID
        custISVChar.setId(1L);

        int databaseSizeBeforeCreate = custISVCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustISVCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVChar)))
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setName(null);

        // Create the CustISVChar, which fails.

        restCustISVCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVChar)))
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setValue(null);

        // Create the CustISVChar, which fails.

        restCustISVCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVChar)))
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValuetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setValuetype(null);

        // Create the CustISVChar, which fails.

        restCustISVCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVChar)))
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerISVIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setCustomerISVId(null);

        // Create the CustISVChar, which fails.

        restCustISVCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVChar)))
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustISVChars() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custISVChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].customerISVId").value(hasItem(DEFAULT_CUSTOMER_ISV_ID)));
    }

    @Test
    @Transactional
    void getCustISVChar() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get the custISVChar
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custISVChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custISVChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valuetype").value(DEFAULT_VALUETYPE))
            .andExpect(jsonPath("$.customerISVId").value(DEFAULT_CUSTOMER_ISV_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustISVChar() throws Exception {
        // Get the custISVChar
        restCustISVCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustISVChar() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();

        // Update the custISVChar
        CustISVChar updatedCustISVChar = custISVCharRepository.findById(custISVChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustISVChar are not directly saved in db
        em.detach(updatedCustISVChar);
        updatedCustISVChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerISVId(UPDATED_CUSTOMER_ISV_ID);

        restCustISVCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustISVChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustISVChar))
            )
            .andExpect(status().isOk());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustISVChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustISVChar.getCustomerISVId()).isEqualTo(UPDATED_CUSTOMER_ISV_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custISVChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVChar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustISVCharWithPatch() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();

        // Update the custISVChar using partial update
        CustISVChar partialUpdatedCustISVChar = new CustISVChar();
        partialUpdatedCustISVChar.setId(custISVChar.getId());

        partialUpdatedCustISVChar.valuetype(UPDATED_VALUETYPE).customerISVId(UPDATED_CUSTOMER_ISV_ID);

        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVChar))
            )
            .andExpect(status().isOk());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustISVChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustISVChar.getCustomerISVId()).isEqualTo(UPDATED_CUSTOMER_ISV_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustISVCharWithPatch() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();

        // Update the custISVChar using partial update
        CustISVChar partialUpdatedCustISVChar = new CustISVChar();
        partialUpdatedCustISVChar.setId(custISVChar.getId());

        partialUpdatedCustISVChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .customerISVId(UPDATED_CUSTOMER_ISV_ID);

        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVChar))
            )
            .andExpect(status().isOk());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustISVChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustISVChar.getCustomerISVId()).isEqualTo(UPDATED_CUSTOMER_ISV_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custISVChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custISVChar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustISVChar() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeDelete = custISVCharRepository.findAll().size();

        // Delete the custISVChar
        restCustISVCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custISVChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
