package com.apptium.service.impl;

import com.apptium.domain.CustISVRef;
import com.apptium.repository.CustISVRefRepository;
import com.apptium.service.CustISVRefService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustISVRef}.
 */
@Service
@Transactional
public class CustISVRefServiceImpl implements CustISVRefService {

    private final Logger log = LoggerFactory.getLogger(CustISVRefServiceImpl.class);

    private final CustISVRefRepository custISVRefRepository;

    public CustISVRefServiceImpl(CustISVRefRepository custISVRefRepository) {
        this.custISVRefRepository = custISVRefRepository;
    }

    @Override
    public CustISVRef save(CustISVRef custISVRef) {
        log.debug("Request to save CustISVRef : {}", custISVRef);
        return custISVRefRepository.save(custISVRef);
    }

    @Override
    public Optional<CustISVRef> partialUpdate(CustISVRef custISVRef) {
        log.debug("Request to partially update CustISVRef : {}", custISVRef);

        return custISVRefRepository
            .findById(custISVRef.getId())
            .map(
                existingCustISVRef -> {
                    if (custISVRef.getIsvid() != null) {
                        existingCustISVRef.setIsvid(custISVRef.getIsvid());
                    }
                    if (custISVRef.getIsvname() != null) {
                        existingCustISVRef.setIsvname(custISVRef.getIsvname());
                    }
                    if (custISVRef.getIsvcustomerId() != null) {
                        existingCustISVRef.setIsvcustomerId(custISVRef.getIsvcustomerId());
                    }
                    if (custISVRef.getCustomerId() != null) {
                        existingCustISVRef.setCustomerId(custISVRef.getCustomerId());
                    }

                    return existingCustISVRef;
                }
            )
            .map(custISVRefRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustISVRef> findAll() {
        log.debug("Request to get all CustISVRefs");
        return custISVRefRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustISVRef> findOne(Long id) {
        log.debug("Request to get CustISVRef : {}", id);
        return custISVRefRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustISVRef : {}", id);
        custISVRefRepository.deleteById(id);
    }
}
