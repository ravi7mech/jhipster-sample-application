package com.apptium.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EligibilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eligibility.class);
        Eligibility eligibility1 = new Eligibility();
        eligibility1.setId(1L);
        Eligibility eligibility2 = new Eligibility();
        eligibility2.setId(eligibility1.getId());
        assertThat(eligibility1).isEqualTo(eligibility2);
        eligibility2.setId(2L);
        assertThat(eligibility1).isNotEqualTo(eligibility2);
        eligibility1.setId(null);
        assertThat(eligibility1).isNotEqualTo(eligibility2);
    }
}
