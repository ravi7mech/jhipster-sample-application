package com.apptium.repository;

import com.apptium.domain.RoleTypeRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoleTypeRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleTypeRefRepository extends JpaRepository<RoleTypeRef, Long> {}
