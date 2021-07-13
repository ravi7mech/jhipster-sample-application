package com.apptium.repository;

import com.apptium.domain.IndNewsLetterConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndNewsLetterConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndNewsLetterConfRepository extends JpaRepository<IndNewsLetterConf, Long> {}
