package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.NewsLetterType;
import com.apptium.repository.NewsLetterTypeRepository;
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
 * Integration tests for the {@link NewsLetterTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewsLetterTypeResourceIT {

    private static final String DEFAULT_NEW_LETTER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_LETTER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/news-letter-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NewsLetterTypeRepository newsLetterTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsLetterTypeMockMvc;

    private NewsLetterType newsLetterType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsLetterType createEntity(EntityManager em) {
        NewsLetterType newsLetterType = new NewsLetterType()
            .newLetterType(DEFAULT_NEW_LETTER_TYPE)
            .displayValue(DEFAULT_DISPLAY_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return newsLetterType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsLetterType createUpdatedEntity(EntityManager em) {
        NewsLetterType newsLetterType = new NewsLetterType()
            .newLetterType(UPDATED_NEW_LETTER_TYPE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return newsLetterType;
    }

    @BeforeEach
    public void initTest() {
        newsLetterType = createEntity(em);
    }

    @Test
    @Transactional
    void createNewsLetterType() throws Exception {
        int databaseSizeBeforeCreate = newsLetterTypeRepository.findAll().size();
        // Create the NewsLetterType
        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isCreated());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeCreate + 1);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(DEFAULT_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(DEFAULT_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createNewsLetterTypeWithExistingId() throws Exception {
        // Create the NewsLetterType with an existing ID
        newsLetterType.setId(1L);

        int databaseSizeBeforeCreate = newsLetterTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNewLetterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsLetterTypeRepository.findAll().size();
        // set the field null
        newsLetterType.setNewLetterType(null);

        // Create the NewsLetterType, which fails.

        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisplayValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsLetterTypeRepository.findAll().size();
        // set the field null
        newsLetterType.setDisplayValue(null);

        // Create the NewsLetterType, which fails.

        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsLetterTypeRepository.findAll().size();
        // set the field null
        newsLetterType.setStatus(null);

        // Create the NewsLetterType, which fails.

        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypes() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsLetterType.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterType").value(hasItem(DEFAULT_NEW_LETTER_TYPE)))
            .andExpect(jsonPath("$.[*].displayValue").value(hasItem(DEFAULT_DISPLAY_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getNewsLetterType() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get the newsLetterType
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, newsLetterType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(newsLetterType.getId().intValue()))
            .andExpect(jsonPath("$.newLetterType").value(DEFAULT_NEW_LETTER_TYPE))
            .andExpect(jsonPath("$.displayValue").value(DEFAULT_DISPLAY_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingNewsLetterType() throws Exception {
        // Get the newsLetterType
        restNewsLetterTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNewsLetterType() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();

        // Update the newsLetterType
        NewsLetterType updatedNewsLetterType = newsLetterTypeRepository.findById(newsLetterType.getId()).get();
        // Disconnect from session so that the updates on updatedNewsLetterType are not directly saved in db
        em.detach(updatedNewsLetterType);
        updatedNewsLetterType
            .newLetterType(UPDATED_NEW_LETTER_TYPE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restNewsLetterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNewsLetterType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNewsLetterType))
            )
            .andExpect(status().isOk());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(UPDATED_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(UPDATED_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsLetterType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewsLetterTypeWithPatch() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();

        // Update the newsLetterType using partial update
        NewsLetterType partialUpdatedNewsLetterType = new NewsLetterType();
        partialUpdatedNewsLetterType.setId(newsLetterType.getId());

        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsLetterType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsLetterType))
            )
            .andExpect(status().isOk());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(DEFAULT_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(DEFAULT_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateNewsLetterTypeWithPatch() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();

        // Update the newsLetterType using partial update
        NewsLetterType partialUpdatedNewsLetterType = new NewsLetterType();
        partialUpdatedNewsLetterType.setId(newsLetterType.getId());

        partialUpdatedNewsLetterType
            .newLetterType(UPDATED_NEW_LETTER_TYPE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsLetterType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsLetterType))
            )
            .andExpect(status().isOk());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(UPDATED_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(UPDATED_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, newsLetterType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(newsLetterType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNewsLetterType() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeDelete = newsLetterTypeRepository.findAll().size();

        // Delete the newsLetterType
        restNewsLetterTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, newsLetterType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
