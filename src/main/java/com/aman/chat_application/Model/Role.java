package com.aman.chat_application.Model;

import com.aman.chat_application.Enumerator.AppRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
@Builder
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20 , name = "role_name")
    AppRole roleName;

    @OneToMany(mappedBy = "role" , fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonBackReference
    @ToString.Exclude
    Set<User> users = new HashSet<>();

    public Role(AppRole roleName){
        this.roleName = roleName;
    }
}
