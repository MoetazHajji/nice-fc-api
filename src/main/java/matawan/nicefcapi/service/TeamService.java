package matawan.nicefcapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.entity.Team;
import matawan.nicefcapi.entity.Player;
import matawan.nicefcapi.interfaces.ITeamService;
import matawan.nicefcapi.mappers.TeamMapper;
import matawan.nicefcapi.repository.ITeamRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService implements ITeamService {

    private final ITeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Override
    public TeamDto save(TeamDto dto) {
        Team entity = teamMapper.toEntity(dto);
        if (entity.getPlayers() != null) {
            entity.getPlayers().forEach(p -> p.setTeam(entity));
        }
        return teamMapper.toDto(teamRepository.save(entity));
    }

    @Override
    public List<TeamDto> getAll(Pageable pageable, String sortBy) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy));
        return teamRepository.findAll(sortedPageable)
                .stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }


}

