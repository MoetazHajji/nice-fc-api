package matawan.nicefcapi.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import matawan.nicefcapi.entity.Player;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto implements Serializable {
    private Long id;

    @NotBlank(message = "Team name is required")
    private String name;
    @NotBlank(message = "Acronym is required")
    private String acronym;
    @NotNull(message = "Budget must be provided")
    @PositiveOrZero(message = "Budget cannot be negative")
    private Float budget;

    @NotNull(message = "Players list must be provided (can be empty)")
    @Size(min = 1, message = "At least one player must be specified")
    @Valid
    private List<PlayerDto> players;
}
