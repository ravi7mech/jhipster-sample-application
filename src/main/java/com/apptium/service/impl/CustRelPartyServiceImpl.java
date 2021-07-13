package com.apptium.service.impl;

import com.apptium.domain.CustRelParty;
import com.apptium.repository.CustRelPartyRepository;
import com.apptium.service.CustRelPartyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustRelParty}.
 */
@Service
@Transactional
public class CustRelPartyServiceImpl implements CustRelPartyService {

    private final Logger log = LoggerFactory.getLogger(CustRelPartyServiceImpl.class);

    private final CustRelPartyRepository custRelPartyRepository;

    public CustRelPartyServiceImpl(CustRelPartyRepository custRelPartyRepository) {
        this.custRelPartyRepository = custRelPartyRepository;
    }

    @Override
    public CustRelParty save(CustRelParty custRelParty) {
        log.debug("Request to save CustRelParty : {}", custRelParty);
        return custRelPartyRepository.save(custRelParty);
    }

    @Override
    public Optional<CustRelParty> partialUpdate(CustRelParty custRelParty) {
        log.debug("Request to partially update CustRelParty : {}", custRelParty);

        return custRelPartyRepository
            .findById(custRelParty.getId())
            .map(
                existingCustRelParty -> {
                    if (custRelParty.getName() != null) {
                        existingCustRelParty.setName(custRelParty.getName());
                    }
                    if (custRelParty.getRoleId() != null) {
                        existingCustRelParty.setRoleId(custRelParty.getRoleId());
                    }
                    if (custRelParty.getIndividualId() != null) {
                        existingCustRelParty.setIndividualId(custRelParty.getIndividualId());
                    }
                    if (custRelParty.getValidFrom() != null) {
                        existingCustRelParty.setValidFrom(custRelParty.getValidFrom());
                    }
                    if (custRelParty.getValidTo() != null) {
                        existingCustRelParty.setValidTo(custRelParty.getValidTo());
                    }
                    if (custRelParty.getCustomerId() != null) {
                        existingCustRelParty.setCustomerId(custRelParty.getCustomerId());
                    }

                    return existingCustRelParty;
                }
            )
            .map(custRelPartyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustRelParty> findAll() {
        log.debug("Request to get all CustRelParties");
        return custRelPartyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustRelParty> findOne(Long id) {
        log.debug("Request to get CustRelParty : {}", id);
        return custRelPartyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustRelParty : {}", id);
        custRelPartyRepository.deleteById(id);
    }
}
