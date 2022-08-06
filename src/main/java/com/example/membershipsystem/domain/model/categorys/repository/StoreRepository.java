package com.example.membershipsystem.domain.model.categorys.repository;

import com.example.membershipsystem.domain.model.categorys.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Override
    Optional<Store> findById(Long id);

    boolean existsByIdAndUseYnAndDelYn(Long id, boolean useYn, boolean delYn);
}
