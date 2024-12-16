package com.sh.hexagonal.adapter.out.persistence;

import com.sh.hexagonal.application.domain.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // 쿼리 기입
}
