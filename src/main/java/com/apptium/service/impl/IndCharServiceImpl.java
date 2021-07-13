package com.apptium.service.impl;

import com.apptium.domain.IndChar;
import com.apptium.repository.IndCharRepository;
import com.apptium.service.IndCharService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndChar}.
 */
@Service
@Transactional
public class IndCharServiceImpl implements IndCharService {

    private final Logger log = LoggerFactory.getLogger(IndCharServiceImpl.class);

    private final IndCharRepository indCharRepository;

    public IndCharServiceImpl(IndCharRepository indCharRepository) {
        this.indCharRepository = indCharRepository;
    }

    @Override
    public IndChar save(IndChar indChar) {
        log.debug("Request to save IndChar : {}", indChar);
        return indCharRepository.save(indChar);
    }

    @Override
    public Optional<IndChar> partialUpdate(IndChar indChar) {
        log.debug("Request to partially update IndChar : {}", indChar);

        return indCharRepository
            .findById(indChar.getId())
            .map(
                existingIndChar -> {
                    if (indChar.getName() != null) {
                        existingIndChar.setName(indChar.getName());
                    }
                    if (indChar.getValue() != null) {
                        existingIndChar.setValue(indChar.getValue());
                    }
                    if (indChar.getValuetype() != null) {
                        existingIndChar.setValuetype(indChar.getValuetype());
                    }
                    if (indChar.getIndividualId() != null) {
                        existingIndChar.setIndividualId(indChar.getIndividualId());
                    }

                    return existingIndChar;
                }
            )
            .map(indCharRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndChar> findAll() {
        log.debug("Request to get all IndChars");
        return indCharRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndChar> findOne(Long id) {
        log.debug("Request to get IndChar : {}", id);
        return indCharRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndChar : {}", id);
        indCharRepository.deleteById(id);
    }
}
