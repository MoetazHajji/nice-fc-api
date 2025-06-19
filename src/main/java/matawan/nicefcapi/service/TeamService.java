package matawan.nicefcapi.service;

import lombok.RequiredArgsConstructor;
import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.entity.Team;
import matawan.nicefcapi.entity.Player;
import matawan.nicefcapi.interfaces.ITeamService;
import matawan.nicefcapi.mappers.TeamMapper;
import matawan.nicefcapi.repository.ITeamRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService implements ITeamService {

    private final ITeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Override
    public TeamDto save(TeamDto teamDto) {
        Team teamRequest = teamMapper.toEntity(teamDto);

        if (teamRequest.getPlayers() != null) {
            for (Player player : teamRequest.getPlayers()) {
                player.setTeam(teamRequest);
            }
        }

        Team teamSaved = teamRepository.save(teamRequest);
        return teamMapper.toDto(teamSaved);
    }
}

