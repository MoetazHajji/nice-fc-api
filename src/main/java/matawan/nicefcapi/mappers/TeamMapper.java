package matawan.nicefcapi.mappers;

import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;



@Mapper(componentModel = "spring",
        uses = PlayerMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {
    TeamDto toDto(Team team);
    Team   toEntity(TeamDto dto);
}

