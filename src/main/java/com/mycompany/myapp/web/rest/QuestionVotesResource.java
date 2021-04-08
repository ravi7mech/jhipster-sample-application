package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.QuestionVotes;
import com.mycompany.myapp.repository.QuestionVotesRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.QuestionVotes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuestionVotesResource {

    private final Logger log = LoggerFactory.getLogger(QuestionVotesResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationQuestionVotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionVotesRepository questionVotesRepository;

    public QuestionVotesResource(QuestionVotesRepository questionVotesRepository) {
        this.questionVotesRepository = questionVotesRepository;
    }

    /**
     * {@code POST  /question-votes} : Create a new questionVotes.
     *
     * @param questionVotes the questionVotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionVotes, or with status {@code 400 (Bad Request)} if the questionVotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-votes")
    public ResponseEntity<QuestionVotes> createQuestionVotes(@RequestBody QuestionVotes questionVotes) throws URISyntaxException {
        log.debug("REST request to save QuestionVotes : {}", questionVotes);
        if (questionVotes.getId() != null) {
            throw new BadRequestAlertException("A new questionVotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionVotes result = questionVotesRepository.save(questionVotes);
        return ResponseEntity
            .created(new URI("/api/question-votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-votes/:id} : Updates an existing questionVotes.
     *
     * @param id the id of the questionVotes to save.
     * @param questionVotes the questionVotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionVotes,
     * or with status {@code 400 (Bad Request)} if the questionVotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionVotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-votes/{id}")
    public ResponseEntity<QuestionVotes> updateQuestionVotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuestionVotes questionVotes
    ) throws URISyntaxException {
        log.debug("REST request to update QuestionVotes : {}, {}", id, questionVotes);
        if (questionVotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionVotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionVotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuestionVotes result = questionVotesRepository.save(questionVotes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionVotes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /question-votes/:id} : Partial updates given fields of an existing questionVotes, field will ignore if it is null
     *
     * @param id the id of the questionVotes to save.
     * @param questionVotes the questionVotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionVotes,
     * or with status {@code 400 (Bad Request)} if the questionVotes is not valid,
     * or with status {@code 404 (Not Found)} if the questionVotes is not found,
     * or with status {@code 500 (Internal Server Error)} if the questionVotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/question-votes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuestionVotes> partialUpdateQuestionVotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuestionVotes questionVotes
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuestionVotes partially : {}, {}", id, questionVotes);
        if (questionVotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionVotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionVotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuestionVotes> result = questionVotesRepository
            .findById(questionVotes.getId())
            .map(
                existingQuestionVotes -> {
                    if (questionVotes.getVote() != null) {
                        existingQuestionVotes.setVote(questionVotes.getVote());
                    }

                    return existingQuestionVotes;
                }
            )
            .map(questionVotesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionVotes.getId().toString())
        );
    }

    /**
     * {@code GET  /question-votes} : get all the questionVotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionVotes in body.
     */
    @GetMapping("/question-votes")
    public List<QuestionVotes> getAllQuestionVotes() {
        log.debug("REST request to get all QuestionVotes");
        return questionVotesRepository.findAll();
    }

    /**
     * {@code GET  /question-votes/:id} : get the "id" questionVotes.
     *
     * @param id the id of the questionVotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionVotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-votes/{id}")
    public ResponseEntity<QuestionVotes> getQuestionVotes(@PathVariable Long id) {
        log.debug("REST request to get QuestionVotes : {}", id);
        Optional<QuestionVotes> questionVotes = questionVotesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(questionVotes);
    }

    /**
     * {@code DELETE  /question-votes/:id} : delete the "id" questionVotes.
     *
     * @param id the id of the questionVotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-votes/{id}")
    public ResponseEntity<Void> deleteQuestionVotes(@PathVariable Long id) {
        log.debug("REST request to delete QuestionVotes : {}", id);
        questionVotesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
