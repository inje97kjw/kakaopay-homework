package com.ibk.support.localgov.repository;

import com.ibk.support.localgov.entity.LocalGovCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalGovRepository extends JpaRepository<LocalGovCode, Long> {
}
