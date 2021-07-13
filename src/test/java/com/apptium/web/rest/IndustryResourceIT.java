package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.Industry;
import com.apptium.repository.IndustryRepository;
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
 * Integration tests for the {@link IndustryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndustryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/industries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndustryMockMvc;

    private Industry industry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industry createEntity(EntityManager em) {
        Industry industry = new Industry().name(DEFAULT_NAME).code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return industry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industry createUpdatedEntity(EntityManager em) {
        Industry industry = new Industry().name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return industry;
    }

    @BeforeEach
    public void initTest() {
        industry = createEntity(em);
    }

    @Test
    @Transactional
    void createIndustry() throws Exception {
        int databaseSizeBeforeCreate = industryRepository.findAll().size();
        // Create the Industry
        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isCreated());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate + 1);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createIndustryWithExistingId() throws Exception {
        // Create the Industry with an existing ID
        industry.setId(1L);

        int databaseSizeBeforeCreate = industryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryRepository.findAll().size();
        // set the field null
        industry.setName(null);

        // Create the Industry, which fails.

        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isBadRequest());

        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryRepository.findAll().size();
        // set the field null
        industry.setCode(null);

        // Create the Industry, which fails.

        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isBadRequest());

        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndustries() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get the industry
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL_ID, industry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(industry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingIndustry() throws Exception {
        // Get the industry
        restIndustryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry
        Industry updatedIndustry = industryRepository.findById(industry.getId()).get();
        // Disconnect from session so that the updates on updatedIndustry are not directly saved in db
        em.detach(updatedIndustry);
        updatedIndustry.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndustry.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, industry.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndustryWithPatch() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry using partial update
        Industry partialUpdatedIndustry = new Industry();
        partialUpdatedIndustry.setId(industry.getId());

        partialUpdatedIndustry.name(UPDATED_NAME).code(UPDATED_CODE);

        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndustry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateIndustryWithPatch() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry using partial update
        Industry partialUpdatedIndustry = new Industry();
        partialUpdatedIndustry.setId(industry.getId());

        partialUpdatedIndustry.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndustry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, industry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(industry))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeDelete = industryRepository.findAll().size();

        // Delete the industry
        restIndustryMockMvc
            .perform(delete(ENTITY_API_URL_ID, industry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
