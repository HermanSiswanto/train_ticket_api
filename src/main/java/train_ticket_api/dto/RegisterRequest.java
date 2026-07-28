package train_ticket_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Pattern(
            regexp = "^$|^[A-Za-z]+(?: [A-Za-z]+)*$",
            message = "Name may only contain letters and single spaces"
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Pattern(
            regexp = "^$|^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format"
    )
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^$|^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\S{8,64}$",
            message = "Password must be at least 8 characters, include uppercase, lowercase, number, and contain no whitespace"
    )
    private String password;

    public void setName(String name) {
        this.name = (name == null) ? null : name.trim();
    }

    public void setEmail(String email) {
        this.email = (email == null) ? null : email.trim();
    }
}
