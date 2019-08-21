package com.kakaopay.support.bank.util;

import com.kakaopay.support.bank.util.ConvertUtil;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConvertUtilTest {

    @Test
    public void limitConvert_test() {
        long limitNumber = ConvertUtil.limitConvertToNumber("8억원 이내");
        assertThat(800000000).isEqualTo(limitNumber);

        limitNumber = ConvertUtil.limitConvertToNumber("30백만원 이내");
        assertThat(30000000).isEqualTo(limitNumber);

        limitNumber = ConvertUtil.limitConvertToNumber("추천금액 이내");
        assertThat(0).isEqualTo(limitNumber);
    }

    @Test
    public void rateConvert_test() {
        double avg = ConvertUtil.rateAvg("3%");
        assertThat(3.0).isEqualTo(avg);

        avg = ConvertUtil.rateAvg("1.50%~3.0%");
        assertThat(2.25).isEqualTo(avg);
    }
}
