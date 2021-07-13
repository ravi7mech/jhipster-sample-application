package com.apptium.web.rest;

import com.apptium.domain.RoleTypeRef;
import com.apptium.repository.RoleTypeRefRepository;
import com.apptium.service.RoleTypeRefService;
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
 * REST controller for managing {@link com.apptium.domain.RoleTypeRef}.
 */
@RestController
@RequestMapping("/api")
public class RoleTypeRefResource {

    private final Logger log = LoggerFactory.getLogger(RoleTypeRefResource.class);

    private static final String ENTITY_NAME = "orderMgmtRoleTypeRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleTypeRefService roleTypeRefService;

    private final RoleTypeRefRepository roleTypeRefRepository;

    public RoleTypeRefResource(RoleTypeRefService roleTypeRefService, RoleTypeRefRepository roleTypeRefRepository) {
        this.roleTypeRefService = roleTypeRefService;
        this.roleTypeRefRepository = roleTypeRefRepository;
    }

    /**
     * {@code POST  /role-type-refs} : Create a new roleTypeRef.
     *
     * @param roleTypeRef the roleTypeRef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleTypeRef, or with status {@code 400 (Bad Request)} if the roleTypeRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-type-refs")
    public ResponseEntity<RoleTypeRef> createRoleTypeRef(@Valid @RequestBody RoleTypeRef roleTypeRef) throws URISyntaxException {
        log.debug("REST request to save RoleTypeRef : {}", roleTypeRef);
        if (roleTypeRef.getId() != null) {
            throw new BadRequestAlertException("A new roleTypeRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleTypeRef result = roleTypeRefService.save(roleTypeRef);
        return ResponseEntity
            .created(new URI("/api/role-type-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-type-refs/:id} : Updates an existing roleTypeRef.
     *
     * @param id the id of the roleTypeRef to save.
     * @param roleTypeRef the roleTypeRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleTypeRef,
     * or with status {@code 400 (Bad Request)} if the roleTypeRef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleTypeRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-type-refs/{id}")
    public ResponseEntity<RoleTypeRef> updateRoleTypeRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoleTypeRef roleTypeRef
    ) throws URISyntaxException {
        log.debug("REST request to update RoleTypeRef : {}, {}", id, roleTypeRef);
        if (roleTypeRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleTypeRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleTypeRef result = roleTypeRefService.save(roleTypeRef);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleTypeRef.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-type-refs/:id} : Partial updates given fields of an existing roleTypeRef, field will ignore if it is null
     *
     * @param id the id of the roleTypeRef to save.
     * @param roleTypeRef the roleTypeRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleTypeRef,
     * or with status {@code 400 (Bad Request)} if the roleTypeRef is not valid,
     * or with status {@code 404 (Not Found)} if the roleTypeRef is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleTypeRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-type-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RoleTypeRef> partialUpdateRoleTypeRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoleTypeRef roleTypeRef
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleTypeRef partially : {}, {}", id, roleTypeRef);
        if (roleTypeRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleTypeRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleTypeRef> result = roleTypeRefService.partialUpdate(roleTypeRef);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleTypeRef.getId().toString())
        );
    }

    /**
     * {@code GET  /role-type-refs} : get all the roleTypeRefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleTypeRefs in body.
     */
    @GetMapping("/role-type-refs")
    public List<RoleTypeRef> getAllRoleTypeRefs() {
        log.debug("REST request to get all RoleTypeRefs");
        return roleTypeRefService.findAll();
    }

    /**
     * {@code GET  /role-type-refs/:id} : get the "id" roleTypeRef.
     *
     * @param id the id of the roleTypeRef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleTypeRef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-type-refs/{id}")
    public ResponseEntity<RoleTypeRef> getRoleTypeRef(@PathVariable Long id) {
        log.debug("REST request to get RoleTypeRef : {}", id);
        Optional<RoleTypeRef> roleTypeRef = roleTypeRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleTypeRef);
    }

    /**
     * {@code DELETE  /role-type-refs/:id} : delete the "id" roleTypeRef.
     *
     * @param id the id of the roleTypeRef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-type-refs/{id}")
    public ResponseEntity<Void> deleteRoleTypeRef(@PathVariable Long id) {
        log.debug("REST request to delete RoleTypeRef : {}", id);
        roleTypeRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
