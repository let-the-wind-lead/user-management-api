package com.empress.usermanagementapi.repository;

import com.empress.usermanagementapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
