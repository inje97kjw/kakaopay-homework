package com.ibk.support.localgov.util;

import com.ibk.support.localgov.model.search.Rate;

public class ConvertUtil {

    public static String limitBillion  = "억원 이내";
    public static String limitMillion = "백만원 이내";

    public static String rangeRateDelimeter  = "~";
    public static String rateAll  = "대출이자 전액";

    public static long limitConvertToNumber(String limit) {
        if (limit.indexOf(limitBillion) >= 0) {
            return Long.parseLong(onlyNumStr(limit)) * 100000000;
        } else if (limit.indexOf(limitMillion) >= 0) {
            return Long.parseLong(onlyNumStr(limit)) * 1000000;
        } else {
            return 0;
        }
    }

    public static Rate rateConvertToNumber(String rateNaturalStr) {
        if (rateNaturalStr.indexOf(rateAll) >= 0) {
            return new Rate(100d, 100d);
        } else if (rateNaturalStr.indexOf(rangeRateDelimeter) >= 0) {
            String[] rateArr = rateNaturalStr.split(rangeRateDelimeter);
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
        return str.replaceAll("[^0-9.]", "");
    }
}
