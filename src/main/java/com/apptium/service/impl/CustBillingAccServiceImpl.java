package com.apptium.service.impl;

import com.apptium.domain.CustBillingAcc;
import com.apptium.repository.CustBillingAccRepository;
import com.apptium.service.CustBillingAccService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustBillingAcc}.
 */
@Service
@Transactional
public class CustBillingAccServiceImpl implements CustBillingAccService {

    private final Logger log = LoggerFactory.getLogger(CustBillingAccServiceImpl.class);

    private final CustBillingAccRepository custBillingAccRepository;

    public CustBillingAccServiceImpl(CustBillingAccRepository custBillingAccRepository) {
        this.custBillingAccRepository = custBillingAccRepository;
    }

    @Override
    public CustBillingAcc save(CustBillingAcc custBillingAcc) {
        log.debug("Request to save CustBillingAcc : {}", custBillingAcc);
        return custBillingAccRepository.save(custBillingAcc);
    }

    @Override
    public Optional<CustBillingAcc> partialUpdate(CustBillingAcc custBillingAcc) {
        log.debug("Request to partially update CustBillingAcc : {}", custBillingAcc);

        return custBillingAccRepository
            .findById(custBillingAcc.getId())
            .map(
                existingCustBillingAcc -> {
                    if (custBillingAcc.getName() != null) {
                        existingCustBillingAcc.setName(custBillingAcc.getName());
                    }
                    if (custBillingAcc.getHref() != null) {
                        existingCustBillingAcc.setHref(custBillingAcc.getHref());
                    }
                    if (custBillingAcc.getStatus() != null) {
                        existingCustBillingAcc.setStatus(custBillingAcc.getStatus());
                    }
                    if (custBillingAcc.getDescription() != null) {
                        existingCustBillingAcc.setDescription(custBillingAcc.getDescription());
                    }
                    if (custBillingAcc.getBillingAccountNumber() != null) {
                        existingCustBillingAcc.setBillingAccountNumber(custBillingAcc.getBillingAccountNumber());
                    }
                    if (custBillingAcc.getCustomerId() != null) {
                        existingCustBillingAcc.setCustomerId(custBillingAcc.getCustomerId());
                    }
                    if (custBillingAcc.getCurrencyCode() != null) {
                        existingCustBillingAcc.setCurrencyCode(custBillingAcc.getCurrencyCode());
                    }

                    return existingCustBillingAcc;
                }
            )
            .map(custBillingAccRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustBillingAcc> findAll() {
        log.debug("Request to get all CustBillingAccs");
        return custBillingAccRepository.findAll();
    }

    /**
     *  Get all the custBillingAccs where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillingAcc> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custBillingAccs where Customer is null");
        return StreamSupport
            .stream(custBillingAccRepository.findAll().spliterator(), false)
            .filter(custBillingAcc -> custBillingAcc.getCustomer() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustBillingAcc> findOne(Long id) {
        log.debug("Request to get CustBillingAcc : {}", id);
        return custBillingAccRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustBillingAcc : {}", id);
        custBillingAccRepository.deleteById(id);
    }
}
