package matawan.nicefcapi.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerDto implements Serializable {
    private Long id;
    private String name;
    private String position;
}
