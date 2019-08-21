package com.kakaopay.support.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BANK_SUPPORT")
@Getter
@Setter
public class BankSupport {

    @Id
    @Column(name = "ID", nullable = false)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CODE")
    private RegionCode regionCode;

    @Column(name = "TARGET", nullable = false)
    private String target;

    @Column(name = "USAGE", nullable = false)
    private String usage;

    @Column(name = "`LIMIT`", nullable = false)
    private String limit;

    @Column(name = "RATE", nullable = false)
    private String rate;

    @Column(name = "INSTITUTE", nullable = false)
    private String institute;

    @Column(name = "MGMT", nullable = false)
    private String mgmt;

    @Column(name = "RECEPTION", nullable = false)
    private String reception;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    public BankSupport() {}

    @Builder
    public BankSupport(int id, RegionCode regionCode, String target, String usage, String limit, String rate, String institute, String mgmt, String reception, LocalDateTime regDate) {
        this.id = id;
        this.regionCode = regionCode;
        this.target = target;
        this.usage = usage;
        this.limit = limit;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
        this.regDate = regDate;
    }
}
