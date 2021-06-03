package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TradeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trade.class);
        Trade trade1 = new Trade();
        trade1.setId(1L);
        Trade trade2 = new Trade();
        trade2.setId(trade1.getId());
        assertThat(trade1).isEqualTo(trade2);
        trade2.setId(2L);
        assertThat(trade1).isNotEqualTo(trade2);
        trade1.setId(null);
        assertThat(trade1).isNotEqualTo(trade2);
    }
}
