package com.apptium.service.impl;

import com.apptium.domain.CustNewsLetterConfig;
import com.apptium.repository.CustNewsLetterConfigRepository;
import com.apptium.service.CustNewsLetterConfigService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustNewsLetterConfig}.
 */
@Service
@Transactional
public class CustNewsLetterConfigServiceImpl implements CustNewsLetterConfigService {

    private final Logger log = LoggerFactory.getLogger(CustNewsLetterConfigServiceImpl.class);

    private final CustNewsLetterConfigRepository custNewsLetterConfigRepository;

    public CustNewsLetterConfigServiceImpl(CustNewsLetterConfigRepository custNewsLetterConfigRepository) {
        this.custNewsLetterConfigRepository = custNewsLetterConfigRepository;
    }

    @Override
    public CustNewsLetterConfig save(CustNewsLetterConfig custNewsLetterConfig) {
        log.debug("Request to save CustNewsLetterConfig : {}", custNewsLetterConfig);
        return custNewsLetterConfigRepository.save(custNewsLetterConfig);
    }

    @Override
    public Optional<CustNewsLetterConfig> partialUpdate(CustNewsLetterConfig custNewsLetterConfig) {
        log.debug("Request to partially update CustNewsLetterConfig : {}", custNewsLetterConfig);

        return custNewsLetterConfigRepository
            .findById(custNewsLetterConfig.getId())
            .map(
                existingCustNewsLetterConfig -> {
                    if (custNewsLetterConfig.getNewLetterTypeId() != null) {
                        existingCustNewsLetterConfig.setNewLetterTypeId(custNewsLetterConfig.getNewLetterTypeId());
                    }
                    if (custNewsLetterConfig.getValue() != null) {
                        existingCustNewsLetterConfig.setValue(custNewsLetterConfig.getValue());
                    }
                    if (custNewsLetterConfig.getCustomerId() != null) {
                        existingCustNewsLetterConfig.setCustomerId(custNewsLetterConfig.getCustomerId());
                    }

                    return existingCustNewsLetterConfig;
                }
            )
            .map(custNewsLetterConfigRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustNewsLetterConfig> findAll() {
        log.debug("Request to get all CustNewsLetterConfigs");
        return custNewsLetterConfigRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustNewsLetterConfig> findOne(Long id) {
        log.debug("Request to get CustNewsLetterConfig : {}", id);
        return custNewsLetterConfigRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustNewsLetterConfig : {}", id);
        custNewsLetterConfigRepository.deleteById(id);
    }
}
