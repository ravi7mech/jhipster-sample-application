package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.IndNewsLetterConf;
import com.apptium.repository.IndNewsLetterConfRepository;
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
 * Integration tests for the {@link IndNewsLetterConfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndNewsLetterConfResourceIT {

    private static final Integer DEFAULT_NEW_LETTER_TYPE_ID = 1;
    private static final Integer UPDATED_NEW_LETTER_TYPE_ID = 2;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_INDIVIDUAL_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_ID = 2;

    private static final String ENTITY_API_URL = "/api/ind-news-letter-confs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndNewsLetterConfRepository indNewsLetterConfRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndNewsLetterConfMockMvc;

    private IndNewsLetterConf indNewsLetterConf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndNewsLetterConf createEntity(EntityManager em) {
        IndNewsLetterConf indNewsLetterConf = new IndNewsLetterConf()
            .newLetterTypeId(DEFAULT_NEW_LETTER_TYPE_ID)
            .value(DEFAULT_VALUE)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indNewsLetterConf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndNewsLetterConf createUpdatedEntity(EntityManager em) {
        IndNewsLetterConf indNewsLetterConf = new IndNewsLetterConf()
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indNewsLetterConf;
    }

    @BeforeEach
    public void initTest() {
        indNewsLetterConf = createEntity(em);
    }

    @Test
    @Transactional
    void createIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeCreate = indNewsLetterConfRepository.findAll().size();
        // Create the IndNewsLetterConf
        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isCreated());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeCreate + 1);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(DEFAULT_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndNewsLetterConfWithExistingId() throws Exception {
        // Create the IndNewsLetterConf with an existing ID
        indNewsLetterConf.setId(1L);

        int databaseSizeBeforeCreate = indNewsLetterConfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNewLetterTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indNewsLetterConfRepository.findAll().size();
        // set the field null
        indNewsLetterConf.setNewLetterTypeId(null);

        // Create the IndNewsLetterConf, which fails.

        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = indNewsLetterConfRepository.findAll().size();
        // set the field null
        indNewsLetterConf.setValue(null);

        // Create the IndNewsLetterConf, which fails.

        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indNewsLetterConfRepository.findAll().size();
        // set the field null
        indNewsLetterConf.setIndividualId(null);

        // Create the IndNewsLetterConf, which fails.

        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfs() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indNewsLetterConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterTypeId").value(hasItem(DEFAULT_NEW_LETTER_TYPE_ID)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID)));
    }

    @Test
    @Transactional
    void getIndNewsLetterConf() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get the indNewsLetterConf
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL_ID, indNewsLetterConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indNewsLetterConf.getId().intValue()))
            .andExpect(jsonPath("$.newLetterTypeId").value(DEFAULT_NEW_LETTER_TYPE_ID))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID));
    }

    @Test
    @Transactional
    void getNonExistingIndNewsLetterConf() throws Exception {
        // Get the indNewsLetterConf
        restIndNewsLetterConfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndNewsLetterConf() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();

        // Update the indNewsLetterConf
        IndNewsLetterConf updatedIndNewsLetterConf = indNewsLetterConfRepository.findById(indNewsLetterConf.getId()).get();
        // Disconnect from session so that the updates on updatedIndNewsLetterConf are not directly saved in db
        em.detach(updatedIndNewsLetterConf);
        updatedIndNewsLetterConf.newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID).value(UPDATED_VALUE).individualId(UPDATED_INDIVIDUAL_ID);

        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndNewsLetterConf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndNewsLetterConf))
            )
            .andExpect(status().isOk());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indNewsLetterConf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndNewsLetterConfWithPatch() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();

        // Update the indNewsLetterConf using partial update
        IndNewsLetterConf partialUpdatedIndNewsLetterConf = new IndNewsLetterConf();
        partialUpdatedIndNewsLetterConf.setId(indNewsLetterConf.getId());

        partialUpdatedIndNewsLetterConf
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndNewsLetterConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndNewsLetterConf))
            )
            .andExpect(status().isOk());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndNewsLetterConfWithPatch() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();

        // Update the indNewsLetterConf using partial update
        IndNewsLetterConf partialUpdatedIndNewsLetterConf = new IndNewsLetterConf();
        partialUpdatedIndNewsLetterConf.setId(indNewsLetterConf.getId());

        partialUpdatedIndNewsLetterConf
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndNewsLetterConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndNewsLetterConf))
            )
            .andExpect(status().isOk());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indNewsLetterConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConf))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndNewsLetterConf() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeDelete = indNewsLetterConfRepository.findAll().size();

        // Delete the indNewsLetterConf
        restIndNewsLetterConfMockMvc
            .perform(delete(ENTITY_API_URL_ID, indNewsLetterConf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
