package com.aman.chat_application.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "chats")
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", unique = true , nullable = false)
    Integer chatId;

    @Column(nullable = true)
    String chatName;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @ManyToMany(mappedBy = "chats")
    Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    Set<Message> messages = new HashSet<>();
}
