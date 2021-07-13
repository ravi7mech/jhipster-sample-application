package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.IndContact;
import com.apptium.repository.IndContactRepository;
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
 * Integration tests for the {@link IndContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndContactResourceIT {

    private static final String DEFAULT_PREFERRED = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_INDIVIDUAL_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_ID = 2;

    private static final String ENTITY_API_URL = "/api/ind-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndContactRepository indContactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndContactMockMvc;

    private IndContact indContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContact createEntity(EntityManager em) {
        IndContact indContact = new IndContact()
            .preferred(DEFAULT_PREFERRED)
            .type(DEFAULT_TYPE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContact createUpdatedEntity(EntityManager em) {
        IndContact indContact = new IndContact()
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indContact;
    }

    @BeforeEach
    public void initTest() {
        indContact = createEntity(em);
    }

    @Test
    @Transactional
    void createIndContact() throws Exception {
        int databaseSizeBeforeCreate = indContactRepository.findAll().size();
        // Create the IndContact
        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isCreated());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeCreate + 1);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndContactWithExistingId() throws Exception {
        // Create the IndContact with an existing ID
        indContact.setId(1L);

        int databaseSizeBeforeCreate = indContactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPreferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setPreferred(null);

        // Create the IndContact, which fails.

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setType(null);

        // Create the IndContact, which fails.

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setValidFrom(null);

        // Create the IndContact, which fails.

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setValidTo(null);

        // Create the IndContact, which fails.

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setIndividualId(null);

        // Create the IndContact, which fails.

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndContacts() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID)));
    }

    @Test
    @Transactional
    void getIndContact() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get the indContact
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL_ID, indContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indContact.getId().intValue()))
            .andExpect(jsonPath("$.preferred").value(DEFAULT_PREFERRED))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID));
    }

    @Test
    @Transactional
    void getNonExistingIndContact() throws Exception {
        // Get the indContact
        restIndContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndContact() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();

        // Update the indContact
        IndContact updatedIndContact = indContactRepository.findById(indContact.getId()).get();
        // Disconnect from session so that the updates on updatedIndContact are not directly saved in db
        em.detach(updatedIndContact);
        updatedIndContact
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndContact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndContact))
            )
            .andExpect(status().isOk());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indContact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndContactWithPatch() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();

        // Update the indContact using partial update
        IndContact partialUpdatedIndContact = new IndContact();
        partialUpdatedIndContact.setId(indContact.getId());

        partialUpdatedIndContact
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContact))
            )
            .andExpect(status().isOk());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndContactWithPatch() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();

        // Update the indContact using partial update
        IndContact partialUpdatedIndContact = new IndContact();
        partialUpdatedIndContact.setId(indContact.getId());

        partialUpdatedIndContact
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContact))
            )
            .andExpect(status().isOk());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContact))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indContact))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndContact() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeDelete = indContactRepository.findAll().size();

        // Delete the indContact
        restIndContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, indContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
