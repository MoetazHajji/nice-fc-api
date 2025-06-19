package matawan.nicefcapi.dto;

import java.io.Serializable;
import java.util.List;

public class TeamDto implements Serializable {
    private Long id;
    private String name;
    private String acronym;
    private Float budget;
    private List<PlayerDto> players;
}
