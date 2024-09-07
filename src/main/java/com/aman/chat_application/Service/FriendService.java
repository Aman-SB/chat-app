package com.aman.chat_application.Service;

import com.aman.chat_application.Dto.UserDto.FriendRequestDto;

public interface FriendService {
    void sendFriendRequest(FriendRequestDto requestDto);

    void acceptFriendRequest(Integer requestId);

    void declineFriendRequest(Integer requestId);
}
