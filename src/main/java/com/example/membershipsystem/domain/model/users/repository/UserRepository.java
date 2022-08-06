package com.example.membershipsystem.domain.model.users.repository;

import com.example.membershipsystem.domain.model.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
