package com.kakaopay.support.bank.util;

import com.kakaopay.support.bank.model.search.Rate;

public class ConvertUtil {

    public static final String LIMIT_BILLION  = "억원 이내";
    public static final String LIMIT_MILLION = "백만원 이내";

    public static final String LIMIT_SIMPLE_BILLION  = "억";
    public static final String LIMIT_SIMPLE_MILLION = "백만원";


    public static final String RANGE_RATE_DELIMETER  = "~";
    public static final String RATE_ALL  = "대출이자 전액";

    public static final String ONLY_NUMBER_REGEX  = "[^0-9.]";

    private ConvertUtil() {

    }

    public static long limitConvertToNumber(String limit) {
        if (limit.indexOf(LIMIT_BILLION) >= 0 || limit.indexOf(LIMIT_SIMPLE_BILLION) >= 0) {
            return Long.parseLong(onlyNumStr(limit)) * 100000000;
        } else if (limit.indexOf(LIMIT_MILLION) >= 0 || limit.indexOf(LIMIT_SIMPLE_MILLION) >= 0) {
            return Long.parseLong(onlyNumStr(limit)) * 1000000;
        } else {
            return 0;
        }
    }

    public static Rate rateConvertToNumber(String rateNaturalStr) {
        if (rateNaturalStr.indexOf(RATE_ALL) >= 0) {
            return new Rate(100d, 100d);
        } else if (rateNaturalStr.indexOf(RANGE_RATE_DELIMETER) >= 0) {
            String[] rateArr = rateNaturalStr.split(RANGE_RATE_DELIMETER);
            double rateMin = Double.parseDouble(onlyNumStr(rateArr[0]));
            double rateMax = Double.parseDouble(onlyNumStr(rateArr[1]));
            return new Rate(rateMin, rateMax);
        } else {
            double rate = Double.parseDouble(onlyNumStr(rateNaturalStr));
            return new Rate(rate, rate);
        }
    }

    public static Double rateAvg(String rateNaturalStr) {
        Rate rate = rateConvertToNumber(rateNaturalStr);
        return (rate.getMin() + rate.getMax()) / 2;
    }

    public static String onlyNumStr (String str) {
        return str.replaceAll(ONLY_NUMBER_REGEX, "");
    }
}
