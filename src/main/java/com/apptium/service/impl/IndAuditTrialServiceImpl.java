package com.apptium.service.impl;

import com.apptium.domain.IndAuditTrial;
import com.apptium.repository.IndAuditTrialRepository;
import com.apptium.service.IndAuditTrialService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndAuditTrial}.
 */
@Service
@Transactional
public class IndAuditTrialServiceImpl implements IndAuditTrialService {

    private final Logger log = LoggerFactory.getLogger(IndAuditTrialServiceImpl.class);

    private final IndAuditTrialRepository indAuditTrialRepository;

    public IndAuditTrialServiceImpl(IndAuditTrialRepository indAuditTrialRepository) {
        this.indAuditTrialRepository = indAuditTrialRepository;
    }

    @Override
    public IndAuditTrial save(IndAuditTrial indAuditTrial) {
        log.debug("Request to save IndAuditTrial : {}", indAuditTrial);
        return indAuditTrialRepository.save(indAuditTrial);
    }

    @Override
    public Optional<IndAuditTrial> partialUpdate(IndAuditTrial indAuditTrial) {
        log.debug("Request to partially update IndAuditTrial : {}", indAuditTrial);

        return indAuditTrialRepository
            .findById(indAuditTrial.getId())
            .map(
                existingIndAuditTrial -> {
                    if (indAuditTrial.getName() != null) {
                        existingIndAuditTrial.setName(indAuditTrial.getName());
                    }
                    if (indAuditTrial.getActivity() != null) {
                        existingIndAuditTrial.setActivity(indAuditTrial.getActivity());
                    }
                    if (indAuditTrial.getCustomerId() != null) {
                        existingIndAuditTrial.setCustomerId(indAuditTrial.getCustomerId());
                    }
                    if (indAuditTrial.getIndividualId() != null) {
                        existingIndAuditTrial.setIndividualId(indAuditTrial.getIndividualId());
                    }
                    if (indAuditTrial.getCreatedDate() != null) {
                        existingIndAuditTrial.setCreatedDate(indAuditTrial.getCreatedDate());
                    }

                    return existingIndAuditTrial;
                }
            )
            .map(indAuditTrialRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndAuditTrial> findAll() {
        log.debug("Request to get all IndAuditTrials");
        return indAuditTrialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndAuditTrial> findOne(Long id) {
        log.debug("Request to get IndAuditTrial : {}", id);
        return indAuditTrialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndAuditTrial : {}", id);
        indAuditTrialRepository.deleteById(id);
    }
}
