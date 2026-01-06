package com.kh.chamreunvira.springboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content" , length = 100 , nullable = false)
    private String content;

    @Column(name = "create_at")
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private LocalDate createAt;

    @ManyToOne
    @JoinColumn(name = "post_id" , nullable = false , referencedColumnName = "id")
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false , referencedColumnName = "id")
    @JsonBackReference
    private User user;

}
