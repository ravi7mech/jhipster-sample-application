package com.apptium.service.impl;

import com.apptium.domain.IndNewsLetterConf;
import com.apptium.repository.IndNewsLetterConfRepository;
import com.apptium.service.IndNewsLetterConfService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndNewsLetterConf}.
 */
@Service
@Transactional
public class IndNewsLetterConfServiceImpl implements IndNewsLetterConfService {

    private final Logger log = LoggerFactory.getLogger(IndNewsLetterConfServiceImpl.class);

    private final IndNewsLetterConfRepository indNewsLetterConfRepository;

    public IndNewsLetterConfServiceImpl(IndNewsLetterConfRepository indNewsLetterConfRepository) {
        this.indNewsLetterConfRepository = indNewsLetterConfRepository;
    }

    @Override
    public IndNewsLetterConf save(IndNewsLetterConf indNewsLetterConf) {
        log.debug("Request to save IndNewsLetterConf : {}", indNewsLetterConf);
        return indNewsLetterConfRepository.save(indNewsLetterConf);
    }

    @Override
    public Optional<IndNewsLetterConf> partialUpdate(IndNewsLetterConf indNewsLetterConf) {
        log.debug("Request to partially update IndNewsLetterConf : {}", indNewsLetterConf);

        return indNewsLetterConfRepository
            .findById(indNewsLetterConf.getId())
            .map(
                existingIndNewsLetterConf -> {
                    if (indNewsLetterConf.getNewLetterTypeId() != null) {
                        existingIndNewsLetterConf.setNewLetterTypeId(indNewsLetterConf.getNewLetterTypeId());
                    }
                    if (indNewsLetterConf.getValue() != null) {
                        existingIndNewsLetterConf.setValue(indNewsLetterConf.getValue());
                    }
                    if (indNewsLetterConf.getIndividualId() != null) {
                        existingIndNewsLetterConf.setIndividualId(indNewsLetterConf.getIndividualId());
                    }

                    return existingIndNewsLetterConf;
                }
            )
            .map(indNewsLetterConfRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndNewsLetterConf> findAll() {
        log.debug("Request to get all IndNewsLetterConfs");
        return indNewsLetterConfRepository.findAll();
    }

    /**
     *  Get all the indNewsLetterConfs where Individual is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IndNewsLetterConf> findAllWhereIndividualIsNull() {
        log.debug("Request to get all indNewsLetterConfs where Individual is null");
        return StreamSupport
            .stream(indNewsLetterConfRepository.findAll().spliterator(), false)
            .filter(indNewsLetterConf -> indNewsLetterConf.getIndividual() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndNewsLetterConf> findOne(Long id) {
        log.debug("Request to get IndNewsLetterConf : {}", id);
        return indNewsLetterConfRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndNewsLetterConf : {}", id);
        indNewsLetterConfRepository.deleteById(id);
    }
}
