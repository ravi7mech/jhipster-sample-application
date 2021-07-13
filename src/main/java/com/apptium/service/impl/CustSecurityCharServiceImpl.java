package com.apptium.service.impl;

import com.apptium.domain.CustSecurityChar;
import com.apptium.repository.CustSecurityCharRepository;
import com.apptium.service.CustSecurityCharService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustSecurityChar}.
 */
@Service
@Transactional
public class CustSecurityCharServiceImpl implements CustSecurityCharService {

    private final Logger log = LoggerFactory.getLogger(CustSecurityCharServiceImpl.class);

    private final CustSecurityCharRepository custSecurityCharRepository;

    public CustSecurityCharServiceImpl(CustSecurityCharRepository custSecurityCharRepository) {
        this.custSecurityCharRepository = custSecurityCharRepository;
    }

    @Override
    public CustSecurityChar save(CustSecurityChar custSecurityChar) {
        log.debug("Request to save CustSecurityChar : {}", custSecurityChar);
        return custSecurityCharRepository.save(custSecurityChar);
    }

    @Override
    public Optional<CustSecurityChar> partialUpdate(CustSecurityChar custSecurityChar) {
        log.debug("Request to partially update CustSecurityChar : {}", custSecurityChar);

        return custSecurityCharRepository
            .findById(custSecurityChar.getId())
            .map(
                existingCustSecurityChar -> {
                    if (custSecurityChar.getName() != null) {
                        existingCustSecurityChar.setName(custSecurityChar.getName());
                    }
                    if (custSecurityChar.getValue() != null) {
                        existingCustSecurityChar.setValue(custSecurityChar.getValue());
                    }
                    if (custSecurityChar.getValuetype() != null) {
                        existingCustSecurityChar.setValuetype(custSecurityChar.getValuetype());
                    }
                    if (custSecurityChar.getCustomerId() != null) {
                        existingCustSecurityChar.setCustomerId(custSecurityChar.getCustomerId());
                    }

                    return existingCustSecurityChar;
                }
            )
            .map(custSecurityCharRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustSecurityChar> findAll() {
        log.debug("Request to get all CustSecurityChars");
        return custSecurityCharRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustSecurityChar> findOne(Long id) {
        log.debug("Request to get CustSecurityChar : {}", id);
        return custSecurityCharRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustSecurityChar : {}", id);
        custSecurityCharRepository.deleteById(id);
    }
}
