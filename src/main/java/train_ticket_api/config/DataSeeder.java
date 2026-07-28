package train_ticket_api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import train_ticket_api.entity.Role;
import train_ticket_api.entity.User;
import train_ticket_api.repository.RoleRepository;
import train_ticket_api.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_ADMIN_NAME = "Administrator";
    private static final String DEFAULT_ADMIN_EMAIL = "admin@trainticket.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin@123";

    @Override
    public void run(String... args) {

        Role adminRole = roleRepository
                .findByRoleName("ADMIN")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .roleName("ADMIN")
                                .description("System Administrator")
                                .build()
                ));

        roleRepository
                .findByRoleName("USER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .roleName("USER")
                                .description("Customer")
                                .build()
                ));

        if (!userRepository.existsByEmailIgnoreCase(DEFAULT_ADMIN_EMAIL)) {

            User admin = User.builder()
                    .role(adminRole)
                    .name(DEFAULT_ADMIN_NAME)
                    .email(DEFAULT_ADMIN_EMAIL)
                    .password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
                    .build();

            userRepository.save(admin);
        }
    }
}
