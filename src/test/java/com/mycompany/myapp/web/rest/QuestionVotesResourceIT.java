package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.QuestionVotes;
import com.mycompany.myapp.domain.enumeration.Vote;
import com.mycompany.myapp.repository.QuestionVotesRepository;
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
 * Integration tests for the {@link QuestionVotesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionVotesResourceIT {

    private static final Vote DEFAULT_VOTE = Vote.UPVOTE;
    private static final Vote UPDATED_VOTE = Vote.DOWNVOTE;

    private static final String ENTITY_API_URL = "/api/question-votes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionVotesRepository questionVotesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionVotesMockMvc;

    private QuestionVotes questionVotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionVotes createEntity(EntityManager em) {
        QuestionVotes questionVotes = new QuestionVotes().vote(DEFAULT_VOTE);
        return questionVotes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionVotes createUpdatedEntity(EntityManager em) {
        QuestionVotes questionVotes = new QuestionVotes().vote(UPDATED_VOTE);
        return questionVotes;
    }

    @BeforeEach
    public void initTest() {
        questionVotes = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestionVotes() throws Exception {
        int databaseSizeBeforeCreate = questionVotesRepository.findAll().size();
        // Create the QuestionVotes
        restQuestionVotesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionVotes)))
            .andExpect(status().isCreated());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionVotes testQuestionVotes = questionVotesList.get(questionVotesList.size() - 1);
        assertThat(testQuestionVotes.getVote()).isEqualTo(DEFAULT_VOTE);
    }

    @Test
    @Transactional
    void createQuestionVotesWithExistingId() throws Exception {
        // Create the QuestionVotes with an existing ID
        questionVotes.setId(1L);

        int databaseSizeBeforeCreate = questionVotesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionVotesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionVotes)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuestionVotes() throws Exception {
        // Initialize the database
        questionVotesRepository.saveAndFlush(questionVotes);

        // Get all the questionVotesList
        restQuestionVotesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionVotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE.toString())));
    }

    @Test
    @Transactional
    void getQuestionVotes() throws Exception {
        // Initialize the database
        questionVotesRepository.saveAndFlush(questionVotes);

        // Get the questionVotes
        restQuestionVotesMockMvc
            .perform(get(ENTITY_API_URL_ID, questionVotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionVotes.getId().intValue()))
            .andExpect(jsonPath("$.vote").value(DEFAULT_VOTE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingQuestionVotes() throws Exception {
        // Get the questionVotes
        restQuestionVotesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuestionVotes() throws Exception {
        // Initialize the database
        questionVotesRepository.saveAndFlush(questionVotes);

        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();

        // Update the questionVotes
        QuestionVotes updatedQuestionVotes = questionVotesRepository.findById(questionVotes.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionVotes are not directly saved in db
        em.detach(updatedQuestionVotes);
        updatedQuestionVotes.vote(UPDATED_VOTE);

        restQuestionVotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuestionVotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuestionVotes))
            )
            .andExpect(status().isOk());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
        QuestionVotes testQuestionVotes = questionVotesList.get(questionVotesList.size() - 1);
        assertThat(testQuestionVotes.getVote()).isEqualTo(UPDATED_VOTE);
    }

    @Test
    @Transactional
    void putNonExistingQuestionVotes() throws Exception {
        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();
        questionVotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionVotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionVotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestionVotes() throws Exception {
        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();
        questionVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionVotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestionVotes() throws Exception {
        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();
        questionVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionVotesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionVotes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionVotesWithPatch() throws Exception {
        // Initialize the database
        questionVotesRepository.saveAndFlush(questionVotes);

        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();

        // Update the questionVotes using partial update
        QuestionVotes partialUpdatedQuestionVotes = new QuestionVotes();
        partialUpdatedQuestionVotes.setId(questionVotes.getId());

        restQuestionVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionVotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionVotes))
            )
            .andExpect(status().isOk());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
        QuestionVotes testQuestionVotes = questionVotesList.get(questionVotesList.size() - 1);
        assertThat(testQuestionVotes.getVote()).isEqualTo(DEFAULT_VOTE);
    }

    @Test
    @Transactional
    void fullUpdateQuestionVotesWithPatch() throws Exception {
        // Initialize the database
        questionVotesRepository.saveAndFlush(questionVotes);

        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();

        // Update the questionVotes using partial update
        QuestionVotes partialUpdatedQuestionVotes = new QuestionVotes();
        partialUpdatedQuestionVotes.setId(questionVotes.getId());

        partialUpdatedQuestionVotes.vote(UPDATED_VOTE);

        restQuestionVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionVotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionVotes))
            )
            .andExpect(status().isOk());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
        QuestionVotes testQuestionVotes = questionVotesList.get(questionVotesList.size() - 1);
        assertThat(testQuestionVotes.getVote()).isEqualTo(UPDATED_VOTE);
    }

    @Test
    @Transactional
    void patchNonExistingQuestionVotes() throws Exception {
        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();
        questionVotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionVotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestionVotes() throws Exception {
        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();
        questionVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestionVotes() throws Exception {
        int databaseSizeBeforeUpdate = questionVotesRepository.findAll().size();
        questionVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionVotesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(questionVotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuestionVotes in the database
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestionVotes() throws Exception {
        // Initialize the database
        questionVotesRepository.saveAndFlush(questionVotes);

        int databaseSizeBeforeDelete = questionVotesRepository.findAll().size();

        // Delete the questionVotes
        restQuestionVotesMockMvc
            .perform(delete(ENTITY_API_URL_ID, questionVotes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionVotes> questionVotesList = questionVotesRepository.findAll();
        assertThat(questionVotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
