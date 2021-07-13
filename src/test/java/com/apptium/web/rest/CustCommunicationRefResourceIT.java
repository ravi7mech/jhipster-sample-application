package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustCommunicationRef;
import com.apptium.repository.CustCommunicationRefRepository;
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
 * Integration tests for the {@link CustCommunicationRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustCommunicationRefResourceIT {

    private static final String DEFAULT_CUSTOMER_NOTIFICATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NOTIFICATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-communication-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustCommunicationRefRepository custCommunicationRefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustCommunicationRefMockMvc;

    private CustCommunicationRef custCommunicationRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCommunicationRef createEntity(EntityManager em) {
        CustCommunicationRef custCommunicationRef = new CustCommunicationRef()
            .customerNotificationId(DEFAULT_CUSTOMER_NOTIFICATION_ID)
            .role(DEFAULT_ROLE)
            .status(DEFAULT_STATUS)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custCommunicationRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCommunicationRef createUpdatedEntity(EntityManager em) {
        CustCommunicationRef custCommunicationRef = new CustCommunicationRef()
            .customerNotificationId(UPDATED_CUSTOMER_NOTIFICATION_ID)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);
        return custCommunicationRef;
    }

    @BeforeEach
    public void initTest() {
        custCommunicationRef = createEntity(em);
    }

    @Test
    @Transactional
    void createCustCommunicationRef() throws Exception {
        int databaseSizeBeforeCreate = custCommunicationRefRepository.findAll().size();
        // Create the CustCommunicationRef
        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isCreated());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeCreate + 1);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(DEFAULT_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustCommunicationRefWithExistingId() throws Exception {
        // Create the CustCommunicationRef with an existing ID
        custCommunicationRef.setId(1L);

        int databaseSizeBeforeCreate = custCommunicationRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustomerNotificationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCommunicationRefRepository.findAll().size();
        // set the field null
        custCommunicationRef.setCustomerNotificationId(null);

        // Create the CustCommunicationRef, which fails.

        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCommunicationRefRepository.findAll().size();
        // set the field null
        custCommunicationRef.setRole(null);

        // Create the CustCommunicationRef, which fails.

        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCommunicationRefRepository.findAll().size();
        // set the field null
        custCommunicationRef.setCustomerId(null);

        // Create the CustCommunicationRef, which fails.

        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefs() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custCommunicationRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerNotificationId").value(hasItem(DEFAULT_CUSTOMER_NOTIFICATION_ID)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustCommunicationRef() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get the custCommunicationRef
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL_ID, custCommunicationRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custCommunicationRef.getId().intValue()))
            .andExpect(jsonPath("$.customerNotificationId").value(DEFAULT_CUSTOMER_NOTIFICATION_ID))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustCommunicationRef() throws Exception {
        // Get the custCommunicationRef
        restCustCommunicationRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustCommunicationRef() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();

        // Update the custCommunicationRef
        CustCommunicationRef updatedCustCommunicationRef = custCommunicationRefRepository.findById(custCommunicationRef.getId()).get();
        // Disconnect from session so that the updates on updatedCustCommunicationRef are not directly saved in db
        em.detach(updatedCustCommunicationRef);
        updatedCustCommunicationRef
            .customerNotificationId(UPDATED_CUSTOMER_NOTIFICATION_ID)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustCommunicationRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustCommunicationRef))
            )
            .andExpect(status().isOk());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(UPDATED_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCommunicationRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustCommunicationRefWithPatch() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();

        // Update the custCommunicationRef using partial update
        CustCommunicationRef partialUpdatedCustCommunicationRef = new CustCommunicationRef();
        partialUpdatedCustCommunicationRef.setId(custCommunicationRef.getId());

        partialUpdatedCustCommunicationRef.status(UPDATED_STATUS);

        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCommunicationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCommunicationRef))
            )
            .andExpect(status().isOk());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(DEFAULT_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustCommunicationRefWithPatch() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();

        // Update the custCommunicationRef using partial update
        CustCommunicationRef partialUpdatedCustCommunicationRef = new CustCommunicationRef();
        partialUpdatedCustCommunicationRef.setId(custCommunicationRef.getId());

        partialUpdatedCustCommunicationRef
            .customerNotificationId(UPDATED_CUSTOMER_NOTIFICATION_ID)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCommunicationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCommunicationRef))
            )
            .andExpect(status().isOk());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(UPDATED_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custCommunicationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustCommunicationRef() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeDelete = custCommunicationRefRepository.findAll().size();

        // Delete the custCommunicationRef
        restCustCommunicationRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, custCommunicationRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
