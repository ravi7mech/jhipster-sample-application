package com.apptium.service.impl;

import com.apptium.domain.BankCardType;
import com.apptium.repository.BankCardTypeRepository;
import com.apptium.service.BankCardTypeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankCardType}.
 */
@Service
@Transactional
public class BankCardTypeServiceImpl implements BankCardTypeService {

    private final Logger log = LoggerFactory.getLogger(BankCardTypeServiceImpl.class);

    private final BankCardTypeRepository bankCardTypeRepository;

    public BankCardTypeServiceImpl(BankCardTypeRepository bankCardTypeRepository) {
        this.bankCardTypeRepository = bankCardTypeRepository;
    }

    @Override
    public BankCardType save(BankCardType bankCardType) {
        log.debug("Request to save BankCardType : {}", bankCardType);
        return bankCardTypeRepository.save(bankCardType);
    }

    @Override
    public Optional<BankCardType> partialUpdate(BankCardType bankCardType) {
        log.debug("Request to partially update BankCardType : {}", bankCardType);

        return bankCardTypeRepository
            .findById(bankCardType.getId())
            .map(
                existingBankCardType -> {
                    if (bankCardType.getBrand() != null) {
                        existingBankCardType.setBrand(bankCardType.getBrand());
                    }
                    if (bankCardType.getCardType() != null) {
                        existingBankCardType.setCardType(bankCardType.getCardType());
                    }
                    if (bankCardType.getCardNumber() != null) {
                        existingBankCardType.setCardNumber(bankCardType.getCardNumber());
                    }
                    if (bankCardType.getExpirationDate() != null) {
                        existingBankCardType.setExpirationDate(bankCardType.getExpirationDate());
                    }
                    if (bankCardType.getCvv() != null) {
                        existingBankCardType.setCvv(bankCardType.getCvv());
                    }
                    if (bankCardType.getLastFourDigits() != null) {
                        existingBankCardType.setLastFourDigits(bankCardType.getLastFourDigits());
                    }
                    if (bankCardType.getBank() != null) {
                        existingBankCardType.setBank(bankCardType.getBank());
                    }

                    return existingBankCardType;
                }
            )
            .map(bankCardTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankCardType> findAll() {
        log.debug("Request to get all BankCardTypes");
        return bankCardTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankCardType> findOne(Long id) {
        log.debug("Request to get BankCardType : {}", id);
        return bankCardTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankCardType : {}", id);
        bankCardTypeRepository.deleteById(id);
    }
}
