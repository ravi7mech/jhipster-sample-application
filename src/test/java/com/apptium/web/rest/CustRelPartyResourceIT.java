package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustRelParty;
import com.apptium.repository.CustRelPartyRepository;
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
 * Integration tests for the {@link CustRelPartyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustRelPartyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ROLE_ID = 1;
    private static final Integer UPDATED_ROLE_ID = 2;

    private static final Integer DEFAULT_INDIVIDUAL_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_ID = 2;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-rel-parties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustRelPartyRepository custRelPartyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustRelPartyMockMvc;

    private CustRelParty custRelParty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustRelParty createEntity(EntityManager em) {
        CustRelParty custRelParty = new CustRelParty()
            .name(DEFAULT_NAME)
            .roleId(DEFAULT_ROLE_ID)
            .individualId(DEFAULT_INDIVIDUAL_ID)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custRelParty;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustRelParty createUpdatedEntity(EntityManager em) {
        CustRelParty custRelParty = new CustRelParty()
            .name(UPDATED_NAME)
            .roleId(UPDATED_ROLE_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);
        return custRelParty;
    }

    @BeforeEach
    public void initTest() {
        custRelParty = createEntity(em);
    }

    @Test
    @Transactional
    void createCustRelParty() throws Exception {
        int databaseSizeBeforeCreate = custRelPartyRepository.findAll().size();
        // Create the CustRelParty
        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isCreated());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeCreate + 1);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustRelPartyWithExistingId() throws Exception {
        // Create the CustRelParty with an existing ID
        custRelParty.setId(1L);

        int databaseSizeBeforeCreate = custRelPartyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setName(null);

        // Create the CustRelParty, which fails.

        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setRoleId(null);

        // Create the CustRelParty, which fails.

        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setIndividualId(null);

        // Create the CustRelParty, which fails.

        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setValidFrom(null);

        // Create the CustRelParty, which fails.

        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setValidTo(null);

        // Create the CustRelParty, which fails.

        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setCustomerId(null);

        // Create the CustRelParty, which fails.

        restCustRelPartyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustRelParties() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custRelParty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustRelParty() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get the custRelParty
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL_ID, custRelParty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custRelParty.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustRelParty() throws Exception {
        // Get the custRelParty
        restCustRelPartyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustRelParty() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();

        // Update the custRelParty
        CustRelParty updatedCustRelParty = custRelPartyRepository.findById(custRelParty.getId()).get();
        // Disconnect from session so that the updates on updatedCustRelParty are not directly saved in db
        em.detach(updatedCustRelParty);
        updatedCustRelParty
            .name(UPDATED_NAME)
            .roleId(UPDATED_ROLE_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustRelPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustRelParty.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustRelParty))
            )
            .andExpect(status().isOk());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custRelParty.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custRelParty))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custRelParty))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelParty)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustRelPartyWithPatch() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();

        // Update the custRelParty using partial update
        CustRelParty partialUpdatedCustRelParty = new CustRelParty();
        partialUpdatedCustRelParty.setId(custRelParty.getId());

        partialUpdatedCustRelParty.roleId(UPDATED_ROLE_ID).validFrom(UPDATED_VALID_FROM).validTo(UPDATED_VALID_TO);

        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustRelParty.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustRelParty))
            )
            .andExpect(status().isOk());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustRelPartyWithPatch() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();

        // Update the custRelParty using partial update
        CustRelParty partialUpdatedCustRelParty = new CustRelParty();
        partialUpdatedCustRelParty.setId(custRelParty.getId());

        partialUpdatedCustRelParty
            .name(UPDATED_NAME)
            .roleId(UPDATED_ROLE_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustRelParty.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustRelParty))
            )
            .andExpect(status().isOk());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custRelParty.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custRelParty))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custRelParty))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custRelParty))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustRelParty() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeDelete = custRelPartyRepository.findAll().size();

        // Delete the custRelParty
        restCustRelPartyMockMvc
            .perform(delete(ENTITY_API_URL_ID, custRelParty.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
