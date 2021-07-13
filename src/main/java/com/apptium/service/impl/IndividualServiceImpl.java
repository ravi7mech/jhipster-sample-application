package com.apptium.service.impl;

import com.apptium.domain.Individual;
import com.apptium.repository.IndividualRepository;
import com.apptium.service.IndividualService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Individual}.
 */
@Service
@Transactional
public class IndividualServiceImpl implements IndividualService {

    private final Logger log = LoggerFactory.getLogger(IndividualServiceImpl.class);

    private final IndividualRepository individualRepository;

    public IndividualServiceImpl(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    @Override
    public Individual save(Individual individual) {
        log.debug("Request to save Individual : {}", individual);
        return individualRepository.save(individual);
    }

    @Override
    public Optional<Individual> partialUpdate(Individual individual) {
        log.debug("Request to partially update Individual : {}", individual);

        return individualRepository
            .findById(individual.getId())
            .map(
                existingIndividual -> {
                    if (individual.getTitle() != null) {
                        existingIndividual.setTitle(individual.getTitle());
                    }
                    if (individual.getFirstName() != null) {
                        existingIndividual.setFirstName(individual.getFirstName());
                    }
                    if (individual.getLastName() != null) {
                        existingIndividual.setLastName(individual.getLastName());
                    }
                    if (individual.getMiddleName() != null) {
                        existingIndividual.setMiddleName(individual.getMiddleName());
                    }
                    if (individual.getFormattedName() != null) {
                        existingIndividual.setFormattedName(individual.getFormattedName());
                    }
                    if (individual.getDateOfBirth() != null) {
                        existingIndividual.setDateOfBirth(individual.getDateOfBirth());
                    }
                    if (individual.getGender() != null) {
                        existingIndividual.setGender(individual.getGender());
                    }
                    if (individual.getMaritalStatus() != null) {
                        existingIndividual.setMaritalStatus(individual.getMaritalStatus());
                    }
                    if (individual.getNationality() != null) {
                        existingIndividual.setNationality(individual.getNationality());
                    }
                    if (individual.getStatus() != null) {
                        existingIndividual.setStatus(individual.getStatus());
                    }

                    return existingIndividual;
                }
            )
            .map(individualRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Individual> findAll() {
        log.debug("Request to get all Individuals");
        return individualRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Individual> findOne(Long id) {
        log.debug("Request to get Individual : {}", id);
        return individualRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Individual : {}", id);
        individualRepository.deleteById(id);
    }
}
