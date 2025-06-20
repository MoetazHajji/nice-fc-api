package matawan.nicefcapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.entity.Player;
import matawan.nicefcapi.entity.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import matawan.nicefcapi.interfaces.ITeamService;
import matawan.nicefcapi.mappers.TeamMapper;
import matawan.nicefcapi.repository.ITeamRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService implements ITeamService {

    private final ITeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    @Override
    public TeamDto save(TeamDto dto) {
        log.debug("Mapping DTO to entity: {}", dto);
        Team team = teamMapper.toEntity(dto);
        System.out.println("Mapping DTO to entity: " + team);
        List<Player> players = team.getPlayers();
        if (players != null && !players.isEmpty()) {
            players.forEach(p -> {
                log.info("  ↪ mapped player: id={}, name='{}', pos='{}'",
                        p.getId(), p.getName(), p.getPosition());
                p.setTeam(team);
                log.trace("Assigned team to player {}", p.getName());
            });
        } else {
            // ensure the entity has an empty list rather than null
            team.setPlayers(Collections.emptyList());
        }
        Team saved = teamRepository.save(team);
        System.out.println("saved team: " + saved);
        log.info("Persisted Team[id={}], with {} players",
                saved.getId(),
                saved.getPlayers() == null ? 0 : saved.getPlayers().size());
        return teamMapper.toDto(saved);
    }

    @Override
    public List<TeamDto> getAll(Pageable pageable, String sortBy) {
        log.debug("Fetching page {} of size {} sorted by {}",
                pageable.getPageNumber(), pageable.getPageSize(), sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy));

        List<TeamDto> dtos = teamRepository
                .findAll(sortedPageable)
                .stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());

        log.debug("Fetched {} teams", dtos.size());
        return dtos;
    }
}

