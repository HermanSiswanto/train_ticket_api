package train_ticket_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "role_name",
            nullable = false,
            unique = true
    )
    private String roleName;

    @Column
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}