package com.apptium.service.impl;

import com.apptium.domain.CustPasswordChar;
import com.apptium.repository.CustPasswordCharRepository;
import com.apptium.service.CustPasswordCharService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustPasswordChar}.
 */
@Service
@Transactional
public class CustPasswordCharServiceImpl implements CustPasswordCharService {

    private final Logger log = LoggerFactory.getLogger(CustPasswordCharServiceImpl.class);

    private final CustPasswordCharRepository custPasswordCharRepository;

    public CustPasswordCharServiceImpl(CustPasswordCharRepository custPasswordCharRepository) {
        this.custPasswordCharRepository = custPasswordCharRepository;
    }

    @Override
    public CustPasswordChar save(CustPasswordChar custPasswordChar) {
        log.debug("Request to save CustPasswordChar : {}", custPasswordChar);
        return custPasswordCharRepository.save(custPasswordChar);
    }

    @Override
    public Optional<CustPasswordChar> partialUpdate(CustPasswordChar custPasswordChar) {
        log.debug("Request to partially update CustPasswordChar : {}", custPasswordChar);

        return custPasswordCharRepository
            .findById(custPasswordChar.getId())
            .map(
                existingCustPasswordChar -> {
                    if (custPasswordChar.getName() != null) {
                        existingCustPasswordChar.setName(custPasswordChar.getName());
                    }
                    if (custPasswordChar.getValue() != null) {
                        existingCustPasswordChar.setValue(custPasswordChar.getValue());
                    }
                    if (custPasswordChar.getValuetype() != null) {
                        existingCustPasswordChar.setValuetype(custPasswordChar.getValuetype());
                    }
                    if (custPasswordChar.getCustomerId() != null) {
                        existingCustPasswordChar.setCustomerId(custPasswordChar.getCustomerId());
                    }

                    return existingCustPasswordChar;
                }
            )
            .map(custPasswordCharRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustPasswordChar> findAll() {
        log.debug("Request to get all CustPasswordChars");
        return custPasswordCharRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustPasswordChar> findOne(Long id) {
        log.debug("Request to get CustPasswordChar : {}", id);
        return custPasswordCharRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustPasswordChar : {}", id);
        custPasswordCharRepository.deleteById(id);
    }
}
