package com.ibk.support.localgov.model.search;

import com.ibk.support.localgov.util.ConvertUtil;

import java.util.Comparator;
import java.util.List;

public enum Sort {
    DEFAULT {
        @Override
        public List<SearchSupportInfo> getResultType(List<SearchSupportInfo> searchSupportInfoList, int count) {
            return searchSupportInfoList.subList(0 , count);
        }
    },
    LIMIT_DESC {
        @Override
        public List<SearchSupportInfo> getResultType(List<SearchSupportInfo> searchSupportInfoList, int count) {
            searchSupportInfoList.sort(new Comparator<SearchSupportInfo>() {
                @Override
                public int compare(SearchSupportInfo o1, SearchSupportInfo o2) {
                    long limit1 = ConvertUtil.limitConvertToNumber(o1.getLimit());
                    long limit2 = ConvertUtil.limitConvertToNumber(o2.getLimit());
                    double rate1 = ConvertUtil.rateAvg(o1.getRate());
                    double rate2 = ConvertUtil.rateAvg(o2.getRate());
                    if (limit1 < limit2) {
                        return 1;
                    } else if (limit1 > limit2) {
                        return -1;
                    } else {
                        if (rate1 > rate2) {
                          return 1;
                        } else if (rate1 < rate2) {
                          return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
            return searchSupportInfoList.subList(0 , count);
        }
    },
    RATE_ASC {
        @Override
        public List<SearchSupportInfo> getResultType(List<SearchSupportInfo> searchSupportInfoList, int count) {
            searchSupportInfoList.sort(new Comparator<SearchSupportInfo>() {
                @Override
                public int compare(SearchSupportInfo o1, SearchSupportInfo o2) {
                    Rate rate1 = ConvertUtil.rateConvertToNumber(o1.getRate());
                    Rate rate2 = ConvertUtil.rateConvertToNumber(o2.getRate());
                    if (rate1.getMin() > rate2.getMin()) {
                        return 1;
                    } else if (rate1.getMin() < rate2.getMin()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            return searchSupportInfoList.subList(0 , count);
        }
    };

    public abstract List<SearchSupportInfo> getResultType(List<SearchSupportInfo> searchSupportInfoList, int count);
}
