package com.aman.chat_application.Config;

import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Model.Role;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.RoleRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Config.Service.UserDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository){
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            if(!userRepository.existsByUserName("user1")){
                User user1 = User.builder()
                        .userName("user1")
                        .fullName("TestingUser")
                        .email("user1@example.com")
                        .password("{noop}password")
                        .accountNonLocked(false)
                        .accountNonExpired(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .credentialsExpiryDate(LocalDate.now().plusYears(1))
                        .accountExpiryDate(LocalDate.now().plusYears(1))
                        .isTwoFactorEnabled(false)
                        .signUpMethod("email")
                        .role(userRole)
                        .build();

                userRepository.save(user1);
            }

            if(!userRepository.existsByUserName("admin")){
                User admin = User.builder()
                        .userName("admin")
                        .fullName("TestingAdmin")
                        .email("admin@example.com")
                        .password("{noop}password")
                        .accountNonLocked(false)
                        .accountNonExpired(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .credentialsExpiryDate(LocalDate.now().plusYears(1))
                        .accountExpiryDate(LocalDate.now().plusYears(1))
                        .isTwoFactorEnabled(false)
                        .signUpMethod("email")
                        .role(adminRole)
                        .build();

                userRepository.save(admin);
            }
        };
    }














    //testing
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        if(!manager.userExists("user1")){
//            manager.createUser(
//                    User.withUsername("user1")
//                            .password("{noop}password1")
//                            .roles("USER")
//                            .build()
//            );
//        }
//        if(!manager.userExists("admin")){
//            manager.createUser(
//                    User.withUsername("admin")
//                            .password("{noop}password1")
//                            .roles("ADMIN")
//                            .build()
//            );
//        }
//        return manager;
//    }
}
