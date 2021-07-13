package com.apptium.service.impl;

import com.apptium.domain.CustPaymentMethod;
import com.apptium.repository.CustPaymentMethodRepository;
import com.apptium.service.CustPaymentMethodService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustPaymentMethod}.
 */
@Service
@Transactional
public class CustPaymentMethodServiceImpl implements CustPaymentMethodService {

    private final Logger log = LoggerFactory.getLogger(CustPaymentMethodServiceImpl.class);

    private final CustPaymentMethodRepository custPaymentMethodRepository;

    public CustPaymentMethodServiceImpl(CustPaymentMethodRepository custPaymentMethodRepository) {
        this.custPaymentMethodRepository = custPaymentMethodRepository;
    }

    @Override
    public CustPaymentMethod save(CustPaymentMethod custPaymentMethod) {
        log.debug("Request to save CustPaymentMethod : {}", custPaymentMethod);
        return custPaymentMethodRepository.save(custPaymentMethod);
    }

    @Override
    public Optional<CustPaymentMethod> partialUpdate(CustPaymentMethod custPaymentMethod) {
        log.debug("Request to partially update CustPaymentMethod : {}", custPaymentMethod);

        return custPaymentMethodRepository
            .findById(custPaymentMethod.getId())
            .map(
                existingCustPaymentMethod -> {
                    if (custPaymentMethod.getName() != null) {
                        existingCustPaymentMethod.setName(custPaymentMethod.getName());
                    }
                    if (custPaymentMethod.getDescription() != null) {
                        existingCustPaymentMethod.setDescription(custPaymentMethod.getDescription());
                    }
                    if (custPaymentMethod.getPreferred() != null) {
                        existingCustPaymentMethod.setPreferred(custPaymentMethod.getPreferred());
                    }
                    if (custPaymentMethod.getType() != null) {
                        existingCustPaymentMethod.setType(custPaymentMethod.getType());
                    }
                    if (custPaymentMethod.getAuthorizationCode() != null) {
                        existingCustPaymentMethod.setAuthorizationCode(custPaymentMethod.getAuthorizationCode());
                    }
                    if (custPaymentMethod.getStatus() != null) {
                        existingCustPaymentMethod.setStatus(custPaymentMethod.getStatus());
                    }
                    if (custPaymentMethod.getStatusDate() != null) {
                        existingCustPaymentMethod.setStatusDate(custPaymentMethod.getStatusDate());
                    }
                    if (custPaymentMethod.getDetails() != null) {
                        existingCustPaymentMethod.setDetails(custPaymentMethod.getDetails());
                    }
                    if (custPaymentMethod.getCustomerId() != null) {
                        existingCustPaymentMethod.setCustomerId(custPaymentMethod.getCustomerId());
                    }

                    return existingCustPaymentMethod;
                }
            )
            .map(custPaymentMethodRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustPaymentMethod> findAll() {
        log.debug("Request to get all CustPaymentMethods");
        return custPaymentMethodRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustPaymentMethod> findOne(Long id) {
        log.debug("Request to get CustPaymentMethod : {}", id);
        return custPaymentMethodRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustPaymentMethod : {}", id);
        custPaymentMethodRepository.deleteById(id);
    }
}
