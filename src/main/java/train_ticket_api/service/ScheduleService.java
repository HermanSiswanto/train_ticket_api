package train_ticket_api.service;

import train_ticket_api.dto.CreateScheduleRequest;
import train_ticket_api.dto.UpdateScheduleRequest;
import train_ticket_api.dto.ScheduleResponse;
import train_ticket_api.dto.UpdateStatusRequest;

import java.util.List;

public interface ScheduleService {

    List<ScheduleResponse> getAllSchedules();

    ScheduleResponse getScheduleById(Long id);

    ScheduleResponse createSchedule(CreateScheduleRequest request);

    ScheduleResponse updateSchedule(Long id, UpdateScheduleRequest request);

    ScheduleResponse updateScheduleStatus(
            Long id,
            UpdateStatusRequest request
    );

    void deleteSchedule(Long id);
}
