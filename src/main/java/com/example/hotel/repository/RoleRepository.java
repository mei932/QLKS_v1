package com.example.hotel.repository;

import com.example.hotel.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  @Transactional
  Role findByRoleName(String roleName);
}
