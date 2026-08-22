package train_ticket_api.repository;

import train_ticket_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(
            String roleName
    );
}