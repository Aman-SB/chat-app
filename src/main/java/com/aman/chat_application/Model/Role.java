package com.aman.chat_application.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
@Builder
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    Integer roleId;

    String authority;

    public Role(){
        super();
    }

    public Role(String authority){
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return this.authority;
    }


}
