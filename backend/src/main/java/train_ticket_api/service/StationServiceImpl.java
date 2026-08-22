package train_ticket_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import train_ticket_api.dto.CreateStationRequest;
import train_ticket_api.dto.UpdateStationRequest;
import train_ticket_api.dto.UpdateStatusRequest;
import train_ticket_api.exception.BusinessValidationException;
import train_ticket_api.entity.Station;
import train_ticket_api.exception.DuplicateResourceException;
import train_ticket_api.exception.ResourceNotFoundException;
import train_ticket_api.repository.StationRepository;

import org.springframework.data.domain.Sort;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll(
            Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public Station getStationById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Station not found with ID: " + id));
    }

    @Override
    public Station createStation(CreateStationRequest request) {

        String stationCode = request.getStationCode()
                .trim()
                .toUpperCase();

        if (stationRepository.existsByStationCode(stationCode)) {
            throw new DuplicateResourceException("Station code already exists");
        }

        Station station = Station.builder()
                .stationCode(stationCode)
                .stationName(request.getStationName().trim())
                .city(request.getCity().trim())
                .build();

        return stationRepository.save(station);
    }

    @Override
    public Station updateStation(Long id, UpdateStationRequest request) {

        Station station = stationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Station not found with ID: " + id));

        String stationCode = request.getStationCode()
                .trim()
                .toUpperCase();

        stationRepository.findByStationCode(stationCode)
                .ifPresent(existingStation -> {
                    if (!existingStation.getId().equals(id)) {
                        throw new DuplicateResourceException("Station code already exists");
                    }
                });

        station.setStationCode(stationCode);
        station.setStationName(request.getStationName().trim());
        station.setCity(request.getCity().trim());

        return stationRepository.save(station);
    }

    @Override
    public Station updateStationStatus(
            Long id,
            UpdateStatusRequest request
    ) {

        Station station = stationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Station not found with ID: " + id
                        ));

        String status = request.getStatus().trim();

        if (status.isBlank()) {
            throw new BusinessValidationException(
                    "Status is required"
            );
        }

        if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
            throw new BusinessValidationException(
                    "Status must be either ACTIVE or INACTIVE"
            );
        }

        if (station.getStatus().equals(status)) {
            throw new BusinessValidationException(
                    "Station is already " + status
            );
        }

        station.setStatus(status);

        return stationRepository.save(station);
    }

    @Override
    public void deleteStation(Long id) {

        Station station = stationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Station not found with ID: " + id));

        stationRepository.delete(station);
    }
}
