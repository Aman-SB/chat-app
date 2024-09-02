package com.aman.chat_application.Repository;

import com.aman.chat_application.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {
    List<Chat> findByChatNameContainingIgnoreCase(String name);
}
