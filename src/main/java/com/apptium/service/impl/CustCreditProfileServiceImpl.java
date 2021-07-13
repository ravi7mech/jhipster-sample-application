package com.apptium.service.impl;

import com.apptium.domain.CustCreditProfile;
import com.apptium.repository.CustCreditProfileRepository;
import com.apptium.service.CustCreditProfileService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustCreditProfile}.
 */
@Service
@Transactional
public class CustCreditProfileServiceImpl implements CustCreditProfileService {

    private final Logger log = LoggerFactory.getLogger(CustCreditProfileServiceImpl.class);

    private final CustCreditProfileRepository custCreditProfileRepository;

    public CustCreditProfileServiceImpl(CustCreditProfileRepository custCreditProfileRepository) {
        this.custCreditProfileRepository = custCreditProfileRepository;
    }

    @Override
    public CustCreditProfile save(CustCreditProfile custCreditProfile) {
        log.debug("Request to save CustCreditProfile : {}", custCreditProfile);
        return custCreditProfileRepository.save(custCreditProfile);
    }

    @Override
    public Optional<CustCreditProfile> partialUpdate(CustCreditProfile custCreditProfile) {
        log.debug("Request to partially update CustCreditProfile : {}", custCreditProfile);

        return custCreditProfileRepository
            .findById(custCreditProfile.getId())
            .map(
                existingCustCreditProfile -> {
                    if (custCreditProfile.getCustomerIDType1() != null) {
                        existingCustCreditProfile.setCustomerIDType1(custCreditProfile.getCustomerIDType1());
                    }
                    if (custCreditProfile.getCustomerIDRef1() != null) {
                        existingCustCreditProfile.setCustomerIDRef1(custCreditProfile.getCustomerIDRef1());
                    }
                    if (custCreditProfile.getCustomerIDType2() != null) {
                        existingCustCreditProfile.setCustomerIDType2(custCreditProfile.getCustomerIDType2());
                    }
                    if (custCreditProfile.getCustomerIDRef2() != null) {
                        existingCustCreditProfile.setCustomerIDRef2(custCreditProfile.getCustomerIDRef2());
                    }
                    if (custCreditProfile.getCreditCardNumber() != null) {
                        existingCustCreditProfile.setCreditCardNumber(custCreditProfile.getCreditCardNumber());
                    }
                    if (custCreditProfile.getCreditProfileData() != null) {
                        existingCustCreditProfile.setCreditProfileData(custCreditProfile.getCreditProfileData());
                    }
                    if (custCreditProfile.getCreditRiskRating() != null) {
                        existingCustCreditProfile.setCreditRiskRating(custCreditProfile.getCreditRiskRating());
                    }
                    if (custCreditProfile.getCreditRiskRatingDesc() != null) {
                        existingCustCreditProfile.setCreditRiskRatingDesc(custCreditProfile.getCreditRiskRatingDesc());
                    }
                    if (custCreditProfile.getCreditScore() != null) {
                        existingCustCreditProfile.setCreditScore(custCreditProfile.getCreditScore());
                    }
                    if (custCreditProfile.getValidUntil() != null) {
                        existingCustCreditProfile.setValidUntil(custCreditProfile.getValidUntil());
                    }
                    if (custCreditProfile.getCustomerId() != null) {
                        existingCustCreditProfile.setCustomerId(custCreditProfile.getCustomerId());
                    }

                    return existingCustCreditProfile;
                }
            )
            .map(custCreditProfileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustCreditProfile> findAll() {
        log.debug("Request to get all CustCreditProfiles");
        return custCreditProfileRepository.findAll();
    }

    /**
     *  Get all the custCreditProfiles where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustCreditProfile> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custCreditProfiles where Customer is null");
        return StreamSupport
            .stream(custCreditProfileRepository.findAll().spliterator(), false)
            .filter(custCreditProfile -> custCreditProfile.getCustomer() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustCreditProfile> findOne(Long id) {
        log.debug("Request to get CustCreditProfile : {}", id);
        return custCreditProfileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustCreditProfile : {}", id);
        custCreditProfileRepository.deleteById(id);
    }
}
