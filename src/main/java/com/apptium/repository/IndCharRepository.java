package com.apptium.repository;

import com.apptium.domain.IndChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndCharRepository extends JpaRepository<IndChar, Long> {}
