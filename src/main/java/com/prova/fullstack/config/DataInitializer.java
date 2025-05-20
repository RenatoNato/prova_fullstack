package com.prova.fullstack.config;

import com.prova.fullstack.entity.Role;
import com.prova.fullstack.entity.User;
import com.prova.fullstack.repository.RoleRepository;
import com.prova.fullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initRolesAndUsers() {
        return args -> {
            Role adminRole = roleRepository.findByNome("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

            Role userRole = roleRepository.findByNome("USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "USER")));

            if (userRepository.findByEmail("admin@email.com").isEmpty()) {
                User admin = User.builder()
                        .nome("Administrador")
                        .email("admin@email.com")
                        .senha(passwordEncoder.encode("admin123"))
                        .roles(List.of(adminRole, userRole))
                        .build();
                userRepository.save(admin);
            }

            if (userRepository.findByEmail("user@email.com").isEmpty()) {
                User user = User.builder()
                        .nome("Usuário Comum")
                        .email("user@email.com")
                        .senha(passwordEncoder.encode("user123"))
                        .roles(List.of(userRole))
                        .build();
                userRepository.save(user);
            }
        };
    }
}
