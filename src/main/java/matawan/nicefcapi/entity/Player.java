package matawan.nicefcapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import matawan.nicefcapi.common.entities.IModel;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player extends IModel {
    String name;
    String position;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
