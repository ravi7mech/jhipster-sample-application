package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CustPaymentMethod;
import com.apptium.repository.CustPaymentMethodRepository;
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
 * Integration tests for the {@link CustPaymentMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustPaymentMethodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PREFERRED = false;
    private static final Boolean UPDATED_PREFERRED = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTHORIZATION_CODE = 1;
    private static final Integer UPDATED_AUTHORIZATION_CODE = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String ENTITY_API_URL = "/api/cust-payment-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustPaymentMethodRepository custPaymentMethodRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustPaymentMethodMockMvc;

    private CustPaymentMethod custPaymentMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPaymentMethod createEntity(EntityManager em) {
        CustPaymentMethod custPaymentMethod = new CustPaymentMethod()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .preferred(DEFAULT_PREFERRED)
            .type(DEFAULT_TYPE)
            .authorizationCode(DEFAULT_AUTHORIZATION_CODE)
            .status(DEFAULT_STATUS)
            .statusDate(DEFAULT_STATUS_DATE)
            .details(DEFAULT_DETAILS)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custPaymentMethod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPaymentMethod createUpdatedEntity(EntityManager em) {
        CustPaymentMethod custPaymentMethod = new CustPaymentMethod()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .details(UPDATED_DETAILS)
            .customerId(UPDATED_CUSTOMER_ID);
        return custPaymentMethod;
    }

    @BeforeEach
    public void initTest() {
        custPaymentMethod = createEntity(em);
    }

    @Test
    @Transactional
    void createCustPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = custPaymentMethodRepository.findAll().size();
        // Create the CustPaymentMethod
        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isCreated());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeCreate + 1);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(DEFAULT_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustPaymentMethodWithExistingId() throws Exception {
        // Create the CustPaymentMethod with an existing ID
        custPaymentMethod.setId(1L);

        int databaseSizeBeforeCreate = custPaymentMethodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setName(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPreferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setPreferred(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setType(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthorizationCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setAuthorizationCode(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setStatus(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setStatusDate(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setDetails(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setCustomerId(null);

        // Create the CustPaymentMethod, which fails.

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethods() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custPaymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].authorizationCode").value(hasItem(DEFAULT_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    @Transactional
    void getCustPaymentMethod() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get the custPaymentMethod
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, custPaymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custPaymentMethod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.preferred").value(DEFAULT_PREFERRED.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.authorizationCode").value(DEFAULT_AUTHORIZATION_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    @Transactional
    void getNonExistingCustPaymentMethod() throws Exception {
        // Get the custPaymentMethod
        restCustPaymentMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustPaymentMethod() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();

        // Update the custPaymentMethod
        CustPaymentMethod updatedCustPaymentMethod = custPaymentMethodRepository.findById(custPaymentMethod.getId()).get();
        // Disconnect from session so that the updates on updatedCustPaymentMethod are not directly saved in db
        em.detach(updatedCustPaymentMethod);
        updatedCustPaymentMethod
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .details(UPDATED_DETAILS)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustPaymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custPaymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustPaymentMethodWithPatch() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();

        // Update the custPaymentMethod using partial update
        CustPaymentMethod partialUpdatedCustPaymentMethod = new CustPaymentMethod();
        partialUpdatedCustPaymentMethod.setId(custPaymentMethod.getId());

        partialUpdatedCustPaymentMethod
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS);

        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustPaymentMethodWithPatch() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();

        // Update the custPaymentMethod using partial update
        CustPaymentMethod partialUpdatedCustPaymentMethod = new CustPaymentMethod();
        partialUpdatedCustPaymentMethod.setId(custPaymentMethod.getId());

        partialUpdatedCustPaymentMethod
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .details(UPDATED_DETAILS)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethod))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustPaymentMethod() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeDelete = custPaymentMethodRepository.findAll().size();

        // Delete the custPaymentMethod
        restCustPaymentMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, custPaymentMethod.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
