package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.Individual;
import com.apptium.repository.IndividualRepository;
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
 * Integration tests for the {@link IndividualResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndividualResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORMATTED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMATTED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/individuals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndividualMockMvc;

    private Individual individual;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individual createEntity(EntityManager em) {
        Individual individual = new Individual()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .formattedName(DEFAULT_FORMATTED_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .nationality(DEFAULT_NATIONALITY)
            .status(DEFAULT_STATUS);
        return individual;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individual createUpdatedEntity(EntityManager em) {
        Individual individual = new Individual()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);
        return individual;
    }

    @BeforeEach
    public void initTest() {
        individual = createEntity(em);
    }

    @Test
    @Transactional
    void createIndividual() throws Exception {
        int databaseSizeBeforeCreate = individualRepository.findAll().size();
        // Create the Individual
        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isCreated());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeCreate + 1);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(DEFAULT_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createIndividualWithExistingId() throws Exception {
        // Create the Individual with an existing ID
        individual.setId(1L);

        int databaseSizeBeforeCreate = individualRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setTitle(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setFirstName(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setLastName(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormattedNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setFormattedName(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setDateOfBirth(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setGender(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setMaritalStatus(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setNationality(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setStatus(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndividuals() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(individual.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].formattedName").value(hasItem(DEFAULT_FORMATTED_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get the individual
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL_ID, individual.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(individual.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.formattedName").value(DEFAULT_FORMATTED_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingIndividual() throws Exception {
        // Get the individual
        restIndividualMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual
        Individual updatedIndividual = individualRepository.findById(individual.getId()).get();
        // Disconnect from session so that the updates on updatedIndividual are not directly saved in db
        em.detach(updatedIndividual);
        updatedIndividual
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);

        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndividual.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, individual.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndividualWithPatch() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual using partial update
        Individual partialUpdatedIndividual = new Individual();
        partialUpdatedIndividual.setId(individual.getId());

        partialUpdatedIndividual
            .firstName(UPDATED_FIRST_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);

        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateIndividualWithPatch() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual using partial update
        Individual partialUpdatedIndividual = new Individual();
        partialUpdatedIndividual.setId(individual.getId());

        partialUpdatedIndividual
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);

        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, individual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeDelete = individualRepository.findAll().size();

        // Delete the individual
        restIndividualMockMvc
            .perform(delete(ENTITY_API_URL_ID, individual.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
