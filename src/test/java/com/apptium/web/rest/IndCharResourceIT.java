package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.IndChar;
import com.apptium.repository.IndCharRepository;
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
 * Integration tests for the {@link IndCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALUE = false;
    private static final Boolean UPDATED_VALUE = true;

    private static final String DEFAULT_VALUETYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUETYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_INDIVIDUAL_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_ID = 2;

    private static final String ENTITY_API_URL = "/api/ind-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndCharRepository indCharRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndCharMockMvc;

    private IndChar indChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndChar createEntity(EntityManager em) {
        IndChar indChar = new IndChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valuetype(DEFAULT_VALUETYPE)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndChar createUpdatedEntity(EntityManager em) {
        IndChar indChar = new IndChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indChar;
    }

    @BeforeEach
    public void initTest() {
        indChar = createEntity(em);
    }

    @Test
    @Transactional
    void createIndChar() throws Exception {
        int databaseSizeBeforeCreate = indCharRepository.findAll().size();
        // Create the IndChar
        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isCreated());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeCreate + 1);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIndChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndCharWithExistingId() throws Exception {
        // Create the IndChar with an existing ID
        indChar.setId(1L);

        int databaseSizeBeforeCreate = indCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setName(null);

        // Create the IndChar, which fails.

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setValue(null);

        // Create the IndChar, which fails.

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValuetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setValuetype(null);

        // Create the IndChar, which fails.

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setIndividualId(null);

        // Create the IndChar, which fails.

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndChars() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.booleanValue())))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID)));
    }

    @Test
    @Transactional
    void getIndChar() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get the indChar
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL_ID, indChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.booleanValue()))
            .andExpect(jsonPath("$.valuetype").value(DEFAULT_VALUETYPE))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID));
    }

    @Test
    @Transactional
    void getNonExistingIndChar() throws Exception {
        // Get the indChar
        restIndCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndChar() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();

        // Update the indChar
        IndChar updatedIndChar = indCharRepository.findById(indChar.getId()).get();
        // Disconnect from session so that the updates on updatedIndChar are not directly saved in db
        em.detach(updatedIndChar);
        updatedIndChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).individualId(UPDATED_INDIVIDUAL_ID);

        restIndCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndChar))
            )
            .andExpect(status().isOk());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indChar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndCharWithPatch() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();

        // Update the indChar using partial update
        IndChar partialUpdatedIndChar = new IndChar();
        partialUpdatedIndChar.setId(indChar.getId());

        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndChar))
            )
            .andExpect(status().isOk());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIndChar.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndCharWithPatch() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();

        // Update the indChar using partial update
        IndChar partialUpdatedIndChar = new IndChar();
        partialUpdatedIndChar.setId(indChar.getId());

        partialUpdatedIndChar.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).individualId(UPDATED_INDIVIDUAL_ID);

        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndChar))
            )
            .andExpect(status().isOk());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndChar.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indChar))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indChar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndChar() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeDelete = indCharRepository.findAll().size();

        // Delete the indChar
        restIndCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, indChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
