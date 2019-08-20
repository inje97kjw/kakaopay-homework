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
    @JoinColumn(name = "REGION_CODE")
    private Region region;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REG_DATE", nullable = false, updatable = false)
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;


}
