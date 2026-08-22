package train_ticket_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import train_ticket_api.dto.AuthResponse;
import train_ticket_api.dto.LoginRequest;
import train_ticket_api.dto.RegisterRequest;
import train_ticket_api.entity.User;
import train_ticket_api.entity.Role;
import train_ticket_api.repository.UserRepository;
import train_ticket_api.repository.RoleRepository;
import train_ticket_api.security.JwtUtil;
import train_ticket_api.exception.DuplicateResourceException;
import train_ticket_api.exception.InvalidCredentialsException;
import train_ticket_api.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException("Email already registered");
        }

        Role userRole = roleRepository
                .findByRoleName("USER")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Default role USER not found")
                );

        User user = new User();

        user.setRole(userRole);

        user.setName(request.getName());
        user.setEmail(email);

        String encodedPassword =
                passwordEncoder.encode(
                        request.getPassword()
                );


        user.setPassword(encodedPassword);

        userRepository.save(user);

        return new AuthResponse(
                "Register success",
                null
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        String email = request.getEmail()
                .trim();

        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtUtil.generateToken(user);

        return new AuthResponse(
                "Login success",
                token
        );
    }
    
}