package com.apptium.repository;

import com.apptium.domain.CustPasswordChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustPasswordChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustPasswordCharRepository extends JpaRepository<CustPasswordChar, Long> {}
