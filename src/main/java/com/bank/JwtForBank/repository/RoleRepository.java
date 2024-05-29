package com.bank.JwtForBank.repository;

import com.bank.JwtForBank.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(String roleName);
}
