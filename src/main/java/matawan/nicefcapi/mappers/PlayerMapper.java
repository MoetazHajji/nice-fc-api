package matawan.nicefcapi.mappers;

import matawan.nicefcapi.dto.PlayerDto;
import matawan.nicefcapi.entity.Player;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface PlayerMapper {
    PlayerDto toDto(Player player);
    Player toEntity(PlayerDto playerDto);
}
