package com.aman.chat_application.Repository;

import com.aman.chat_application.Model.FriendRequest;
import com.aman.chat_application.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    // Check if a friend request already exists between two users
    boolean existsBySenderAndReceiver(User sender, User receiver);

    // Find friend requests sent by a specific user
    List<FriendRequest> findBySender(User sender);

    // Find friend requests received by a specific user
    List<FriendRequest> findByReceiver(User receiver);

    // Find a specific friend request between two users
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);
}
