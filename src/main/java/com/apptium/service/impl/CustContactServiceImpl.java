package com.apptium.service.impl;

import com.apptium.domain.CustContact;
import com.apptium.repository.CustContactRepository;
import com.apptium.service.CustContactService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustContact}.
 */
@Service
@Transactional
public class CustContactServiceImpl implements CustContactService {

    private final Logger log = LoggerFactory.getLogger(CustContactServiceImpl.class);

    private final CustContactRepository custContactRepository;

    public CustContactServiceImpl(CustContactRepository custContactRepository) {
        this.custContactRepository = custContactRepository;
    }

    @Override
    public CustContact save(CustContact custContact) {
        log.debug("Request to save CustContact : {}", custContact);
        return custContactRepository.save(custContact);
    }

    @Override
    public Optional<CustContact> partialUpdate(CustContact custContact) {
        log.debug("Request to partially update CustContact : {}", custContact);

        return custContactRepository
            .findById(custContact.getId())
            .map(
                existingCustContact -> {
                    if (custContact.getName() != null) {
                        existingCustContact.setName(custContact.getName());
                    }
                    if (custContact.getDescription() != null) {
                        existingCustContact.setDescription(custContact.getDescription());
                    }
                    if (custContact.getPreferred() != null) {
                        existingCustContact.setPreferred(custContact.getPreferred());
                    }
                    if (custContact.getType() != null) {
                        existingCustContact.setType(custContact.getType());
                    }
                    if (custContact.getValidFrom() != null) {
                        existingCustContact.setValidFrom(custContact.getValidFrom());
                    }
                    if (custContact.getValidTo() != null) {
                        existingCustContact.setValidTo(custContact.getValidTo());
                    }
                    if (custContact.getCustomerId() != null) {
                        existingCustContact.setCustomerId(custContact.getCustomerId());
                    }

                    return existingCustContact;
                }
            )
            .map(custContactRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustContact> findAll() {
        log.debug("Request to get all CustContacts");
        return custContactRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustContact> findOne(Long id) {
        log.debug("Request to get CustContact : {}", id);
        return custContactRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustContact : {}", id);
        custContactRepository.deleteById(id);
    }
}
