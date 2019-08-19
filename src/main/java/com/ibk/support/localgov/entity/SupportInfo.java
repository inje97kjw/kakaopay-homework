package com.ibk.support.localgov.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SUPPORT_INFO")
@Data
public class SupportInfo {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CODE")
    private LocalGovCode localGovCode;

    @Column(name = "LOCALGOV_NAME", nullable = false)
    private String localGovName;

    @Column(name = "TARGET", nullable = false)
    private String target;

    @Column(name = "REASON", nullable = false)
    private String reason;

    @Column(name = "`LIMIT`", nullable = false)
    private String limit;

    @Column(name = "REWARD", nullable = false)
    private String reward;

    @Column(name = "RECOMMEND", nullable = false)
    private String recommend;

    @Column(name = "MANAGER", nullable = false)
    private String manager;

    @Column(name = "DEALER", nullable = false)
    private String dealer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REG_DATE", nullable = false, updatable = false)
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;


}
