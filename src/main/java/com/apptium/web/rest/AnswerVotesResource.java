package com.apptium.web.rest;

import com.apptium.domain.AnswerVotes;
import com.apptium.repository.AnswerVotesRepository;
import com.apptium.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.apptium.domain.AnswerVotes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnswerVotesResource {

    private final Logger log = LoggerFactory.getLogger(AnswerVotesResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationAnswerVotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerVotesRepository answerVotesRepository;

    public AnswerVotesResource(AnswerVotesRepository answerVotesRepository) {
        this.answerVotesRepository = answerVotesRepository;
    }

    /**
     * {@code POST  /answer-votes} : Create a new answerVotes.
     *
     * @param answerVotes the answerVotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerVotes, or with status {@code 400 (Bad Request)} if the answerVotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-votes")
    public ResponseEntity<AnswerVotes> createAnswerVotes(@RequestBody AnswerVotes answerVotes) throws URISyntaxException {
        log.debug("REST request to save AnswerVotes : {}", answerVotes);
        if (answerVotes.getId() != null) {
            throw new BadRequestAlertException("A new answerVotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerVotes result = answerVotesRepository.save(answerVotes);
        return ResponseEntity
            .created(new URI("/api/answer-votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-votes/:id} : Updates an existing answerVotes.
     *
     * @param id the id of the answerVotes to save.
     * @param answerVotes the answerVotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerVotes,
     * or with status {@code 400 (Bad Request)} if the answerVotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerVotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-votes/{id}")
    public ResponseEntity<AnswerVotes> updateAnswerVotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnswerVotes answerVotes
    ) throws URISyntaxException {
        log.debug("REST request to update AnswerVotes : {}, {}", id, answerVotes);
        if (answerVotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, answerVotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!answerVotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnswerVotes result = answerVotesRepository.save(answerVotes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerVotes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /answer-votes/:id} : Partial updates given fields of an existing answerVotes, field will ignore if it is null
     *
     * @param id the id of the answerVotes to save.
     * @param answerVotes the answerVotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerVotes,
     * or with status {@code 400 (Bad Request)} if the answerVotes is not valid,
     * or with status {@code 404 (Not Found)} if the answerVotes is not found,
     * or with status {@code 500 (Internal Server Error)} if the answerVotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/answer-votes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnswerVotes> partialUpdateAnswerVotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnswerVotes answerVotes
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnswerVotes partially : {}, {}", id, answerVotes);
        if (answerVotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, answerVotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!answerVotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnswerVotes> result = answerVotesRepository
            .findById(answerVotes.getId())
            .map(
                existingAnswerVotes -> {
                    if (answerVotes.getVote() != null) {
                        existingAnswerVotes.setVote(answerVotes.getVote());
                    }

                    return existingAnswerVotes;
                }
            )
            .map(answerVotesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerVotes.getId().toString())
        );
    }

    /**
     * {@code GET  /answer-votes} : get all the answerVotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerVotes in body.
     */
    @GetMapping("/answer-votes")
    public List<AnswerVotes> getAllAnswerVotes() {
        log.debug("REST request to get all AnswerVotes");
        return answerVotesRepository.findAll();
    }

    /**
     * {@code GET  /answer-votes/:id} : get the "id" answerVotes.
     *
     * @param id the id of the answerVotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerVotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-votes/{id}")
    public ResponseEntity<AnswerVotes> getAnswerVotes(@PathVariable Long id) {
        log.debug("REST request to get AnswerVotes : {}", id);
        Optional<AnswerVotes> answerVotes = answerVotesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(answerVotes);
    }

    /**
     * {@code DELETE  /answer-votes/:id} : delete the "id" answerVotes.
     *
     * @param id the id of the answerVotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-votes/{id}")
    public ResponseEntity<Void> deleteAnswerVotes(@PathVariable Long id) {
        log.debug("REST request to delete AnswerVotes : {}", id);
        answerVotesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
