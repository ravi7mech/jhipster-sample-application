package com.apptium.service.impl;

import com.apptium.domain.AutoPay;
import com.apptium.repository.AutoPayRepository;
import com.apptium.service.AutoPayService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AutoPay}.
 */
@Service
@Transactional
public class AutoPayServiceImpl implements AutoPayService {

    private final Logger log = LoggerFactory.getLogger(AutoPayServiceImpl.class);

    private final AutoPayRepository autoPayRepository;

    public AutoPayServiceImpl(AutoPayRepository autoPayRepository) {
        this.autoPayRepository = autoPayRepository;
    }

    @Override
    public AutoPay save(AutoPay autoPay) {
        log.debug("Request to save AutoPay : {}", autoPay);
        return autoPayRepository.save(autoPay);
    }

    @Override
    public Optional<AutoPay> partialUpdate(AutoPay autoPay) {
        log.debug("Request to partially update AutoPay : {}", autoPay);

        return autoPayRepository
            .findById(autoPay.getId())
            .map(
                existingAutoPay -> {
                    if (autoPay.getChannel() != null) {
                        existingAutoPay.setChannel(autoPay.getChannel());
                    }
                    if (autoPay.getAutoPayId() != null) {
                        existingAutoPay.setAutoPayId(autoPay.getAutoPayId());
                    }
                    if (autoPay.getDebitDate() != null) {
                        existingAutoPay.setDebitDate(autoPay.getDebitDate());
                    }
                    if (autoPay.getStatus() != null) {
                        existingAutoPay.setStatus(autoPay.getStatus());
                    }
                    if (autoPay.getCreatedDate() != null) {
                        existingAutoPay.setCreatedDate(autoPay.getCreatedDate());
                    }
                    if (autoPay.getCreatedBy() != null) {
                        existingAutoPay.setCreatedBy(autoPay.getCreatedBy());
                    }
                    if (autoPay.getUpdatedDate() != null) {
                        existingAutoPay.setUpdatedDate(autoPay.getUpdatedDate());
                    }
                    if (autoPay.getUpdatedBy() != null) {
                        existingAutoPay.setUpdatedBy(autoPay.getUpdatedBy());
                    }
                    if (autoPay.getCustomerId() != null) {
                        existingAutoPay.setCustomerId(autoPay.getCustomerId());
                    }

                    return existingAutoPay;
                }
            )
            .map(autoPayRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutoPay> findAll() {
        log.debug("Request to get all AutoPays");
        return autoPayRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AutoPay> findOne(Long id) {
        log.debug("Request to get AutoPay : {}", id);
        return autoPayRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AutoPay : {}", id);
        autoPayRepository.deleteById(id);
    }
}
