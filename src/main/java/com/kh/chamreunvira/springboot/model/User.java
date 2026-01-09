package com.kh.chamreunvira.springboot.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kh.chamreunvira.springboot.enumz.Role;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity<Long , LocalDate> implements UserDetails {

    @Column(name = "username" , nullable = false , length = 50)
    private String username;
    @Column(name = "image" , nullable = false)
    private String profile;
    @Column(name = "email" , nullable = false , unique = true , length = 100)
    private String email;
    @Column(name = "password" , nullable = false , length = 255)
    private String password;
    @Column(name = "roles" , nullable = false)
    private List<Role> roles;

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts;

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Like> likes;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<FriendRequest> sendRequest;

    @OneToMany(mappedBy = "receiver" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<FriendRequest> receiveRequest;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> {
            String role = r.name();
            if(!role.startsWith("ROLE_")) {
                role = "ROLE_" + r.name();
            }
            return new SimpleGrantedAuthority(role);
        }).toList();
    }

    public String getFullName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
