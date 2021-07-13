package com.apptium.repository;

import com.apptium.domain.ShoppingSessionRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ShoppingSessionRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingSessionRefRepository extends JpaRepository<ShoppingSessionRef, Long> {}
