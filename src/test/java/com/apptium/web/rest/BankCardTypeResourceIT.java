package com.apptium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.IntegrationTest;
import com.apptium.domain.BankCardType;
import com.apptium.repository.BankCardTypeRepository;
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
 * Integration tests for the {@link BankCardTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankCardTypeResourceIT {

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CARD_NUMBER = 2L;

    private static final Instant DEFAULT_EXPIRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CVV = 1;
    private static final Integer UPDATED_CVV = 2;

    private static final Integer DEFAULT_LAST_FOUR_DIGITS = 1;
    private static final Integer UPDATED_LAST_FOUR_DIGITS = 2;

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-card-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankCardTypeRepository bankCardTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankCardTypeMockMvc;

    private BankCardType bankCardType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCardType createEntity(EntityManager em) {
        BankCardType bankCardType = new BankCardType()
            .brand(DEFAULT_BRAND)
            .cardType(DEFAULT_CARD_TYPE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .cvv(DEFAULT_CVV)
            .lastFourDigits(DEFAULT_LAST_FOUR_DIGITS)
            .bank(DEFAULT_BANK);
        return bankCardType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCardType createUpdatedEntity(EntityManager em) {
        BankCardType bankCardType = new BankCardType()
            .brand(UPDATED_BRAND)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .cvv(UPDATED_CVV)
            .lastFourDigits(UPDATED_LAST_FOUR_DIGITS)
            .bank(UPDATED_BANK);
        return bankCardType;
    }

    @BeforeEach
    public void initTest() {
        bankCardType = createEntity(em);
    }

    @Test
    @Transactional
    void createBankCardType() throws Exception {
        int databaseSizeBeforeCreate = bankCardTypeRepository.findAll().size();
        // Create the BankCardType
        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isCreated());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(DEFAULT_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(DEFAULT_BANK);
    }

    @Test
    @Transactional
    void createBankCardTypeWithExistingId() throws Exception {
        // Create the BankCardType with an existing ID
        bankCardType.setId(1L);

        int databaseSizeBeforeCreate = bankCardTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setBrand(null);

        // Create the BankCardType, which fails.

        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCardTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setCardType(null);

        // Create the BankCardType, which fails.

        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setCardNumber(null);

        // Create the BankCardType, which fails.

        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpirationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setExpirationDate(null);

        // Create the BankCardType, which fails.

        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCvvIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setCvv(null);

        // Create the BankCardType, which fails.

        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastFourDigitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setLastFourDigits(null);

        // Create the BankCardType, which fails.

        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setBank(null);

        // Create the BankCardType, which fails.

        restBankCardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankCardTypes() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV)))
            .andExpect(jsonPath("$.[*].lastFourDigits").value(hasItem(DEFAULT_LAST_FOUR_DIGITS)))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)));
    }

    @Test
    @Transactional
    void getBankCardType() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get the bankCardType
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, bankCardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankCardType.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.cvv").value(DEFAULT_CVV))
            .andExpect(jsonPath("$.lastFourDigits").value(DEFAULT_LAST_FOUR_DIGITS))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK));
    }

    @Test
    @Transactional
    void getNonExistingBankCardType() throws Exception {
        // Get the bankCardType
        restBankCardTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankCardType() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();

        // Update the bankCardType
        BankCardType updatedBankCardType = bankCardTypeRepository.findById(bankCardType.getId()).get();
        // Disconnect from session so that the updates on updatedBankCardType are not directly saved in db
        em.detach(updatedBankCardType);
        updatedBankCardType
            .brand(UPDATED_BRAND)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .cvv(UPDATED_CVV)
            .lastFourDigits(UPDATED_LAST_FOUR_DIGITS)
            .bank(UPDATED_BANK);

        restBankCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankCardType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankCardType))
            )
            .andExpect(status().isOk());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(UPDATED_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void putNonExistingBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankCardType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankCardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankCardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankCardTypeWithPatch() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();

        // Update the bankCardType using partial update
        BankCardType partialUpdatedBankCardType = new BankCardType();
        partialUpdatedBankCardType.setId(bankCardType.getId());

        partialUpdatedBankCardType.brand(UPDATED_BRAND).cardNumber(UPDATED_CARD_NUMBER).bank(UPDATED_BANK);

        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankCardType))
            )
            .andExpect(status().isOk());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(DEFAULT_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void fullUpdateBankCardTypeWithPatch() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();

        // Update the bankCardType using partial update
        BankCardType partialUpdatedBankCardType = new BankCardType();
        partialUpdatedBankCardType.setId(bankCardType.getId());

        partialUpdatedBankCardType
            .brand(UPDATED_BRAND)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .cvv(UPDATED_CVV)
            .lastFourDigits(UPDATED_LAST_FOUR_DIGITS)
            .bank(UPDATED_BANK);

        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankCardType))
            )
            .andExpect(status().isOk());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(UPDATED_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void patchNonExistingBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankCardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankCardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankCardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankCardType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankCardType() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeDelete = bankCardTypeRepository.findAll().size();

        // Delete the bankCardType
        restBankCardTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankCardType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
