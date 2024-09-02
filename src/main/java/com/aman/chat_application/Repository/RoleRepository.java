package com.aman.chat_application.Repository;

import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("SELECT r FROM Role r WHERE r.roleName = :roleName")
    Optional<Role> findByRoleName(@Param("roleName") AppRole roleName);
}
