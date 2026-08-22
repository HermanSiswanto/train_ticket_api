package train_ticket_api.service;

import train_ticket_api.entity.Train;
import train_ticket_api.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import train_ticket_api.exception.ResourceNotFoundException;
import train_ticket_api.exception.DuplicateResourceException;
import train_ticket_api.exception.BusinessValidationException;

import train_ticket_api.dto.CreateTrainRequest;
import train_ticket_api.dto.UpdateTrainRequest;
import train_ticket_api.dto.UpdateStatusRequest;

import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    @Override
    public List<Train> getAllTrains() {
        return trainRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")

);
    }

    @Override
    public Train getTrainById(
            Long id
    ) {

        return trainRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Train not found with ID: " + id
                        ));
    }

    @Override
    public Train createTrain(
            CreateTrainRequest request
    ) {
        String trainCode = request.getTrainCode()
                .toUpperCase();

        if (trainRepository.existsByTrainCode(trainCode)) {

            throw new DuplicateResourceException(
                    "Train code already exists"
            );
        }

        Train train = new Train();

        train.setTrainCode(trainCode);

        train.setTrainName(
                request.getTrainName().trim()
        );
        train.setStatus("ACTIVE");

        return trainRepository.save(
                train
        );
    }
    
    @Override
    public Train updateTrain(
            Long id,
            UpdateTrainRequest request
    ) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Train not found with ID: " + id
                        ));
        String trainCode = request.getTrainCode()
                .toUpperCase();

        trainRepository.findByTrainCode(trainCode)
                .ifPresent(existingTrain -> {
                    if (!existingTrain.getId().equals(id)) {
                        throw new DuplicateResourceException(
                                "Train code already exists"
                        );
                    }
                });

        train.setTrainCode(trainCode);

        train.setTrainName(
                request.getTrainName().trim()
        );

        return trainRepository.save(
                train
        );
    }

    @Override
    public Train updateTrainStatus(
            Long id,
            UpdateStatusRequest request
    ) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Train not found with ID: " + id
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

        if (train.getStatus().equals(status)) {
            throw new BusinessValidationException(
                    "Train is already " + status
            );
        }

        train.setStatus(status);

        return trainRepository.save(train);
    }

    @Override
    public void deleteTrain(Long id) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Train not found with ID: " + id
                        ));

        trainRepository.delete(train);
    }
}