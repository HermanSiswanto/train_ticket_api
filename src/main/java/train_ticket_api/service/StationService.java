
package train_ticket_api.service;

import train_ticket_api.dto.CreateStationRequest;
import train_ticket_api.dto.UpdateStationRequest;
import train_ticket_api.dto.UpdateStatusRequest;
import train_ticket_api.entity.Station;

import java.util.List;

public interface StationService {

    List<Station> getAllStations();

    Station getStationById(Long id);

    Station createStation(CreateStationRequest request);

    Station updateStation(Long id, UpdateStationRequest request);

    Station updateStationStatus(
            Long id,
            UpdateStatusRequest request
    );

    void deleteStation(Long id);
}
