package train_ticket_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import train_ticket_api.entity.Station;
import train_ticket_api.entity.Train;
import train_ticket_api.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsByTrainAndOriginStationAndDestinationStationAndDepartureTime(
            Train train,
            Station originStation,
            Station destinationStation,
            LocalDateTime departureTime
    );

    boolean existsByTrainAndOriginStationAndDestinationStationAndDepartureTimeAndIdNot(
            Train train,
            Station originStation,
            Station destinationStation,
            LocalDateTime departureTime,
            Long id
    );
}