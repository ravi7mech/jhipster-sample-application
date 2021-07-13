package com.apptium.web.rest;

import com.apptium.domain.IndChar;
import com.apptium.repository.IndCharRepository;
import com.apptium.service.IndCharService;
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
 * REST controller for managing {@link com.apptium.domain.IndChar}.
 */
@RestController
@RequestMapping("/api")
public class IndCharResource {

    private final Logger log = LoggerFactory.getLogger(IndCharResource.class);

    private static final String ENTITY_NAME = "orderMgmtIndChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndCharService indCharService;

    private final IndCharRepository indCharRepository;

    public IndCharResource(IndCharService indCharService, IndCharRepository indCharRepository) {
        this.indCharService = indCharService;
        this.indCharRepository = indCharRepository;
    }

    /**
     * {@code POST  /ind-chars} : Create a new indChar.
     *
     * @param indChar the indChar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indChar, or with status {@code 400 (Bad Request)} if the indChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-chars")
    public ResponseEntity<IndChar> createIndChar(@Valid @RequestBody IndChar indChar) throws URISyntaxException {
        log.debug("REST request to save IndChar : {}", indChar);
        if (indChar.getId() != null) {
            throw new BadRequestAlertException("A new indChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndChar result = indCharService.save(indChar);
        return ResponseEntity
            .created(new URI("/api/ind-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-chars/:id} : Updates an existing indChar.
     *
     * @param id the id of the indChar to save.
     * @param indChar the indChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indChar,
     * or with status {@code 400 (Bad Request)} if the indChar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-chars/{id}")
    public ResponseEntity<IndChar> updateIndChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndChar indChar
    ) throws URISyntaxException {
        log.debug("REST request to update IndChar : {}, {}", id, indChar);
        if (indChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndChar result = indCharService.save(indChar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indChar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-chars/:id} : Partial updates given fields of an existing indChar, field will ignore if it is null
     *
     * @param id the id of the indChar to save.
     * @param indChar the indChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indChar,
     * or with status {@code 400 (Bad Request)} if the indChar is not valid,
     * or with status {@code 404 (Not Found)} if the indChar is not found,
     * or with status {@code 500 (Internal Server Error)} if the indChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndChar> partialUpdateIndChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndChar indChar
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndChar partially : {}, {}", id, indChar);
        if (indChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndChar> result = indCharService.partialUpdate(indChar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indChar.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-chars} : get all the indChars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indChars in body.
     */
    @GetMapping("/ind-chars")
    public List<IndChar> getAllIndChars() {
        log.debug("REST request to get all IndChars");
        return indCharService.findAll();
    }

    /**
     * {@code GET  /ind-chars/:id} : get the "id" indChar.
     *
     * @param id the id of the indChar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indChar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-chars/{id}")
    public ResponseEntity<IndChar> getIndChar(@PathVariable Long id) {
        log.debug("REST request to get IndChar : {}", id);
        Optional<IndChar> indChar = indCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indChar);
    }

    /**
     * {@code DELETE  /ind-chars/:id} : delete the "id" indChar.
     *
     * @param id the id of the indChar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-chars/{id}")
    public ResponseEntity<Void> deleteIndChar(@PathVariable Long id) {
        log.debug("REST request to delete IndChar : {}", id);
        indCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
