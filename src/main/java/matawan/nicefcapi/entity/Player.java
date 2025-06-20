package matawan.nicefcapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import matawan.nicefcapi.common.entities.IModel;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player extends IModel {
    @NotBlank
    String name;
    @NotBlank
    String position;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
