package train_ticket_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import train_ticket_api.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByStationCode(String stationCode);

    boolean existsByStationCode(String stationCode);

}