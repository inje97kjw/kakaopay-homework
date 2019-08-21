package com.kakaopay.support.repository;

import com.kakaopay.support.entity.RegionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionCodeRepository extends JpaRepository<RegionCode, Long> {
}
