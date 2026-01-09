package com.kh.chamreunvira.springboot.repository;

import com.kh.chamreunvira.springboot.enumz.FriendRequestStatus;
import com.kh.chamreunvira.springboot.model.FriendRequest;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.service.FriendRequestService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest , Long> {

    Optional<FriendRequest> findBySenderAndReceiver(User sender , User receiver);
    List<FriendRequest> findBySenderAndStatusOrReceiverAndStatus(
            User sender, FriendRequestStatus statusOne, User receiver, FriendRequestStatus statusTwo
    );
    List<FriendRequest> findByReceiverAndStatus(User receiver, FriendRequestStatus status);
    Optional<FriendRequest> findBySenderAndReceiverAndStatus(User userId , User friend , FriendRequestStatus status);

}
