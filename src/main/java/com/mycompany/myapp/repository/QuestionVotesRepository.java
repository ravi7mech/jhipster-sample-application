package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.QuestionVotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QuestionVotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionVotesRepository extends JpaRepository<QuestionVotes, Long> {}
