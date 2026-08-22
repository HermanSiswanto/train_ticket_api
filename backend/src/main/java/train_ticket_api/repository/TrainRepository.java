package train_ticket_api.repository;

import train_ticket_api.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrainRepository
        extends JpaRepository<Train, Long> {

    boolean existsByTrainCode(
            String trainCode
    );

    Optional<Train> findByTrainCode(
            String trainCode
    );
}