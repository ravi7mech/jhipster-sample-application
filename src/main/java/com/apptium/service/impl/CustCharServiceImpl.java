package com.apptium.service.impl;

import com.apptium.domain.CustChar;
import com.apptium.repository.CustCharRepository;
import com.apptium.service.CustCharService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustChar}.
 */
@Service
@Transactional
public class CustCharServiceImpl implements CustCharService {

    private final Logger log = LoggerFactory.getLogger(CustCharServiceImpl.class);

    private final CustCharRepository custCharRepository;

    public CustCharServiceImpl(CustCharRepository custCharRepository) {
        this.custCharRepository = custCharRepository;
    }

    @Override
    public CustChar save(CustChar custChar) {
        log.debug("Request to save CustChar : {}", custChar);
        return custCharRepository.save(custChar);
    }

    @Override
    public Optional<CustChar> partialUpdate(CustChar custChar) {
        log.debug("Request to partially update CustChar : {}", custChar);

        return custCharRepository
            .findById(custChar.getId())
            .map(
                existingCustChar -> {
                    if (custChar.getName() != null) {
                        existingCustChar.setName(custChar.getName());
                    }
                    if (custChar.getValue() != null) {
                        existingCustChar.setValue(custChar.getValue());
                    }
                    if (custChar.getValuetype() != null) {
                        existingCustChar.setValuetype(custChar.getValuetype());
                    }
                    if (custChar.getCustomerId() != null) {
                        existingCustChar.setCustomerId(custChar.getCustomerId());
                    }

                    return existingCustChar;
                }
            )
            .map(custCharRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustChar> findAll() {
        log.debug("Request to get all CustChars");
        return custCharRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustChar> findOne(Long id) {
        log.debug("Request to get CustChar : {}", id);
        return custCharRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustChar : {}", id);
        custCharRepository.deleteById(id);
    }
}
