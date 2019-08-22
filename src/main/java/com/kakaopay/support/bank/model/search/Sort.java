package com.kakaopay.support.bank.model.search;

import com.kakaopay.support.bank.service.sort.CustomSort;
import com.kakaopay.support.bank.service.sort.LimitSort;
import com.kakaopay.support.bank.service.sort.RateSort;

public enum Sort {
    LIMIT {
        @Override
        public CustomSort createSort(){
            return new LimitSort();
        }
    },
    RATE {
        @Override
        public CustomSort createSort(){
            return new RateSort();
        }
    }
    ;
    public abstract CustomSort createSort();

}
