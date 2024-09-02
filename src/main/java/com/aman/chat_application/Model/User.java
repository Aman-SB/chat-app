package com.aman.chat_application.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users" ,
uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    String userName;

    @Column(nullable = false)
    String fullName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "email")
    String email;

    @Size(max = 120)
    @Column(nullable = false, name = "password")
    @JsonIgnore
    String password;

    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    LocalDate credentialsExpiryDate;
    LocalDate accountExpiryDate;

    private String twoFactorySecret;
    boolean isTwoFactorEnabled = false;
    private String signUpMethod;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    @JsonBackReference
    @ToString.Exclude
    Role role;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private Set<Chat> chats = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdDate;

    @UpdateTimestamp
    LocalDateTime updateDate;

    @Size(max = 255)
    private String bio;

    @Size(max = 255)
    private String profilePictureUrl;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiry_date")
    private LocalDateTime tokenExpiryDate;


    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(!(o instanceof User))return false;
        return userId != null && userId.equals(((User) o).getUserId());
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }
}
