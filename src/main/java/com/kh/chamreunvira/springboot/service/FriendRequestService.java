package com.kh.chamreunvira.springboot.service;

import com.kh.chamreunvira.springboot.dto.FriendResponse;
import com.kh.chamreunvira.springboot.dto.FriendSenderResponse;

import java.util.List;

public interface FriendRequestService {
    void sendRequest(Long sender , Long receiver);
    void acceptFriend(Long requestId , Long userId);
    void rejectFriend(Long requestId , Long userId);
    List<FriendResponse> getFriend(Long userId);
    List<FriendSenderResponse> getRequestPending(Long userId);
    void deleteFriend(long userId , Long friendId);

}
