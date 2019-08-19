package com.ibk.support.localgov.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LOCAL_GOV_CODE")
@Data
public class LocalGovCode {

    @Id
    @Column(name = "CODE", nullable = false, length = 100)
    private String localGovCode;

    @Column(name = "NAME", nullable = false, length = 200)
    private String localGovName;
}
