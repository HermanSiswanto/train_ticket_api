package train_ticket_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTrainRequest {

    @NotBlank(message = "Train code is required")
    @Size(max = 20, message = "Train code must not exceed 20 characters")
    @Pattern(regexp = "^$|^[A-Za-z0-9]+$", message = "Train code may only contain alphanumeric characters without spaces")
    private String trainCode;

    @NotBlank(message = "Train name is required")
    @Size(max = 100, message = "Train name must not exceed 100 characters")
    private String trainName;
}
