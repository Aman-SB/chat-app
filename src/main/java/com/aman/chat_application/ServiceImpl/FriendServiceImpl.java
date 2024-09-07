package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Dto.UserDto.FriendRequestDto;
import com.aman.chat_application.Enumerator.FriendRequestStatus;
import com.aman.chat_application.Exception.UserNotFoundException;
import com.aman.chat_application.Model.FriendRequest;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.FriendRequestRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Service.FriendService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;

    private final FriendRequestRepository friendRequestRepository;

    @Autowired
    public FriendServiceImpl(UserRepository userRepository, FriendRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    @Transactional
    public void sendFriendRequest(FriendRequestDto requestDto) {
        // Validate sender and receiver exist in the system
        User sender = userRepository.findById(requestDto.getSenderId())
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));

        User receiver = userRepository.findById(requestDto.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        // Check if a friend request already exists between these users
        if (friendRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new IllegalStateException("Friend request already sent");
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .message(requestDto.getMessage())
                .status(FriendRequestStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .build();

        friendRequestRepository.save(friendRequest);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Integer requestId) {
        // Find the friend request by its ID
        FriendRequest existingRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        // Update the status to ACCEPTED using the builder pattern
        FriendRequest updatedRequest = FriendRequest.builder()
                .requestId(existingRequest.getRequestId())
                .sender(existingRequest.getSender())
                .receiver(existingRequest.getReceiver())
                .message(existingRequest.getMessage())
                .status(FriendRequestStatus.ACCEPTED)
                .timestamp(existingRequest.getTimestamp())
                .build();

        // Save the updated friend request
        friendRequestRepository.save(updatedRequest);
    }

    @Override
    @Transactional
    public void declineFriendRequest(Integer requestId) {
        FriendRequest existingRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        // Update the status to DECLINED using the builder pattern
        FriendRequest updatedRequest = FriendRequest.builder()
                .requestId(existingRequest.getRequestId())
                .sender(existingRequest.getSender())
                .receiver(existingRequest.getReceiver())
                .message(existingRequest.getMessage())
                .status(FriendRequestStatus.DECLINED)
                .timestamp(existingRequest.getTimestamp())
                .build();

        // Save the updated friend request
        friendRequestRepository.save(updatedRequest);
    }
}
