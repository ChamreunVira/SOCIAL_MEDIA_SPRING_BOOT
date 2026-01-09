package com.kh.chamreunvira.springboot.service.impl;

import com.kh.chamreunvira.springboot.dto.FriendResponse;
import com.kh.chamreunvira.springboot.dto.FriendSenderResponse;
import com.kh.chamreunvira.springboot.enumz.FriendRequestStatus;
import com.kh.chamreunvira.springboot.exception.CustomMessageException;
import com.kh.chamreunvira.springboot.model.FriendRequest;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.FriendRequestRepository;
import com.kh.chamreunvira.springboot.repository.UserRepository;
import com.kh.chamreunvira.springboot.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    @Override
    public void sendRequest(Long sender, Long receiver) {

        User userSender = userRepository.findById(sender)
                .orElseThrow(() -> new UsernameNotFoundException("User sender not found."));
        User userReceiver = userRepository.findById(receiver)
                .orElseThrow(() -> new UsernameNotFoundException("User receiver not found."));

        if(friendRequestRepository.findBySenderAndReceiver(userSender , userReceiver).isPresent()
            || friendRequestRepository.findBySenderAndReceiver(userReceiver , userSender).isPresent()
        ) {
            throw new CustomMessageException("Friend is already request" , HttpStatus.BAD_REQUEST.value());
        }
        if(userSender.equals(userReceiver)) {
            throw new CustomMessageException("You can request friend to your self." , HttpStatus.BAD_REQUEST.value());
        }
        FriendRequest friend = new FriendRequest();
        friend.setSender(userSender);
        friend.setReceiver(userReceiver);
        friend.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepository.save(friend);
    }

    @Override
    public void acceptFriend(Long requestId, Long userId) {
        FriendRequest friend = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend Request not found."));
        if(!friend.getReceiver().getId().equals(userId)) {
            throw new CustomMessageException("You don't have permission to accept this friend." , HttpStatus.BAD_REQUEST.value());
        }
        friend.setStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.save(friend);
    }

    @Override
    public void rejectFriend(Long requestId, Long userId) {
        FriendRequest friend = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found with requestId: " + requestId));
        if (!friend.getReceiver().getId().equals(userId)) {
            throw new CustomMessageException("You don't have permission to accept this friend.", HttpStatus.BAD_REQUEST.value());
        }
        friend.setStatus(FriendRequestStatus.REJECTED);
        friendRequestRepository.save(friend);
    }

    @Override
    public List<FriendResponse> getFriend(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        List<FriendRequest> relations = friendRequestRepository.findBySenderAndStatusOrReceiverAndStatus(
                user,
                FriendRequestStatus.ACCEPTED,
                user,
                FriendRequestStatus.ACCEPTED
        );

        return relations
                .stream()
                .map(fr -> {
                    FriendResponse response = new FriendResponse();
                    if(fr.getSender().equals(user)) {
                        response.setUserId(fr.getReceiver().getId());
                        response.setUsername(fr.getReceiver().getFullName());
                        return response;
                    }
                    response.setUserId(fr.getSender().getId());
                    response.setUsername(fr.getSender().getFullName());
                    return response;
                })
                .toList();
    }

    @Override
    public List<FriendSenderResponse> getRequestPending(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+ userId));
        List<FriendRequest> friendRequests = friendRequestRepository.findByReceiverAndStatus(user , FriendRequestStatus.PENDING);
        return friendRequests
                .stream()
                .map(fr -> {
                    FriendSenderResponse senderResponse = new FriendSenderResponse();
                    senderResponse.setId(fr.getId());
                    FriendResponse response = new FriendResponse();
                    response.setUserId(fr.getSender().getId());
                    response.setUsername(fr.getSender().getFullName());
                    senderResponse.setSender(response);
                    senderResponse.setStatus(fr.getStatus());
                    return senderResponse;
                })
                .toList();
    }

    @Override
    public void deleteFriend(long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UsernameNotFoundException("Friend not found with id: " + friendId));

        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiverAndStatus(user , friend , FriendRequestStatus.ACCEPTED)
                .orElseThrow(() -> new CustomMessageException("Users are not friendship." , HttpStatus.BAD_REQUEST.value()));

        friendRequestRepository.delete(friendRequest);
    }

}
