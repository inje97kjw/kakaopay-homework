package com.kakaopay.support.bank.model.search;

import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.util.ConvertUtil;

import java.util.List;

public enum Sort {
    DEFAULT {
        @Override
        public List<BankSupport> customSort(List<BankSupport> bankSupportList, int count) {
            return bankSupportList.subList(0 , count);
        }
        @Override
        public List<BankSupport> geocordingSort(List<BankSupport> responceBankSupportList, double lat, double lon) {
            return responceBankSupportList;
        }
    },
    LIMIT_DESC {
        @Override
        public List<BankSupport> customSort(List<BankSupport> bankSupportList, int count) {
            bankSupportList.sort((o1, o2) -> {
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
            });
            return bankSupportList.subList(0 , count);
        }
        @Override
        public List<BankSupport> geocordingSort(List<BankSupport> responceBankSupportList, double lat, double lon) {
            return responceBankSupportList;
        }
    },
    RATE_ASC {
        @Override
        public List<BankSupport> customSort(List<BankSupport> bankSupportList, int count) {
            return bankSupportList.subList(0 , count);
        }

        @Override
        public List<BankSupport> geocordingSort(List<BankSupport> responceBankSupportList, double lat, double lon) {

            

            return responceBankSupportList;
        }
    },
    GEOCORDING_ASC {
        @Override
        public List<BankSupport> customSort(List<BankSupport> bankSupportList, int count) {
            bankSupportList.sort((o1, o2) -> {
                Rate rate1 = ConvertUtil.rateConvertToNumber(o1.getRate());
                Rate rate2 = ConvertUtil.rateConvertToNumber(o2.getRate());
                if (rate1.getMin() > rate2.getMin()) {
                    return 1;
                } else if (rate1.getMin() < rate2.getMin()) {
                    return -1;
                } else {
                    return 0;
                }
            });
            return bankSupportList.subList(0 , count);
        }

        @Override
        public List<BankSupport> geocordingSort(List<BankSupport> responceBankSupportList, double lat, double lon) {
            return responceBankSupportList;
        }
    };
    public abstract List<BankSupport> customSort(List<BankSupport> responceBankSupportList, int count);
    public abstract List<BankSupport> geocordingSort(List<BankSupport> responceBankSupportList, double lat, double lon);

}
