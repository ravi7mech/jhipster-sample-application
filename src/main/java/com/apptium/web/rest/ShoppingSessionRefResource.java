package com.apptium.web.rest;

import com.apptium.domain.ShoppingSessionRef;
import com.apptium.repository.ShoppingSessionRefRepository;
import com.apptium.service.ShoppingSessionRefService;
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
 * REST controller for managing {@link com.apptium.domain.ShoppingSessionRef}.
 */
@RestController
@RequestMapping("/api")
public class ShoppingSessionRefResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingSessionRefResource.class);

    private static final String ENTITY_NAME = "orderMgmtShoppingSessionRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoppingSessionRefService shoppingSessionRefService;

    private final ShoppingSessionRefRepository shoppingSessionRefRepository;

    public ShoppingSessionRefResource(
        ShoppingSessionRefService shoppingSessionRefService,
        ShoppingSessionRefRepository shoppingSessionRefRepository
    ) {
        this.shoppingSessionRefService = shoppingSessionRefService;
        this.shoppingSessionRefRepository = shoppingSessionRefRepository;
    }

    /**
     * {@code POST  /shopping-session-refs} : Create a new shoppingSessionRef.
     *
     * @param shoppingSessionRef the shoppingSessionRef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoppingSessionRef, or with status {@code 400 (Bad Request)} if the shoppingSessionRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shopping-session-refs")
    public ResponseEntity<ShoppingSessionRef> createShoppingSessionRef(@Valid @RequestBody ShoppingSessionRef shoppingSessionRef)
        throws URISyntaxException {
        log.debug("REST request to save ShoppingSessionRef : {}", shoppingSessionRef);
        if (shoppingSessionRef.getId() != null) {
            throw new BadRequestAlertException("A new shoppingSessionRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingSessionRef result = shoppingSessionRefService.save(shoppingSessionRef);
        return ResponseEntity
            .created(new URI("/api/shopping-session-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shopping-session-refs/:id} : Updates an existing shoppingSessionRef.
     *
     * @param id the id of the shoppingSessionRef to save.
     * @param shoppingSessionRef the shoppingSessionRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingSessionRef,
     * or with status {@code 400 (Bad Request)} if the shoppingSessionRef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoppingSessionRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shopping-session-refs/{id}")
    public ResponseEntity<ShoppingSessionRef> updateShoppingSessionRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShoppingSessionRef shoppingSessionRef
    ) throws URISyntaxException {
        log.debug("REST request to update ShoppingSessionRef : {}, {}", id, shoppingSessionRef);
        if (shoppingSessionRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingSessionRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingSessionRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoppingSessionRef result = shoppingSessionRefService.save(shoppingSessionRef);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shoppingSessionRef.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shopping-session-refs/:id} : Partial updates given fields of an existing shoppingSessionRef, field will ignore if it is null
     *
     * @param id the id of the shoppingSessionRef to save.
     * @param shoppingSessionRef the shoppingSessionRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingSessionRef,
     * or with status {@code 400 (Bad Request)} if the shoppingSessionRef is not valid,
     * or with status {@code 404 (Not Found)} if the shoppingSessionRef is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoppingSessionRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shopping-session-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ShoppingSessionRef> partialUpdateShoppingSessionRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShoppingSessionRef shoppingSessionRef
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoppingSessionRef partially : {}, {}", id, shoppingSessionRef);
        if (shoppingSessionRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingSessionRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingSessionRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoppingSessionRef> result = shoppingSessionRefService.partialUpdate(shoppingSessionRef);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shoppingSessionRef.getId().toString())
        );
    }

    /**
     * {@code GET  /shopping-session-refs} : get all the shoppingSessionRefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoppingSessionRefs in body.
     */
    @GetMapping("/shopping-session-refs")
    public List<ShoppingSessionRef> getAllShoppingSessionRefs() {
        log.debug("REST request to get all ShoppingSessionRefs");
        return shoppingSessionRefService.findAll();
    }

    /**
     * {@code GET  /shopping-session-refs/:id} : get the "id" shoppingSessionRef.
     *
     * @param id the id of the shoppingSessionRef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoppingSessionRef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shopping-session-refs/{id}")
    public ResponseEntity<ShoppingSessionRef> getShoppingSessionRef(@PathVariable Long id) {
        log.debug("REST request to get ShoppingSessionRef : {}", id);
        Optional<ShoppingSessionRef> shoppingSessionRef = shoppingSessionRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingSessionRef);
    }

    /**
     * {@code DELETE  /shopping-session-refs/:id} : delete the "id" shoppingSessionRef.
     *
     * @param id the id of the shoppingSessionRef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shopping-session-refs/{id}")
    public ResponseEntity<Void> deleteShoppingSessionRef(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingSessionRef : {}", id);
        shoppingSessionRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
