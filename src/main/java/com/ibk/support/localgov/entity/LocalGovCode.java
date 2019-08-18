package com.ibk.support.localgov.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LOCAL_GOV_CODE")
@Data
public class LocalGovCode {

    @Id
    @Column(name = "REGION_CODE", nullable = false, length = 100)
    private String regionCode;

    @Column(name = "NAME", nullable = false, length = 200)
    private String regionName;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "REGION_CODE")
    private SupportInfo supportInfo;
}
