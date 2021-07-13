package com.apptium.service.impl;

import com.apptium.domain.NewsLetterType;
import com.apptium.repository.NewsLetterTypeRepository;
import com.apptium.service.NewsLetterTypeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NewsLetterType}.
 */
@Service
@Transactional
public class NewsLetterTypeServiceImpl implements NewsLetterTypeService {

    private final Logger log = LoggerFactory.getLogger(NewsLetterTypeServiceImpl.class);

    private final NewsLetterTypeRepository newsLetterTypeRepository;

    public NewsLetterTypeServiceImpl(NewsLetterTypeRepository newsLetterTypeRepository) {
        this.newsLetterTypeRepository = newsLetterTypeRepository;
    }

    @Override
    public NewsLetterType save(NewsLetterType newsLetterType) {
        log.debug("Request to save NewsLetterType : {}", newsLetterType);
        return newsLetterTypeRepository.save(newsLetterType);
    }

    @Override
    public Optional<NewsLetterType> partialUpdate(NewsLetterType newsLetterType) {
        log.debug("Request to partially update NewsLetterType : {}", newsLetterType);

        return newsLetterTypeRepository
            .findById(newsLetterType.getId())
            .map(
                existingNewsLetterType -> {
                    if (newsLetterType.getNewLetterType() != null) {
                        existingNewsLetterType.setNewLetterType(newsLetterType.getNewLetterType());
                    }
                    if (newsLetterType.getDisplayValue() != null) {
                        existingNewsLetterType.setDisplayValue(newsLetterType.getDisplayValue());
                    }
                    if (newsLetterType.getDescription() != null) {
                        existingNewsLetterType.setDescription(newsLetterType.getDescription());
                    }
                    if (newsLetterType.getStatus() != null) {
                        existingNewsLetterType.setStatus(newsLetterType.getStatus());
                    }

                    return existingNewsLetterType;
                }
            )
            .map(newsLetterTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsLetterType> findAll() {
        log.debug("Request to get all NewsLetterTypes");
        return newsLetterTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NewsLetterType> findOne(Long id) {
        log.debug("Request to get NewsLetterType : {}", id);
        return newsLetterTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewsLetterType : {}", id);
        newsLetterTypeRepository.deleteById(id);
    }
}
