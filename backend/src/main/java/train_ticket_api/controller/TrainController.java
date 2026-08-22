package train_ticket_api.controller;

import train_ticket_api.entity.Train;
import train_ticket_api.service.TrainService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import jakarta.validation.Valid;
import train_ticket_api.dto.CreateTrainRequest;
import train_ticket_api.dto.UpdateTrainRequest;
import train_ticket_api.dto.UpdateStatusRequest;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @GetMapping
    public List<Train> getAllTrains() {
        return trainService.getAllTrains();
    }

    @GetMapping("/{id}")
    public Train getTrainById(
        @PathVariable Long id
    ) {

        return trainService.getTrainById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Train createTrain(
        @Valid
        @RequestBody CreateTrainRequest request
    ) {

        return trainService.createTrain(
            request
        );
    }

    @PutMapping("/{id}")
        public Train updateTrain(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateTrainRequest request
    ) {

        return trainService.updateTrain(
            id,
            request
        );
    }

    @PatchMapping("/{id}/status")
    public Train updateTrainStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request
    ) {
        return trainService.updateTrainStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteTrain(@PathVariable Long id) {
            trainService.deleteTrain(id);

        }
}