package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.AnswerVotes;
import com.apptium.domain.enumeration.Vote;
import com.apptium.repository.AnswerVotesRepository;
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
 * Integration tests for the {@link AnswerVotesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnswerVotesResourceIT {

    private static final Vote DEFAULT_VOTE = Vote.UPVOTE;
    private static final Vote UPDATED_VOTE = Vote.DOWNVOTE;

    private static final String ENTITY_API_URL = "/api/answer-votes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnswerVotesRepository answerVotesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerVotesMockMvc;

    private AnswerVotes answerVotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerVotes createEntity(EntityManager em) {
        AnswerVotes answerVotes = new AnswerVotes().vote(DEFAULT_VOTE);
        return answerVotes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerVotes createUpdatedEntity(EntityManager em) {
        AnswerVotes answerVotes = new AnswerVotes().vote(UPDATED_VOTE);
        return answerVotes;
    }

    @BeforeEach
    public void initTest() {
        answerVotes = createEntity(em);
    }

    @Test
    @Transactional
    void createAnswerVotes() throws Exception {
        int databaseSizeBeforeCreate = answerVotesRepository.findAll().size();
        // Create the AnswerVotes
        restAnswerVotesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerVotes)))
            .andExpect(status().isCreated());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeCreate + 1);
        AnswerVotes testAnswerVotes = answerVotesList.get(answerVotesList.size() - 1);
        assertThat(testAnswerVotes.getVote()).isEqualTo(DEFAULT_VOTE);
    }

    @Test
    @Transactional
    void createAnswerVotesWithExistingId() throws Exception {
        // Create the AnswerVotes with an existing ID
        answerVotes.setId(1L);

        int databaseSizeBeforeCreate = answerVotesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerVotesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerVotes)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnswerVotes() throws Exception {
        // Initialize the database
        answerVotesRepository.saveAndFlush(answerVotes);

        // Get all the answerVotesList
        restAnswerVotesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerVotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE.toString())));
    }

    @Test
    @Transactional
    void getAnswerVotes() throws Exception {
        // Initialize the database
        answerVotesRepository.saveAndFlush(answerVotes);

        // Get the answerVotes
        restAnswerVotesMockMvc
            .perform(get(ENTITY_API_URL_ID, answerVotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answerVotes.getId().intValue()))
            .andExpect(jsonPath("$.vote").value(DEFAULT_VOTE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnswerVotes() throws Exception {
        // Get the answerVotes
        restAnswerVotesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnswerVotes() throws Exception {
        // Initialize the database
        answerVotesRepository.saveAndFlush(answerVotes);

        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();

        // Update the answerVotes
        AnswerVotes updatedAnswerVotes = answerVotesRepository.findById(answerVotes.getId()).get();
        // Disconnect from session so that the updates on updatedAnswerVotes are not directly saved in db
        em.detach(updatedAnswerVotes);
        updatedAnswerVotes.vote(UPDATED_VOTE);

        restAnswerVotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnswerVotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnswerVotes))
            )
            .andExpect(status().isOk());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
        AnswerVotes testAnswerVotes = answerVotesList.get(answerVotesList.size() - 1);
        assertThat(testAnswerVotes.getVote()).isEqualTo(UPDATED_VOTE);
    }

    @Test
    @Transactional
    void putNonExistingAnswerVotes() throws Exception {
        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();
        answerVotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerVotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, answerVotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(answerVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnswerVotes() throws Exception {
        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();
        answerVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerVotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(answerVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnswerVotes() throws Exception {
        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();
        answerVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerVotesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerVotes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnswerVotesWithPatch() throws Exception {
        // Initialize the database
        answerVotesRepository.saveAndFlush(answerVotes);

        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();

        // Update the answerVotes using partial update
        AnswerVotes partialUpdatedAnswerVotes = new AnswerVotes();
        partialUpdatedAnswerVotes.setId(answerVotes.getId());

        restAnswerVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswerVotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnswerVotes))
            )
            .andExpect(status().isOk());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
        AnswerVotes testAnswerVotes = answerVotesList.get(answerVotesList.size() - 1);
        assertThat(testAnswerVotes.getVote()).isEqualTo(DEFAULT_VOTE);
    }

    @Test
    @Transactional
    void fullUpdateAnswerVotesWithPatch() throws Exception {
        // Initialize the database
        answerVotesRepository.saveAndFlush(answerVotes);

        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();

        // Update the answerVotes using partial update
        AnswerVotes partialUpdatedAnswerVotes = new AnswerVotes();
        partialUpdatedAnswerVotes.setId(answerVotes.getId());

        partialUpdatedAnswerVotes.vote(UPDATED_VOTE);

        restAnswerVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswerVotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnswerVotes))
            )
            .andExpect(status().isOk());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
        AnswerVotes testAnswerVotes = answerVotesList.get(answerVotesList.size() - 1);
        assertThat(testAnswerVotes.getVote()).isEqualTo(UPDATED_VOTE);
    }

    @Test
    @Transactional
    void patchNonExistingAnswerVotes() throws Exception {
        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();
        answerVotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, answerVotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(answerVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnswerVotes() throws Exception {
        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();
        answerVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerVotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(answerVotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnswerVotes() throws Exception {
        int databaseSizeBeforeUpdate = answerVotesRepository.findAll().size();
        answerVotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerVotesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(answerVotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnswerVotes in the database
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnswerVotes() throws Exception {
        // Initialize the database
        answerVotesRepository.saveAndFlush(answerVotes);

        int databaseSizeBeforeDelete = answerVotesRepository.findAll().size();

        // Delete the answerVotes
        restAnswerVotesMockMvc
            .perform(delete(ENTITY_API_URL_ID, answerVotes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnswerVotes> answerVotesList = answerVotesRepository.findAll();
        assertThat(answerVotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
