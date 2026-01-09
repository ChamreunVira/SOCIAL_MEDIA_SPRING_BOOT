package com.kh.chamreunvira.springboot.model;

import com.kh.chamreunvira.springboot.enumz.FriendRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_friend_request")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id" , referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver" , referencedColumnName = "id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    @CreationTimestamp
    private LocalDate created;

}
