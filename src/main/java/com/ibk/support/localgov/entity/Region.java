package com.ibk.support.localgov.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "REGION")
@Data
public class Region {

    @Id
    @Column(name = "REGION_CODE", nullable = false, length = 100)
    private String regionCode;

    @Column(name = "REGION", nullable = false, length = 200)
    private String region;
}
