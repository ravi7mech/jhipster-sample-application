package com.apptium.web.rest;

import static com.apptium.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.Eligibility;
import com.apptium.repository.EligibilityRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link EligibilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EligibilityResourceIT {

    private static final Integer DEFAULT_NO_OF_LINES = 1;
    private static final Integer UPDATED_NO_OF_LINES = 2;

    private static final BigDecimal DEFAULT_CREDIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ELIGIBLE_F_OR_PAY_LATER = false;
    private static final Boolean UPDATED_IS_ELIGIBLE_F_OR_PAY_LATER = true;

    private static final String ENTITY_API_URL = "/api/eligibilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEligibilityMockMvc;

    private Eligibility eligibility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibility createEntity(EntityManager em) {
        Eligibility eligibility = new Eligibility()
            .noOfLines(DEFAULT_NO_OF_LINES)
            .creditAmount(DEFAULT_CREDIT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .isEligibleFOrPayLater(DEFAULT_IS_ELIGIBLE_F_OR_PAY_LATER);
        return eligibility;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibility createUpdatedEntity(EntityManager em) {
        Eligibility eligibility = new Eligibility()
            .noOfLines(UPDATED_NO_OF_LINES)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .isEligibleFOrPayLater(UPDATED_IS_ELIGIBLE_F_OR_PAY_LATER);
        return eligibility;
    }

    @BeforeEach
    public void initTest() {
        eligibility = createEntity(em);
    }

    @Test
    @Transactional
    void createEligibility() throws Exception {
        int databaseSizeBeforeCreate = eligibilityRepository.findAll().size();
        // Create the Eligibility
        restEligibilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibility)))
            .andExpect(status().isCreated());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeCreate + 1);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(DEFAULT_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualByComparingTo(DEFAULT_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEligibility.getIsEligibleFOrPayLater()).isEqualTo(DEFAULT_IS_ELIGIBLE_F_OR_PAY_LATER);
    }

    @Test
    @Transactional
    void createEligibilityWithExistingId() throws Exception {
        // Create the Eligibility with an existing ID
        eligibility.setId(1L);

        int databaseSizeBeforeCreate = eligibilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEligibilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibility)))
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNoOfLinesIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityRepository.findAll().size();
        // set the field null
        eligibility.setNoOfLines(null);

        // Create the Eligibility, which fails.

        restEligibilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibility)))
            .andExpect(status().isBadRequest());

        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityRepository.findAll().size();
        // set the field null
        eligibility.setCreditAmount(null);

        // Create the Eligibility, which fails.

        restEligibilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibility)))
            .andExpect(status().isBadRequest());

        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsEligibleFOrPayLaterIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityRepository.findAll().size();
        // set the field null
        eligibility.setIsEligibleFOrPayLater(null);

        // Create the Eligibility, which fails.

        restEligibilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibility)))
            .andExpect(status().isBadRequest());

        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEligibilities() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfLines").value(hasItem(DEFAULT_NO_OF_LINES)))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(sameNumber(DEFAULT_CREDIT_AMOUNT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isEligibleFOrPayLater").value(hasItem(DEFAULT_IS_ELIGIBLE_F_OR_PAY_LATER.booleanValue())));
    }

    @Test
    @Transactional
    void getEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get the eligibility
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL_ID, eligibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eligibility.getId().intValue()))
            .andExpect(jsonPath("$.noOfLines").value(DEFAULT_NO_OF_LINES))
            .andExpect(jsonPath("$.creditAmount").value(sameNumber(DEFAULT_CREDIT_AMOUNT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isEligibleFOrPayLater").value(DEFAULT_IS_ELIGIBLE_F_OR_PAY_LATER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEligibility() throws Exception {
        // Get the eligibility
        restEligibilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Update the eligibility
        Eligibility updatedEligibility = eligibilityRepository.findById(eligibility.getId()).get();
        // Disconnect from session so that the updates on updatedEligibility are not directly saved in db
        em.detach(updatedEligibility);
        updatedEligibility
            .noOfLines(UPDATED_NO_OF_LINES)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .isEligibleFOrPayLater(UPDATED_IS_ELIGIBLE_F_OR_PAY_LATER);

        restEligibilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEligibility.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEligibility))
            )
            .andExpect(status().isOk());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(UPDATED_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEligibility.getIsEligibleFOrPayLater()).isEqualTo(UPDATED_IS_ELIGIBLE_F_OR_PAY_LATER);
    }

    @Test
    @Transactional
    void putNonExistingEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eligibility.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibility)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEligibilityWithPatch() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Update the eligibility using partial update
        Eligibility partialUpdatedEligibility = new Eligibility();
        partialUpdatedEligibility.setId(eligibility.getId());

        partialUpdatedEligibility.creditAmount(UPDATED_CREDIT_AMOUNT);

        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibility))
            )
            .andExpect(status().isOk());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(DEFAULT_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualByComparingTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEligibility.getIsEligibleFOrPayLater()).isEqualTo(DEFAULT_IS_ELIGIBLE_F_OR_PAY_LATER);
    }

    @Test
    @Transactional
    void fullUpdateEligibilityWithPatch() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Update the eligibility using partial update
        Eligibility partialUpdatedEligibility = new Eligibility();
        partialUpdatedEligibility.setId(eligibility.getId());

        partialUpdatedEligibility
            .noOfLines(UPDATED_NO_OF_LINES)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .isEligibleFOrPayLater(UPDATED_IS_ELIGIBLE_F_OR_PAY_LATER);

        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibility))
            )
            .andExpect(status().isOk());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(UPDATED_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualByComparingTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEligibility.getIsEligibleFOrPayLater()).isEqualTo(UPDATED_IS_ELIGIBLE_F_OR_PAY_LATER);
    }

    @Test
    @Transactional
    void patchNonExistingEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eligibility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eligibility))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeDelete = eligibilityRepository.findAll().size();

        // Delete the eligibility
        restEligibilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, eligibility.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
