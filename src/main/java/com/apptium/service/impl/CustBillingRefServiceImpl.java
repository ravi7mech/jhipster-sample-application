package com.apptium.service.impl;

import com.apptium.domain.CustBillingRef;
import com.apptium.repository.CustBillingRefRepository;
import com.apptium.service.CustBillingRefService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustBillingRef}.
 */
@Service
@Transactional
public class CustBillingRefServiceImpl implements CustBillingRefService {

    private final Logger log = LoggerFactory.getLogger(CustBillingRefServiceImpl.class);

    private final CustBillingRefRepository custBillingRefRepository;

    public CustBillingRefServiceImpl(CustBillingRefRepository custBillingRefRepository) {
        this.custBillingRefRepository = custBillingRefRepository;
    }

    @Override
    public CustBillingRef save(CustBillingRef custBillingRef) {
        log.debug("Request to save CustBillingRef : {}", custBillingRef);
        return custBillingRefRepository.save(custBillingRef);
    }

    @Override
    public Optional<CustBillingRef> partialUpdate(CustBillingRef custBillingRef) {
        log.debug("Request to partially update CustBillingRef : {}", custBillingRef);

        return custBillingRefRepository
            .findById(custBillingRef.getId())
            .map(
                existingCustBillingRef -> {
                    if (custBillingRef.getAmountDue() != null) {
                        existingCustBillingRef.setAmountDue(custBillingRef.getAmountDue());
                    }
                    if (custBillingRef.getBillDate() != null) {
                        existingCustBillingRef.setBillDate(custBillingRef.getBillDate());
                    }
                    if (custBillingRef.getBillNo() != null) {
                        existingCustBillingRef.setBillNo(custBillingRef.getBillNo());
                    }
                    if (custBillingRef.getBillingPeriod() != null) {
                        existingCustBillingRef.setBillingPeriod(custBillingRef.getBillingPeriod());
                    }
                    if (custBillingRef.getCategory() != null) {
                        existingCustBillingRef.setCategory(custBillingRef.getCategory());
                    }
                    if (custBillingRef.getHref() != null) {
                        existingCustBillingRef.setHref(custBillingRef.getHref());
                    }
                    if (custBillingRef.getLastUpdatedDate() != null) {
                        existingCustBillingRef.setLastUpdatedDate(custBillingRef.getLastUpdatedDate());
                    }
                    if (custBillingRef.getNextUpdatedDate() != null) {
                        existingCustBillingRef.setNextUpdatedDate(custBillingRef.getNextUpdatedDate());
                    }
                    if (custBillingRef.getPaymentDueDate() != null) {
                        existingCustBillingRef.setPaymentDueDate(custBillingRef.getPaymentDueDate());
                    }
                    if (custBillingRef.getState() != null) {
                        existingCustBillingRef.setState(custBillingRef.getState());
                    }
                    if (custBillingRef.getTaxExcludedAmount() != null) {
                        existingCustBillingRef.setTaxExcludedAmount(custBillingRef.getTaxExcludedAmount());
                    }
                    if (custBillingRef.getTaxIncludedAmount() != null) {
                        existingCustBillingRef.setTaxIncludedAmount(custBillingRef.getTaxIncludedAmount());
                    }
                    if (custBillingRef.getCustomerId() != null) {
                        existingCustBillingRef.setCustomerId(custBillingRef.getCustomerId());
                    }

                    return existingCustBillingRef;
                }
            )
            .map(custBillingRefRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustBillingRef> findAll() {
        log.debug("Request to get all CustBillingRefs");
        return custBillingRefRepository.findAll();
    }

    /**
     *  Get all the custBillingRefs where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillingRef> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custBillingRefs where Customer is null");
        return StreamSupport
            .stream(custBillingRefRepository.findAll().spliterator(), false)
            .filter(custBillingRef -> custBillingRef.getCustomer() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustBillingRef> findOne(Long id) {
        log.debug("Request to get CustBillingRef : {}", id);
        return custBillingRefRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustBillingRef : {}", id);
        custBillingRefRepository.deleteById(id);
    }
}
