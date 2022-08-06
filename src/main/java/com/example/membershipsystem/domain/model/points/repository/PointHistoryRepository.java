package com.example.membershipsystem.domain.model.points.repository;

import com.example.membershipsystem.domain.model.points.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer>, PointHistoryRepositoryCustom {
}
