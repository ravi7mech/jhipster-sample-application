package com.apptium.service.impl;

import com.apptium.domain.RoleTypeRef;
import com.apptium.repository.RoleTypeRefRepository;
import com.apptium.service.RoleTypeRefService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoleTypeRef}.
 */
@Service
@Transactional
public class RoleTypeRefServiceImpl implements RoleTypeRefService {

    private final Logger log = LoggerFactory.getLogger(RoleTypeRefServiceImpl.class);

    private final RoleTypeRefRepository roleTypeRefRepository;

    public RoleTypeRefServiceImpl(RoleTypeRefRepository roleTypeRefRepository) {
        this.roleTypeRefRepository = roleTypeRefRepository;
    }

    @Override
    public RoleTypeRef save(RoleTypeRef roleTypeRef) {
        log.debug("Request to save RoleTypeRef : {}", roleTypeRef);
        return roleTypeRefRepository.save(roleTypeRef);
    }

    @Override
    public Optional<RoleTypeRef> partialUpdate(RoleTypeRef roleTypeRef) {
        log.debug("Request to partially update RoleTypeRef : {}", roleTypeRef);

        return roleTypeRefRepository
            .findById(roleTypeRef.getId())
            .map(
                existingRoleTypeRef -> {
                    if (roleTypeRef.getRolename() != null) {
                        existingRoleTypeRef.setRolename(roleTypeRef.getRolename());
                    }
                    if (roleTypeRef.getRoletype() != null) {
                        existingRoleTypeRef.setRoletype(roleTypeRef.getRoletype());
                    }

                    return existingRoleTypeRef;
                }
            )
            .map(roleTypeRefRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleTypeRef> findAll() {
        log.debug("Request to get all RoleTypeRefs");
        return roleTypeRefRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleTypeRef> findOne(Long id) {
        log.debug("Request to get RoleTypeRef : {}", id);
        return roleTypeRefRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleTypeRef : {}", id);
        roleTypeRefRepository.deleteById(id);
    }
}
