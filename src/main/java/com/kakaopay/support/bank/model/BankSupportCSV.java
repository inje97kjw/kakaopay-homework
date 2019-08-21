package com.kakaopay.support.bank.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "region", "target", "usage", "limit", "rate", "institute", "mgmt", "reception" })
public class BankSupportCSV {
    private int id;
    private String region;
    private String target;
    private String usage;
    private String limit;
    private String rate;
    private String institute;
    private String mgmt;
    private String reception;

}
