package com.apptium.service.impl;

import com.apptium.domain.CustCommunicationRef;
import com.apptium.repository.CustCommunicationRefRepository;
import com.apptium.service.CustCommunicationRefService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustCommunicationRef}.
 */
@Service
@Transactional
public class CustCommunicationRefServiceImpl implements CustCommunicationRefService {

    private final Logger log = LoggerFactory.getLogger(CustCommunicationRefServiceImpl.class);

    private final CustCommunicationRefRepository custCommunicationRefRepository;

    public CustCommunicationRefServiceImpl(CustCommunicationRefRepository custCommunicationRefRepository) {
        this.custCommunicationRefRepository = custCommunicationRefRepository;
    }

    @Override
    public CustCommunicationRef save(CustCommunicationRef custCommunicationRef) {
        log.debug("Request to save CustCommunicationRef : {}", custCommunicationRef);
        return custCommunicationRefRepository.save(custCommunicationRef);
    }

    @Override
    public Optional<CustCommunicationRef> partialUpdate(CustCommunicationRef custCommunicationRef) {
        log.debug("Request to partially update CustCommunicationRef : {}", custCommunicationRef);

        return custCommunicationRefRepository
            .findById(custCommunicationRef.getId())
            .map(
                existingCustCommunicationRef -> {
                    if (custCommunicationRef.getCustomerNotificationId() != null) {
                        existingCustCommunicationRef.setCustomerNotificationId(custCommunicationRef.getCustomerNotificationId());
                    }
                    if (custCommunicationRef.getRole() != null) {
                        existingCustCommunicationRef.setRole(custCommunicationRef.getRole());
                    }
                    if (custCommunicationRef.getStatus() != null) {
                        existingCustCommunicationRef.setStatus(custCommunicationRef.getStatus());
                    }
                    if (custCommunicationRef.getCustomerId() != null) {
                        existingCustCommunicationRef.setCustomerId(custCommunicationRef.getCustomerId());
                    }

                    return existingCustCommunicationRef;
                }
            )
            .map(custCommunicationRefRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustCommunicationRef> findAll() {
        log.debug("Request to get all CustCommunicationRefs");
        return custCommunicationRefRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustCommunicationRef> findOne(Long id) {
        log.debug("Request to get CustCommunicationRef : {}", id);
        return custCommunicationRefRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustCommunicationRef : {}", id);
        custCommunicationRefRepository.deleteById(id);
    }
}
