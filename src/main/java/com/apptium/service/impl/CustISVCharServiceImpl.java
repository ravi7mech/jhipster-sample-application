package com.apptium.service.impl;

import com.apptium.domain.CustISVChar;
import com.apptium.repository.CustISVCharRepository;
import com.apptium.service.CustISVCharService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustISVChar}.
 */
@Service
@Transactional
public class CustISVCharServiceImpl implements CustISVCharService {

    private final Logger log = LoggerFactory.getLogger(CustISVCharServiceImpl.class);

    private final CustISVCharRepository custISVCharRepository;

    public CustISVCharServiceImpl(CustISVCharRepository custISVCharRepository) {
        this.custISVCharRepository = custISVCharRepository;
    }

    @Override
    public CustISVChar save(CustISVChar custISVChar) {
        log.debug("Request to save CustISVChar : {}", custISVChar);
        return custISVCharRepository.save(custISVChar);
    }

    @Override
    public Optional<CustISVChar> partialUpdate(CustISVChar custISVChar) {
        log.debug("Request to partially update CustISVChar : {}", custISVChar);

        return custISVCharRepository
            .findById(custISVChar.getId())
            .map(
                existingCustISVChar -> {
                    if (custISVChar.getName() != null) {
                        existingCustISVChar.setName(custISVChar.getName());
                    }
                    if (custISVChar.getValue() != null) {
                        existingCustISVChar.setValue(custISVChar.getValue());
                    }
                    if (custISVChar.getValuetype() != null) {
                        existingCustISVChar.setValuetype(custISVChar.getValuetype());
                    }
                    if (custISVChar.getCustomerISVId() != null) {
                        existingCustISVChar.setCustomerISVId(custISVChar.getCustomerISVId());
                    }

                    return existingCustISVChar;
                }
            )
            .map(custISVCharRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustISVChar> findAll() {
        log.debug("Request to get all CustISVChars");
        return custISVCharRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustISVChar> findOne(Long id) {
        log.debug("Request to get CustISVChar : {}", id);
        return custISVCharRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustISVChar : {}", id);
        custISVCharRepository.deleteById(id);
    }
}
