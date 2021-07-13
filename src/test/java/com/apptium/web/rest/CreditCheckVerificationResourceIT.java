package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.CreditCheckVerification;
import com.apptium.repository.CreditCheckVerificationRepository;
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
 * Integration tests for the {@link CreditCheckVerificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreditCheckVerificationResourceIT {

    private static final String DEFAULT_VERIFICATION_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_VERIFICATION_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_VERIFICATION_QUESTION_CHOICE = "AAAAAAAAAA";
    private static final String UPDATED_VERIFICATION_QUESTION_CHOICE = "BBBBBBBBBB";

    private static final String DEFAULT_VERIFICATION_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_VERIFICATION_ANSWER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/credit-check-verifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditCheckVerificationRepository creditCheckVerificationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditCheckVerificationMockMvc;

    private CreditCheckVerification creditCheckVerification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCheckVerification createEntity(EntityManager em) {
        CreditCheckVerification creditCheckVerification = new CreditCheckVerification()
            .verificationQuestion(DEFAULT_VERIFICATION_QUESTION)
            .verificationQuestionChoice(DEFAULT_VERIFICATION_QUESTION_CHOICE)
            .verificationAnswer(DEFAULT_VERIFICATION_ANSWER);
        return creditCheckVerification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCheckVerification createUpdatedEntity(EntityManager em) {
        CreditCheckVerification creditCheckVerification = new CreditCheckVerification()
            .verificationQuestion(UPDATED_VERIFICATION_QUESTION)
            .verificationQuestionChoice(UPDATED_VERIFICATION_QUESTION_CHOICE)
            .verificationAnswer(UPDATED_VERIFICATION_ANSWER);
        return creditCheckVerification;
    }

    @BeforeEach
    public void initTest() {
        creditCheckVerification = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditCheckVerification() throws Exception {
        int databaseSizeBeforeCreate = creditCheckVerificationRepository.findAll().size();
        // Create the CreditCheckVerification
        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isCreated());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerificationQuestion()).isEqualTo(DEFAULT_VERIFICATION_QUESTION);
        assertThat(testCreditCheckVerification.getVerificationQuestionChoice()).isEqualTo(DEFAULT_VERIFICATION_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerificationAnswer()).isEqualTo(DEFAULT_VERIFICATION_ANSWER);
    }

    @Test
    @Transactional
    void createCreditCheckVerificationWithExistingId() throws Exception {
        // Create the CreditCheckVerification with an existing ID
        creditCheckVerification.setId(1L);

        int databaseSizeBeforeCreate = creditCheckVerificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVerificationQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCheckVerificationRepository.findAll().size();
        // set the field null
        creditCheckVerification.setVerificationQuestion(null);

        // Create the CreditCheckVerification, which fails.

        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerificationQuestionChoiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCheckVerificationRepository.findAll().size();
        // set the field null
        creditCheckVerification.setVerificationQuestionChoice(null);

        // Create the CreditCheckVerification, which fails.

        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerificationAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCheckVerificationRepository.findAll().size();
        // set the field null
        creditCheckVerification.setVerificationAnswer(null);

        // Create the CreditCheckVerification, which fails.

        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerifications() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCheckVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].verificationQuestion").value(hasItem(DEFAULT_VERIFICATION_QUESTION)))
            .andExpect(jsonPath("$.[*].verificationQuestionChoice").value(hasItem(DEFAULT_VERIFICATION_QUESTION_CHOICE)))
            .andExpect(jsonPath("$.[*].verificationAnswer").value(hasItem(DEFAULT_VERIFICATION_ANSWER)));
    }

    @Test
    @Transactional
    void getCreditCheckVerification() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get the creditCheckVerification
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL_ID, creditCheckVerification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditCheckVerification.getId().intValue()))
            .andExpect(jsonPath("$.verificationQuestion").value(DEFAULT_VERIFICATION_QUESTION))
            .andExpect(jsonPath("$.verificationQuestionChoice").value(DEFAULT_VERIFICATION_QUESTION_CHOICE))
            .andExpect(jsonPath("$.verificationAnswer").value(DEFAULT_VERIFICATION_ANSWER));
    }

    @Test
    @Transactional
    void getNonExistingCreditCheckVerification() throws Exception {
        // Get the creditCheckVerification
        restCreditCheckVerificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCreditCheckVerification() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();

        // Update the creditCheckVerification
        CreditCheckVerification updatedCreditCheckVerification = creditCheckVerificationRepository
            .findById(creditCheckVerification.getId())
            .get();
        // Disconnect from session so that the updates on updatedCreditCheckVerification are not directly saved in db
        em.detach(updatedCreditCheckVerification);
        updatedCreditCheckVerification
            .verificationQuestion(UPDATED_VERIFICATION_QUESTION)
            .verificationQuestionChoice(UPDATED_VERIFICATION_QUESTION_CHOICE)
            .verificationAnswer(UPDATED_VERIFICATION_ANSWER);

        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCreditCheckVerification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCreditCheckVerification))
            )
            .andExpect(status().isOk());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerificationQuestion()).isEqualTo(UPDATED_VERIFICATION_QUESTION);
        assertThat(testCreditCheckVerification.getVerificationQuestionChoice()).isEqualTo(UPDATED_VERIFICATION_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerificationAnswer()).isEqualTo(UPDATED_VERIFICATION_ANSWER);
    }

    @Test
    @Transactional
    void putNonExistingCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCheckVerification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditCheckVerificationWithPatch() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();

        // Update the creditCheckVerification using partial update
        CreditCheckVerification partialUpdatedCreditCheckVerification = new CreditCheckVerification();
        partialUpdatedCreditCheckVerification.setId(creditCheckVerification.getId());

        partialUpdatedCreditCheckVerification.verificationQuestion(UPDATED_VERIFICATION_QUESTION);

        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCheckVerification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCheckVerification))
            )
            .andExpect(status().isOk());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerificationQuestion()).isEqualTo(UPDATED_VERIFICATION_QUESTION);
        assertThat(testCreditCheckVerification.getVerificationQuestionChoice()).isEqualTo(DEFAULT_VERIFICATION_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerificationAnswer()).isEqualTo(DEFAULT_VERIFICATION_ANSWER);
    }

    @Test
    @Transactional
    void fullUpdateCreditCheckVerificationWithPatch() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();

        // Update the creditCheckVerification using partial update
        CreditCheckVerification partialUpdatedCreditCheckVerification = new CreditCheckVerification();
        partialUpdatedCreditCheckVerification.setId(creditCheckVerification.getId());

        partialUpdatedCreditCheckVerification
            .verificationQuestion(UPDATED_VERIFICATION_QUESTION)
            .verificationQuestionChoice(UPDATED_VERIFICATION_QUESTION_CHOICE)
            .verificationAnswer(UPDATED_VERIFICATION_ANSWER);

        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCheckVerification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCheckVerification))
            )
            .andExpect(status().isOk());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerificationQuestion()).isEqualTo(UPDATED_VERIFICATION_QUESTION);
        assertThat(testCreditCheckVerification.getVerificationQuestionChoice()).isEqualTo(UPDATED_VERIFICATION_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerificationAnswer()).isEqualTo(UPDATED_VERIFICATION_ANSWER);
    }

    @Test
    @Transactional
    void patchNonExistingCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditCheckVerification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditCheckVerification() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeDelete = creditCheckVerificationRepository.findAll().size();

        // Delete the creditCheckVerification
        restCreditCheckVerificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditCheckVerification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
