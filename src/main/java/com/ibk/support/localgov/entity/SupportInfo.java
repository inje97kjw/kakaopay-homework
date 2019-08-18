package com.ibk.support.localgov.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SUPPORT_INFO")
@Data
public class SupportInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "REGION_CODE", nullable = false)
    private String regionCode;

    @Column(name = "REGION_NAME", nullable = false)
    private String regionName;

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

    @Column(name = "LAT", nullable = true)
    @ColumnDefault("0.0")
    private double lat;

    @Column(name = "LON", nullable = true)
    @ColumnDefault("0.0")
    private double lon;
}
