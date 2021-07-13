package com.apptium.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionVotesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionVotes.class);
        QuestionVotes questionVotes1 = new QuestionVotes();
        questionVotes1.setId(1L);
        QuestionVotes questionVotes2 = new QuestionVotes();
        questionVotes2.setId(questionVotes1.getId());
        assertThat(questionVotes1).isEqualTo(questionVotes2);
        questionVotes2.setId(2L);
        assertThat(questionVotes1).isNotEqualTo(questionVotes2);
        questionVotes1.setId(null);
        assertThat(questionVotes1).isNotEqualTo(questionVotes2);
    }
}
