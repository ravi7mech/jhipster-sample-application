package com.apptium.web.rest;

import com.apptium.domain.IndNewsLetterConf;
import com.apptium.repository.IndNewsLetterConfRepository;
import com.apptium.service.IndNewsLetterConfService;
import com.apptium.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.apptium.domain.IndNewsLetterConf}.
 */
@RestController
@RequestMapping("/api")
public class IndNewsLetterConfResource {

    private final Logger log = LoggerFactory.getLogger(IndNewsLetterConfResource.class);

    private static final String ENTITY_NAME = "orderMgmtIndNewsLetterConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndNewsLetterConfService indNewsLetterConfService;

    private final IndNewsLetterConfRepository indNewsLetterConfRepository;

    public IndNewsLetterConfResource(
        IndNewsLetterConfService indNewsLetterConfService,
        IndNewsLetterConfRepository indNewsLetterConfRepository
    ) {
        this.indNewsLetterConfService = indNewsLetterConfService;
        this.indNewsLetterConfRepository = indNewsLetterConfRepository;
    }

    /**
     * {@code POST  /ind-news-letter-confs} : Create a new indNewsLetterConf.
     *
     * @param indNewsLetterConf the indNewsLetterConf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indNewsLetterConf, or with status {@code 400 (Bad Request)} if the indNewsLetterConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-news-letter-confs")
    public ResponseEntity<IndNewsLetterConf> createIndNewsLetterConf(@Valid @RequestBody IndNewsLetterConf indNewsLetterConf)
        throws URISyntaxException {
        log.debug("REST request to save IndNewsLetterConf : {}", indNewsLetterConf);
        if (indNewsLetterConf.getId() != null) {
            throw new BadRequestAlertException("A new indNewsLetterConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndNewsLetterConf result = indNewsLetterConfService.save(indNewsLetterConf);
        return ResponseEntity
            .created(new URI("/api/ind-news-letter-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-news-letter-confs/:id} : Updates an existing indNewsLetterConf.
     *
     * @param id the id of the indNewsLetterConf to save.
     * @param indNewsLetterConf the indNewsLetterConf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indNewsLetterConf,
     * or with status {@code 400 (Bad Request)} if the indNewsLetterConf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indNewsLetterConf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-news-letter-confs/{id}")
    public ResponseEntity<IndNewsLetterConf> updateIndNewsLetterConf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndNewsLetterConf indNewsLetterConf
    ) throws URISyntaxException {
        log.debug("REST request to update IndNewsLetterConf : {}, {}", id, indNewsLetterConf);
        if (indNewsLetterConf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indNewsLetterConf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indNewsLetterConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndNewsLetterConf result = indNewsLetterConfService.save(indNewsLetterConf);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indNewsLetterConf.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-news-letter-confs/:id} : Partial updates given fields of an existing indNewsLetterConf, field will ignore if it is null
     *
     * @param id the id of the indNewsLetterConf to save.
     * @param indNewsLetterConf the indNewsLetterConf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indNewsLetterConf,
     * or with status {@code 400 (Bad Request)} if the indNewsLetterConf is not valid,
     * or with status {@code 404 (Not Found)} if the indNewsLetterConf is not found,
     * or with status {@code 500 (Internal Server Error)} if the indNewsLetterConf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-news-letter-confs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndNewsLetterConf> partialUpdateIndNewsLetterConf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndNewsLetterConf indNewsLetterConf
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndNewsLetterConf partially : {}, {}", id, indNewsLetterConf);
        if (indNewsLetterConf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indNewsLetterConf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indNewsLetterConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndNewsLetterConf> result = indNewsLetterConfService.partialUpdate(indNewsLetterConf);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indNewsLetterConf.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-news-letter-confs} : get all the indNewsLetterConfs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indNewsLetterConfs in body.
     */
    @GetMapping("/ind-news-letter-confs")
    public List<IndNewsLetterConf> getAllIndNewsLetterConfs(@RequestParam(required = false) String filter) {
        if ("individual-is-null".equals(filter)) {
            log.debug("REST request to get all IndNewsLetterConfs where individual is null");
            return indNewsLetterConfService.findAllWhereIndividualIsNull();
        }
        log.debug("REST request to get all IndNewsLetterConfs");
        return indNewsLetterConfService.findAll();
    }

    /**
     * {@code GET  /ind-news-letter-confs/:id} : get the "id" indNewsLetterConf.
     *
     * @param id the id of the indNewsLetterConf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indNewsLetterConf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-news-letter-confs/{id}")
    public ResponseEntity<IndNewsLetterConf> getIndNewsLetterConf(@PathVariable Long id) {
        log.debug("REST request to get IndNewsLetterConf : {}", id);
        Optional<IndNewsLetterConf> indNewsLetterConf = indNewsLetterConfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indNewsLetterConf);
    }

    /**
     * {@code DELETE  /ind-news-letter-confs/:id} : delete the "id" indNewsLetterConf.
     *
     * @param id the id of the indNewsLetterConf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-news-letter-confs/{id}")
    public ResponseEntity<Void> deleteIndNewsLetterConf(@PathVariable Long id) {
        log.debug("REST request to delete IndNewsLetterConf : {}", id);
        indNewsLetterConfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
