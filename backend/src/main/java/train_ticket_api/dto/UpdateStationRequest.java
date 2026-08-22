package train_ticket_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStationRequest {

    @NotBlank(message = "Station code is required")
    @Size(max = 10, message = "Station code must not exceed 10 characters")
    @Pattern(regexp = "^$|^[A-Za-z]+$", message = "Station code may only contain letters without spaces")
    private String stationCode;

    @NotBlank(message = "Station name is required")
    @Size(max = 100, message = "Station name must not exceed 100 characters")
    private String stationName;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

}