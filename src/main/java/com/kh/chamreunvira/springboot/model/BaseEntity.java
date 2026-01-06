package com.kh.chamreunvira.springboot.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;



@Data
@MappedSuperclass
public class BaseEntity<I , T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private I id;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at" , nullable = false , updatable = false)
    private T createdAt;
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    @Column(name = "update_at" , insertable = false)
    private T updatedAt;

}
