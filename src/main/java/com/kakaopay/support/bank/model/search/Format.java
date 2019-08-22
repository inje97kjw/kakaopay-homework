package com.kakaopay.support.bank.model.search;

import com.kakaopay.support.bank.service.format.BasicFormat;
import com.kakaopay.support.bank.service.format.CustomFormat;

public enum Format {
    BASIC {
        @Override
        public CustomFormat createFormat(){
            return new BasicFormat();
        }
    }
    ;
    public abstract CustomFormat createFormat();

}
