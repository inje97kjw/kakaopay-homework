package com.kakaopay.support.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REGION")
@Getter
public class RegionCode {

    @Id
    @Column(name = "CODE", nullable = false, length = 100)
    private String code;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    public RegionCode() {}

    @Builder
    public RegionCode(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
