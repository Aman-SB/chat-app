package com.aman.chat_application.Repository;

import com.aman.chat_application.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByAuthority(String authority);
}
