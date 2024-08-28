package com.aman.chat_application.Model;

import com.aman.chat_application.Enumerator.MessageStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "messages")
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    Integer messageId;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    MessageStatus status;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime timestamp;
}
