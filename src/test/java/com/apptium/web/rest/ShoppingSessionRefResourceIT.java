package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.ShoppingSessionRef;
import com.apptium.repository.ShoppingSessionRefRepository;
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
 * Integration tests for the {@link ShoppingSessionRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShoppingSessionRefResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOPPING_SESSION_ID = 1;
    private static final Integer UPDATED_SHOPPING_SESSION_ID = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_SESSION_ABONDONED = false;
    private static final Boolean UPDATED_SESSION_ABONDONED = true;

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_INDIVIDUAL_ID = 1;
    private static final Integer UPDATED_INDIVIDUAL_ID = 2;

    private static final String ENTITY_API_URL = "/api/shopping-session-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShoppingSessionRefRepository shoppingSessionRefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShoppingSessionRefMockMvc;

    private ShoppingSessionRef shoppingSessionRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingSessionRef createEntity(EntityManager em) {
        ShoppingSessionRef shoppingSessionRef = new ShoppingSessionRef()
            .href(DEFAULT_HREF)
            .shoppingSessionId(DEFAULT_SHOPPING_SESSION_ID)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .sessionAbondoned(DEFAULT_SESSION_ABONDONED)
            .customerId(DEFAULT_CUSTOMER_ID)
            .channel(DEFAULT_CHANNEL)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return shoppingSessionRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingSessionRef createUpdatedEntity(EntityManager em) {
        ShoppingSessionRef shoppingSessionRef = new ShoppingSessionRef()
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .sessionAbondoned(UPDATED_SESSION_ABONDONED)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return shoppingSessionRef;
    }

    @BeforeEach
    public void initTest() {
        shoppingSessionRef = createEntity(em);
    }

    @Test
    @Transactional
    void createShoppingSessionRef() throws Exception {
        int databaseSizeBeforeCreate = shoppingSessionRefRepository.findAll().size();
        // Create the ShoppingSessionRef
        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isCreated());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(DEFAULT_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShoppingSessionRef.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(DEFAULT_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createShoppingSessionRefWithExistingId() throws Exception {
        // Create the ShoppingSessionRef with an existing ID
        shoppingSessionRef.setId(1L);

        int databaseSizeBeforeCreate = shoppingSessionRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHrefIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setHref(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShoppingSessionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setShoppingSessionId(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setStatus(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setCreatedDate(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionAbondonedIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setSessionAbondoned(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setCustomerId(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setChannel(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setIndividualId(null);

        // Create the ShoppingSessionRef, which fails.

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefs() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingSessionRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].shoppingSessionId").value(hasItem(DEFAULT_SHOPPING_SESSION_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sessionAbondoned").value(hasItem(DEFAULT_SESSION_ABONDONED.booleanValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID)));
    }

    @Test
    @Transactional
    void getShoppingSessionRef() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get the shoppingSessionRef
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL_ID, shoppingSessionRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingSessionRef.getId().intValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.shoppingSessionId").value(DEFAULT_SHOPPING_SESSION_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.sessionAbondoned").value(DEFAULT_SESSION_ABONDONED.booleanValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID));
    }

    @Test
    @Transactional
    void getNonExistingShoppingSessionRef() throws Exception {
        // Get the shoppingSessionRef
        restShoppingSessionRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShoppingSessionRef() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();

        // Update the shoppingSessionRef
        ShoppingSessionRef updatedShoppingSessionRef = shoppingSessionRefRepository.findById(shoppingSessionRef.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingSessionRef are not directly saved in db
        em.detach(updatedShoppingSessionRef);
        updatedShoppingSessionRef
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .sessionAbondoned(UPDATED_SESSION_ABONDONED)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedShoppingSessionRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedShoppingSessionRef))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(UPDATED_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShoppingSessionRef.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(UPDATED_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shoppingSessionRef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShoppingSessionRefWithPatch() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();

        // Update the shoppingSessionRef using partial update
        ShoppingSessionRef partialUpdatedShoppingSessionRef = new ShoppingSessionRef();
        partialUpdatedShoppingSessionRef.setId(shoppingSessionRef.getId());

        partialUpdatedShoppingSessionRef
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .channel(UPDATED_CHANNEL);

        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShoppingSessionRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShoppingSessionRef))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(UPDATED_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShoppingSessionRef.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(DEFAULT_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateShoppingSessionRefWithPatch() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();

        // Update the shoppingSessionRef using partial update
        ShoppingSessionRef partialUpdatedShoppingSessionRef = new ShoppingSessionRef();
        partialUpdatedShoppingSessionRef.setId(shoppingSessionRef.getId());

        partialUpdatedShoppingSessionRef
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .sessionAbondoned(UPDATED_SESSION_ABONDONED)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShoppingSessionRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShoppingSessionRef))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(UPDATED_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShoppingSessionRef.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(UPDATED_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shoppingSessionRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShoppingSessionRef() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeDelete = shoppingSessionRefRepository.findAll().size();

        // Delete the shoppingSessionRef
        restShoppingSessionRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, shoppingSessionRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
