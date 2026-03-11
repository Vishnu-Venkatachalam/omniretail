package com.cognizant.omniretail.config;

import com.cognizant.omniretail.model.User;
import com.cognizant.omniretail.model.enums.Role;
import com.cognizant.omniretail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@demo.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .name("Admin")
                    .email(adminEmail)
                    .phone("9876543210")
                    .passwordHash(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .status("ACTIVE")
                    .build();

            userRepository.save(admin);

            System.out.println("✅ Default ADMIN created: " + adminEmail);
            System.out.println("✅ Password: Admin@123");
        }
    }
}