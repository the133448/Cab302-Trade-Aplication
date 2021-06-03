package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetTypeQuantityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetTypeQuantity.class);
        AssetTypeQuantity assetTypeQuantity1 = new AssetTypeQuantity();
        assetTypeQuantity1.setId(1L);
        AssetTypeQuantity assetTypeQuantity2 = new AssetTypeQuantity();
        assetTypeQuantity2.setId(assetTypeQuantity1.getId());
        assertThat(assetTypeQuantity1).isEqualTo(assetTypeQuantity2);
        assetTypeQuantity2.setId(2L);
        assertThat(assetTypeQuantity1).isNotEqualTo(assetTypeQuantity2);
        assetTypeQuantity1.setId(null);
        assertThat(assetTypeQuantity1).isNotEqualTo(assetTypeQuantity2);
    }
}
