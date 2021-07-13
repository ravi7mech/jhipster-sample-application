package com.apptium.web.rest;

import com.apptium.domain.IndContactChar;
import com.apptium.repository.IndContactCharRepository;
import com.apptium.service.IndContactCharService;
import com.apptium.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apptium.domain.IndContactChar}.
 */
@RestController
@RequestMapping("/api")
public class IndContactCharResource {

    private final Logger log = LoggerFactory.getLogger(IndContactCharResource.class);

    private static final String ENTITY_NAME = "orderMgmtIndContactChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndContactCharService indContactCharService;

    private final IndContactCharRepository indContactCharRepository;

    public IndContactCharResource(IndContactCharService indContactCharService, IndContactCharRepository indContactCharRepository) {
        this.indContactCharService = indContactCharService;
        this.indContactCharRepository = indContactCharRepository;
    }

    /**
     * {@code POST  /ind-contact-chars} : Create a new indContactChar.
     *
     * @param indContactChar the indContactChar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indContactChar, or with status {@code 400 (Bad Request)} if the indContactChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-contact-chars")
    public ResponseEntity<IndContactChar> createIndContactChar(@Valid @RequestBody IndContactChar indContactChar)
        throws URISyntaxException {
        log.debug("REST request to save IndContactChar : {}", indContactChar);
        if (indContactChar.getId() != null) {
            throw new BadRequestAlertException("A new indContactChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndContactChar result = indContactCharService.save(indContactChar);
        return ResponseEntity
            .created(new URI("/api/ind-contact-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-contact-chars/:id} : Updates an existing indContactChar.
     *
     * @param id the id of the indContactChar to save.
     * @param indContactChar the indContactChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContactChar,
     * or with status {@code 400 (Bad Request)} if the indContactChar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indContactChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-contact-chars/{id}")
    public ResponseEntity<IndContactChar> updateIndContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndContactChar indContactChar
    ) throws URISyntaxException {
        log.debug("REST request to update IndContactChar : {}, {}", id, indContactChar);
        if (indContactChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContactChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndContactChar result = indContactCharService.save(indContactChar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indContactChar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-contact-chars/:id} : Partial updates given fields of an existing indContactChar, field will ignore if it is null
     *
     * @param id the id of the indContactChar to save.
     * @param indContactChar the indContactChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContactChar,
     * or with status {@code 400 (Bad Request)} if the indContactChar is not valid,
     * or with status {@code 404 (Not Found)} if the indContactChar is not found,
     * or with status {@code 500 (Internal Server Error)} if the indContactChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-contact-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndContactChar> partialUpdateIndContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndContactChar indContactChar
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndContactChar partially : {}, {}", id, indContactChar);
        if (indContactChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContactChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndContactChar> result = indContactCharService.partialUpdate(indContactChar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indContactChar.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-contact-chars} : get all the indContactChars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indContactChars in body.
     */
    @GetMapping("/ind-contact-chars")
    public List<IndContactChar> getAllIndContactChars() {
        log.debug("REST request to get all IndContactChars");
        return indContactCharService.findAll();
    }

    /**
     * {@code GET  /ind-contact-chars/:id} : get the "id" indContactChar.
     *
     * @param id the id of the indContactChar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indContactChar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-contact-chars/{id}")
    public ResponseEntity<IndContactChar> getIndContactChar(@PathVariable Long id) {
        log.debug("REST request to get IndContactChar : {}", id);
        Optional<IndContactChar> indContactChar = indContactCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indContactChar);
    }

    /**
     * {@code DELETE  /ind-contact-chars/:id} : delete the "id" indContactChar.
     *
     * @param id the id of the indContactChar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-contact-chars/{id}")
    public ResponseEntity<Void> deleteIndContactChar(@PathVariable Long id) {
        log.debug("REST request to delete IndContactChar : {}", id);
        indContactCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
