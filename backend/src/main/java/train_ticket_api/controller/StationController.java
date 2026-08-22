package train_ticket_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import train_ticket_api.dto.CreateStationRequest;
import train_ticket_api.dto.UpdateStationRequest;
import train_ticket_api.dto.UpdateStatusRequest;
import train_ticket_api.entity.Station;
import train_ticket_api.service.StationService;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public List<Station> getAllStations() {
        return stationService.getAllStations();
    }

    @GetMapping("/{id}")
    public Station getStationById(@PathVariable Long id) {
        return stationService.getStationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Station createStation(
            @Valid @RequestBody CreateStationRequest request
    ) {
        return stationService.createStation(request);
    }

    @PutMapping("/{id}")
    public Station updateStation(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStationRequest request
    ) {
        return stationService.updateStation(id, request);
    }

    @PatchMapping("/{id}/status")
    public Station updateStationStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request
    ) {
        return stationService.updateStationStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
    }
}
