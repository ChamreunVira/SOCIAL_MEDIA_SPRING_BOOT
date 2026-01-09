package com.kh.chamreunvira.springboot.controller;

import com.kh.chamreunvira.springboot.dto.FriendResponse;
import com.kh.chamreunvira.springboot.dto.FriendSenderResponse;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.security.AuthService;
import com.kh.chamreunvira.springboot.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final AuthService authService;

    @PostMapping("request/{receiverId}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable Long receiverId) {
        User user = authService.getCurrentUser();
        friendRequestService.sendRequest(user.getId() , receiverId);
        return ResponseEntity.ok().body("Friend request send successfully.");
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long requestId) {
        User user = authService.getCurrentUser();
        friendRequestService.acceptFriend(requestId , user.getId());
        return ResponseEntity.ok().body("Friend accept successfully.");
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectFriendRequest(@PathVariable Long requestId) {
        User user = authService.getCurrentUser();
        friendRequestService.rejectFriend(requestId , user.getId());
        return ResponseEntity.ok().body("Friend reject successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendResponse>> getAllFriend(@PathVariable Long userId) {
        List<FriendResponse> responses = friendRequestService.getFriend(userId);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("pending/{userId}")
    public ResponseEntity<List<FriendSenderResponse>> getFriendPadding(@PathVariable Long userId) {
        return ResponseEntity.ok().body(friendRequestService.getRequestPending(userId));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable Long friendId) {
        User user = authService.getCurrentUser();
        friendRequestService.deleteFriend(user.getId() , friendId);
        return ResponseEntity.ok().body("Delete friend successfully.");
    }

}
