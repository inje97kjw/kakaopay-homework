package com.kakaopay.support.bank.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GeoUtilTest {
    @Test
    public void limitConvert_test() {
        String article = "충남 충북 경북 경남";
        String adjustArticleResionName = GeoUtil.adjustArticleRegionName(article);
        assertThat("충청남도 충청북도 경상북도 경상남도").isEqualTo(adjustArticleResionName);
    }
}
