package com.apptium.repository;

import com.apptium.domain.IndContactChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndContactChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndContactCharRepository extends JpaRepository<IndContactChar, Long> {}
