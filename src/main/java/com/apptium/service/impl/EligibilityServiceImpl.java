package com.apptium.service.impl;

import com.apptium.domain.Eligibility;
import com.apptium.repository.EligibilityRepository;
import com.apptium.service.EligibilityService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Eligibility}.
 */
@Service
@Transactional
public class EligibilityServiceImpl implements EligibilityService {

    private final Logger log = LoggerFactory.getLogger(EligibilityServiceImpl.class);

    private final EligibilityRepository eligibilityRepository;

    public EligibilityServiceImpl(EligibilityRepository eligibilityRepository) {
        this.eligibilityRepository = eligibilityRepository;
    }

    @Override
    public Eligibility save(Eligibility eligibility) {
        log.debug("Request to save Eligibility : {}", eligibility);
        return eligibilityRepository.save(eligibility);
    }

    @Override
    public Optional<Eligibility> partialUpdate(Eligibility eligibility) {
        log.debug("Request to partially update Eligibility : {}", eligibility);

        return eligibilityRepository
            .findById(eligibility.getId())
            .map(
                existingEligibility -> {
                    if (eligibility.getNoOfLines() != null) {
                        existingEligibility.setNoOfLines(eligibility.getNoOfLines());
                    }
                    if (eligibility.getCreditAmount() != null) {
                        existingEligibility.setCreditAmount(eligibility.getCreditAmount());
                    }
                    if (eligibility.getDescription() != null) {
                        existingEligibility.setDescription(eligibility.getDescription());
                    }
                    if (eligibility.getIsEligibleFOrPayLater() != null) {
                        existingEligibility.setIsEligibleFOrPayLater(eligibility.getIsEligibleFOrPayLater());
                    }

                    return existingEligibility;
                }
            )
            .map(eligibilityRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Eligibility> findAll() {
        log.debug("Request to get all Eligibilities");
        return eligibilityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Eligibility> findOne(Long id) {
        log.debug("Request to get Eligibility : {}", id);
        return eligibilityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eligibility : {}", id);
        eligibilityRepository.deleteById(id);
    }
}
