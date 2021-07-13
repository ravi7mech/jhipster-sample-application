package com.apptium.service.impl;

import com.apptium.domain.Industry;
import com.apptium.repository.IndustryRepository;
import com.apptium.service.IndustryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Industry}.
 */
@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    private final Logger log = LoggerFactory.getLogger(IndustryServiceImpl.class);

    private final IndustryRepository industryRepository;

    public IndustryServiceImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    public Industry save(Industry industry) {
        log.debug("Request to save Industry : {}", industry);
        return industryRepository.save(industry);
    }

    @Override
    public Optional<Industry> partialUpdate(Industry industry) {
        log.debug("Request to partially update Industry : {}", industry);

        return industryRepository
            .findById(industry.getId())
            .map(
                existingIndustry -> {
                    if (industry.getName() != null) {
                        existingIndustry.setName(industry.getName());
                    }
                    if (industry.getCode() != null) {
                        existingIndustry.setCode(industry.getCode());
                    }
                    if (industry.getDescription() != null) {
                        existingIndustry.setDescription(industry.getDescription());
                    }

                    return existingIndustry;
                }
            )
            .map(industryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Industry> findAll() {
        log.debug("Request to get all Industries");
        return industryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Industry> findOne(Long id) {
        log.debug("Request to get Industry : {}", id);
        return industryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Industry : {}", id);
        industryRepository.deleteById(id);
    }
}
