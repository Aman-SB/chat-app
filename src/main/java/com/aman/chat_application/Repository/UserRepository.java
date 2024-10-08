package com.aman.chat_application.Repository;

import com.aman.chat_application.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "Select * from users where username=?1",nativeQuery = true)
    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String query, String query1);
}
