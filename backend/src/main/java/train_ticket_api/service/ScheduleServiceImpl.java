package train_ticket_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import train_ticket_api.dto.CreateScheduleRequest;
import train_ticket_api.dto.UpdateScheduleRequest;
import train_ticket_api.dto.UpdateStatusRequest;
import train_ticket_api.entity.Schedule;
import train_ticket_api.entity.Train;
import train_ticket_api.entity.Station;
import train_ticket_api.exception.ResourceNotFoundException;
import train_ticket_api.exception.BusinessValidationException;
import train_ticket_api.exception.DuplicateResourceException;
import train_ticket_api.repository.ScheduleRepository;
import train_ticket_api.repository.StationRepository;
import train_ticket_api.repository.TrainRepository;
import train_ticket_api.dto.ScheduleResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    @Override
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        )
        .stream()
        .map(this::mapToResponse)
        .toList();
    }

    @Override
    public ScheduleResponse getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Schedule not found with ID: " + id
                ));
                return mapToResponse(schedule);
    }

    @Override
    public ScheduleResponse createSchedule(CreateScheduleRequest request) {
        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Train not found with ID: " + request.getTrainId()
                ));

        Station originStation = stationRepository.findById(request.getOriginStationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Origin station not found with ID: " + request.getOriginStationId()
                ));

        Station destinationStation = stationRepository.findById(request.getDestinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Destination station not found with ID: " + request.getDestinationStationId()
                ));

        if (!"ACTIVE".equals(train.getStatus())) {
            throw new BusinessValidationException("Train is inactive");
        }

        if (!"ACTIVE".equals(originStation.getStatus())) {
            throw new BusinessValidationException("Origin station is inactive");
        }

        if (!"ACTIVE".equals(destinationStation.getStatus())) {
            throw new BusinessValidationException("Destination station is inactive");
        }

        if (originStation.getId().equals(destinationStation.getId())) {
            throw new BusinessValidationException(
                    "Origin and destination stations cannot be the same"
            );
        }

        if (!request.getArrivalTime().isAfter(request.getDepartureTime())) {
            throw new BusinessValidationException(
                    "Arrival time must be after departure time"
            );
        }

        if (scheduleRepository.existsByTrainAndOriginStationAndDestinationStationAndDepartureTime(
                train,
                originStation,
                destinationStation,
                request.getDepartureTime()
        )) {
            throw new DuplicateResourceException("Schedule already exists.");
        }

        Schedule schedule = Schedule.builder()
                .train(train)
                .originStation(originStation)
                .destinationStation(destinationStation)
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .price(request.getPrice())
                .status("ACTIVE")
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return mapToResponse(savedSchedule);
    }

    @Override
    public ScheduleResponse updateSchedule(Long id, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Schedule not found with ID: " + id
                ));

        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Train not found with ID: " + request.getTrainId()
                ));

        Station originStation = stationRepository.findById(request.getOriginStationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Origin station not found with ID: " + request.getOriginStationId()
                ));

        Station destinationStation = stationRepository.findById(request.getDestinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Destination station not found with ID: " + request.getDestinationStationId()
                ));

        if (!"ACTIVE".equals(train.getStatus())) {
            throw new BusinessValidationException("Train is inactive");
        }

        if (!"ACTIVE".equals(originStation.getStatus())) {
            throw new BusinessValidationException("Origin station is inactive");
        }

        if (!"ACTIVE".equals(destinationStation.getStatus())) {
            throw new BusinessValidationException("Destination station is inactive");
        }

        if (originStation.getId().equals(destinationStation.getId())) {
            throw new BusinessValidationException(
                    "Origin and destination stations cannot be the same"
            );
        }

        if (!request.getArrivalTime().isAfter(request.getDepartureTime())) {
            throw new BusinessValidationException(
                    "Arrival time must be after departure time"
            );
        }

        if (scheduleRepository.existsByTrainAndOriginStationAndDestinationStationAndDepartureTimeAndIdNot(

            train,
            originStation,
            destinationStation,
            request.getDepartureTime(),
            id
        )) {

        throw new DuplicateResourceException("Schedule already exists.");

        }

        schedule.setTrain(train);
        schedule.setOriginStation(originStation);
        schedule.setDestinationStation(destinationStation);
        schedule.setDepartureTime(request.getDepartureTime());
        schedule.setArrivalTime(request.getArrivalTime());
        schedule.setPrice(request.getPrice());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return mapToResponse(updatedSchedule);
    }

    @Override
    public ScheduleResponse updateScheduleStatus(
            Long id,
            UpdateStatusRequest request
    ) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Schedule not found with ID: " + id
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

        if (schedule.getStatus().equals(status)) {
            throw new BusinessValidationException(
                    "Schedule is already " + status
            );
        }

        schedule.setStatus(status);

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return mapToResponse(updatedSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Schedule not found with ID: " + id
                ));

        scheduleRepository.delete(schedule);
    }

    private ScheduleResponse mapToResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .train(ScheduleResponse.TrainInfo.builder()
                        .id(schedule.getTrain().getId())
                        .trainCode(schedule.getTrain().getTrainCode())
                        .trainName(schedule.getTrain().getTrainName())
                        .build())
                .originStation(ScheduleResponse.StationInfo.builder()
                        .id(schedule.getOriginStation().getId())
                        .stationCode(schedule.getOriginStation().getStationCode())
                        .stationName(schedule.getOriginStation().getStationName())
                        .city(schedule.getOriginStation().getCity())
                        .build())
                .destinationStation(ScheduleResponse.StationInfo.builder()
                        .id(schedule.getDestinationStation().getId())
                        .stationCode(schedule.getDestinationStation().getStationCode())
                        .stationName(schedule.getDestinationStation().getStationName())
                        .city(schedule.getDestinationStation().getCity())
                        .build())
                .departureTime(schedule.getDepartureTime())
                .arrivalTime(schedule.getArrivalTime())
                .price(schedule.getPrice())
                .status(schedule.getStatus())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}
