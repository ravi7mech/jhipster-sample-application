package com.apptium.service.impl;

import com.apptium.domain.CreditCheckVerification;
import com.apptium.repository.CreditCheckVerificationRepository;
import com.apptium.service.CreditCheckVerificationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditCheckVerification}.
 */
@Service
@Transactional
public class CreditCheckVerificationServiceImpl implements CreditCheckVerificationService {

    private final Logger log = LoggerFactory.getLogger(CreditCheckVerificationServiceImpl.class);

    private final CreditCheckVerificationRepository creditCheckVerificationRepository;

    public CreditCheckVerificationServiceImpl(CreditCheckVerificationRepository creditCheckVerificationRepository) {
        this.creditCheckVerificationRepository = creditCheckVerificationRepository;
    }

    @Override
    public CreditCheckVerification save(CreditCheckVerification creditCheckVerification) {
        log.debug("Request to save CreditCheckVerification : {}", creditCheckVerification);
        return creditCheckVerificationRepository.save(creditCheckVerification);
    }

    @Override
    public Optional<CreditCheckVerification> partialUpdate(CreditCheckVerification creditCheckVerification) {
        log.debug("Request to partially update CreditCheckVerification : {}", creditCheckVerification);

        return creditCheckVerificationRepository
            .findById(creditCheckVerification.getId())
            .map(
                existingCreditCheckVerification -> {
                    if (creditCheckVerification.getVerificationQuestion() != null) {
                        existingCreditCheckVerification.setVerificationQuestion(creditCheckVerification.getVerificationQuestion());
                    }
                    if (creditCheckVerification.getVerificationQuestionChoice() != null) {
                        existingCreditCheckVerification.setVerificationQuestionChoice(
                            creditCheckVerification.getVerificationQuestionChoice()
                        );
                    }
                    if (creditCheckVerification.getVerificationAnswer() != null) {
                        existingCreditCheckVerification.setVerificationAnswer(creditCheckVerification.getVerificationAnswer());
                    }

                    return existingCreditCheckVerification;
                }
            )
            .map(creditCheckVerificationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCheckVerification> findAll() {
        log.debug("Request to get all CreditCheckVerifications");
        return creditCheckVerificationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCheckVerification> findOne(Long id) {
        log.debug("Request to get CreditCheckVerification : {}", id);
        return creditCheckVerificationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditCheckVerification : {}", id);
        creditCheckVerificationRepository.deleteById(id);
    }
}
