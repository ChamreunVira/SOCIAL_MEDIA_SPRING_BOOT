package com.kh.chamreunvira.springboot.dto;

import com.kh.chamreunvira.springboot.enumz.FriendRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendSenderResponse {

    private Long id;
    private FriendResponse sender;
    private FriendRequestStatus status;

}


