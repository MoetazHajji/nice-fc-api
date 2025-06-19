package matawan.nicefcapi.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import matawan.nicefcapi.entity.Player;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto implements Serializable {
    private Long id;
    private String name;
    private String acronym;
    private Float budget;

    private List<PlayerDto> players;
}
