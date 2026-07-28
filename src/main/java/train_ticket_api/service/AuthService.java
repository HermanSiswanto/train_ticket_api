package train_ticket_api.service;

import train_ticket_api.dto.AuthResponse;
import train_ticket_api.dto.LoginRequest;
import train_ticket_api.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}