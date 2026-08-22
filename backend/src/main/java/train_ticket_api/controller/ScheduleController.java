package train_ticket_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import train_ticket_api.dto.CreateScheduleRequest;
import train_ticket_api.dto.ScheduleResponse;
import train_ticket_api.dto.UpdateScheduleRequest;
import train_ticket_api.dto.UpdateStatusRequest;
import train_ticket_api.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleResponse getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponse createSchedule(
            @Valid @RequestBody CreateScheduleRequest request
    ) {
        return scheduleService.createSchedule(request);
    }

    @PutMapping("/{id}")
    public ScheduleResponse updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        return scheduleService.updateSchedule(id, request);
    }

    @PatchMapping("/{id}/status")
    public ScheduleResponse updateScheduleStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request
    ) {
        return scheduleService.updateScheduleStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}
