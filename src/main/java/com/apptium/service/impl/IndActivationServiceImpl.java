package com.apptium.service.impl;

import com.apptium.domain.IndActivation;
import com.apptium.repository.IndActivationRepository;
import com.apptium.service.IndActivationService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndActivation}.
 */
@Service
@Transactional
public class IndActivationServiceImpl implements IndActivationService {

    private final Logger log = LoggerFactory.getLogger(IndActivationServiceImpl.class);

    private final IndActivationRepository indActivationRepository;

    public IndActivationServiceImpl(IndActivationRepository indActivationRepository) {
        this.indActivationRepository = indActivationRepository;
    }

    @Override
    public IndActivation save(IndActivation indActivation) {
        log.debug("Request to save IndActivation : {}", indActivation);
        return indActivationRepository.save(indActivation);
    }

    @Override
    public Optional<IndActivation> partialUpdate(IndActivation indActivation) {
        log.debug("Request to partially update IndActivation : {}", indActivation);

        return indActivationRepository
            .findById(indActivation.getId())
            .map(
                existingIndActivation -> {
                    if (indActivation.getName() != null) {
                        existingIndActivation.setName(indActivation.getName());
                    }
                    if (indActivation.getActivity() != null) {
                        existingIndActivation.setActivity(indActivation.getActivity());
                    }
                    if (indActivation.getCreatedDate() != null) {
                        existingIndActivation.setCreatedDate(indActivation.getCreatedDate());
                    }
                    if (indActivation.getCustomerId() != null) {
                        existingIndActivation.setCustomerId(indActivation.getCustomerId());
                    }
                    if (indActivation.getIndividualId() != null) {
                        existingIndActivation.setIndividualId(indActivation.getIndividualId());
                    }

                    return existingIndActivation;
                }
            )
            .map(indActivationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndActivation> findAll() {
        log.debug("Request to get all IndActivations");
        return indActivationRepository.findAll();
    }

    /**
     *  Get all the indActivations where Individual is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IndActivation> findAllWhereIndividualIsNull() {
        log.debug("Request to get all indActivations where Individual is null");
        return StreamSupport
            .stream(indActivationRepository.findAll().spliterator(), false)
            .filter(indActivation -> indActivation.getIndividual() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndActivation> findOne(Long id) {
        log.debug("Request to get IndActivation : {}", id);
        return indActivationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndActivation : {}", id);
        indActivationRepository.deleteById(id);
    }
}
