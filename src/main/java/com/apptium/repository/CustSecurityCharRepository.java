package com.apptium.repository;

import com.apptium.domain.CustSecurityChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustSecurityChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustSecurityCharRepository extends JpaRepository<CustSecurityChar, Long> {}
