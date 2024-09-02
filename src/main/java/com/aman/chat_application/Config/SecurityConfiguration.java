package com.aman.chat_application.Config;

import com.aman.chat_application.Config.Jwt.JwtAuthenticationFilter;
import com.aman.chat_application.Config.Jwt.JwtService;
import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Model.Role;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.RoleRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.ServiceImpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDate;
import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfiguration(JwtService jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth/register", "/v1/auth/login", "/v1/auth/reset-password").permitAll()
                        .requestMatchers("/v1/auth/change-password", "/v1/auth/two-factor/enable", "/v1/auth/two-factor/disable").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            if (!userRepository.existsByUserName("user1")) {
                User user1 = User.builder()
                        .userName("user1")
                        .fullName("TestingUser")
                        .email("user1@example.com")
                        .password(passwordEncoder().encode("password"))
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

            if (!userRepository.existsByUserName("admin")) {
                User admin = User.builder()
                        .userName("admin")
                        .fullName("TestingAdmin")
                        .email("admin@example.com")
                        .password(passwordEncoder().encode("password"))
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

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("your-email@gmail.com");
        mailSender.setPassword("your-email-password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
