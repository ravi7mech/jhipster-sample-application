package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustISVRef;
import com.apptium.repository.CustISVRefRepository;
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
 * Integration tests for the {@link CustISVRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustISVRefResourceIT {

    private static final Integer DEFAULT_ISVID = 1;
    private static final Integer UPDATED_ISVID = 2;

    private static final String DEFAULT_ISVNAME = "AAAAAAAAAA";
    private static final String UPDATED_ISVNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISVCUSTOMER_ID = 1;
    private static final Integer UPDATED_ISVCUSTOMER_ID = 2;

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-isv-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustISVRefRepository custISVRefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustISVRefMockMvc;

    private CustISVRef custISVRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVRef createEntity(EntityManager em) {
        CustISVRef custISVRef = new CustISVRef()
            .isvid(DEFAULT_ISVID)
            .isvname(DEFAULT_ISVNAME)
            .isvcustomerId(DEFAULT_ISVCUSTOMER_ID)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custISVRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVRef createUpdatedEntity(EntityManager em) {
        CustISVRef custISVRef = new CustISVRef()
            .isvid(UPDATED_ISVID)
            .isvname(UPDATED_ISVNAME)
            .isvcustomerId(UPDATED_ISVCUSTOMER_ID)
            .customerId(UPDATED_CUSTOMER_ID);
        return custISVRef;
    }

    @BeforeEach
    public void initTest() {
        custISVRef = createEntity(em);
    }

    @Test
    @Transactional
    void createCustISVRef() throws Exception {
        int databaseSizeBeforeCreate = custISVRefRepository.findAll().size();
        // Create the CustISVRef
        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRef)))
            .andExpect(status().isCreated());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeCreate + 1);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvid()).isEqualTo(DEFAULT_ISVID);
        assertThat(testCustISVRef.getIsvname()).isEqualTo(DEFAULT_ISVNAME);
        assertThat(testCustISVRef.getIsvcustomerId()).isEqualTo(DEFAULT_ISVCUSTOMER_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustISVRefWithExistingId() throws Exception {
        // Create the CustISVRef with an existing ID
        custISVRef.setId(1L);

        int databaseSizeBeforeCreate = custISVRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRef)))
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsvidIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setIsvid(null);

        // Create the CustISVRef, which fails.

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRef)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsvnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setIsvname(null);

        // Create the CustISVRef, which fails.

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRef)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsvcustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setIsvcustomerId(null);

        // Create the CustISVRef, which fails.

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRef)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setCustomerId(null);

        // Create the CustISVRef, which fails.

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRef)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustISVRefs() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custISVRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].isvid").value(hasItem(DEFAULT_ISVID)))
            .andExpect(jsonPath("$.[*].isvname").value(hasItem(DEFAULT_ISVNAME)))
            .andExpect(jsonPath("$.[*].isvcustomerId").value(hasItem(DEFAULT_ISVCUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustISVRef() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get the custISVRef
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL_ID, custISVRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custISVRef.getId().intValue()))
            .andExpect(jsonPath("$.isvid").value(DEFAULT_ISVID))
            .andExpect(jsonPath("$.isvname").value(DEFAULT_ISVNAME))
            .andExpect(jsonPath("$.isvcustomerId").value(DEFAULT_ISVCUSTOMER_ID))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustISVRef() throws Exception {
        // Get the custISVRef
        restCustISVRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustISVRef() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();

        // Update the custISVRef
        CustISVRef updatedCustISVRef = custISVRefRepository.findById(custISVRef.getId()).get();
        // Disconnect from session so that the updates on updatedCustISVRef are not directly saved in db
        em.detach(updatedCustISVRef);
        updatedCustISVRef
            .isvid(UPDATED_ISVID)
            .isvname(UPDATED_ISVNAME)
            .isvcustomerId(UPDATED_ISVCUSTOMER_ID)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustISVRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustISVRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustISVRef))
            )
            .andExpect(status().isOk());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvid()).isEqualTo(UPDATED_ISVID);
        assertThat(testCustISVRef.getIsvname()).isEqualTo(UPDATED_ISVNAME);
        assertThat(testCustISVRef.getIsvcustomerId()).isEqualTo(UPDATED_ISVCUSTOMER_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custISVRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRef)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustISVRefWithPatch() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();

        // Update the custISVRef using partial update
        CustISVRef partialUpdatedCustISVRef = new CustISVRef();
        partialUpdatedCustISVRef.setId(custISVRef.getId());

        partialUpdatedCustISVRef.isvid(UPDATED_ISVID).isvcustomerId(UPDATED_ISVCUSTOMER_ID).customerId(UPDATED_CUSTOMER_ID);

        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVRef))
            )
            .andExpect(status().isOk());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvid()).isEqualTo(UPDATED_ISVID);
        assertThat(testCustISVRef.getIsvname()).isEqualTo(DEFAULT_ISVNAME);
        assertThat(testCustISVRef.getIsvcustomerId()).isEqualTo(UPDATED_ISVCUSTOMER_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustISVRefWithPatch() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();

        // Update the custISVRef using partial update
        CustISVRef partialUpdatedCustISVRef = new CustISVRef();
        partialUpdatedCustISVRef.setId(custISVRef.getId());

        partialUpdatedCustISVRef
            .isvid(UPDATED_ISVID)
            .isvname(UPDATED_ISVNAME)
            .isvcustomerId(UPDATED_ISVCUSTOMER_ID)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVRef))
            )
            .andExpect(status().isOk());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvid()).isEqualTo(UPDATED_ISVID);
        assertThat(testCustISVRef.getIsvname()).isEqualTo(UPDATED_ISVNAME);
        assertThat(testCustISVRef.getIsvcustomerId()).isEqualTo(UPDATED_ISVCUSTOMER_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custISVRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custISVRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustISVRef() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeDelete = custISVRefRepository.findAll().size();

        // Delete the custISVRef
        restCustISVRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, custISVRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
