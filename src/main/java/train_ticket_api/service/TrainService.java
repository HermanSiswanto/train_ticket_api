package train_ticket_api.service;

import train_ticket_api.entity.Train;
import train_ticket_api.dto.CreateTrainRequest;
import train_ticket_api.dto.UpdateTrainRequest;
import train_ticket_api.dto.UpdateStatusRequest;

import java.util.List;

public interface TrainService {

    List<Train> getAllTrains();

    Train getTrainById(Long id);

    Train createTrain(
            CreateTrainRequest request
    );

    Train updateTrain(
            Long id,
            UpdateTrainRequest request
    );

    Train updateTrainStatus(
            Long id,
            UpdateStatusRequest request
    );

    void deleteTrain(Long id);
}