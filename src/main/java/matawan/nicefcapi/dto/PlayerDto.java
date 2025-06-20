package matawan.nicefcapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto implements Serializable {
    private Long id;

    @NotBlank(message = "Player name is required")
    private String name;
    @NotBlank(message = "Position is required")
    private String position;
}
