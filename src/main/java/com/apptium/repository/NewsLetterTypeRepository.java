package com.apptium.repository;

import com.apptium.domain.NewsLetterType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NewsLetterType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsLetterTypeRepository extends JpaRepository<NewsLetterType, Long> {}
