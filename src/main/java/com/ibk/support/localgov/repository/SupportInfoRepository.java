package com.ibk.support.localgov.repository;

import com.ibk.support.localgov.entity.SupportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportInfoRepository extends JpaRepository<SupportInfo, Long>, SupportInfoRepositoryCustom {
    SupportInfo findByRegionRegionCode(String regionCode);
}
