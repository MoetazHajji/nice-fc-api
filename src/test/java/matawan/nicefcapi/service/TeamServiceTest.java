package matawan.nicefcapi.service;

import matawan.nicefcapi.dto.PlayerDto;
import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.entity.Player;
import matawan.nicefcapi.entity.Team;
import matawan.nicefcapi.mappers.TeamMapper;
import matawan.nicefcapi.repository.ITeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TeamServiceTest {

    @Mock ITeamRepository repo;
    @Mock TeamMapper mapper;
    @InjectMocks TeamService service;

    private TeamDto dto;
    private Team    entity;

    @BeforeEach
    void setUp() {
        dto = new TeamDto(
                null,
                "Nice",
                "OGCN",
                90_000_000f,
                List.of(new PlayerDto(null, "Jean", "Forward"))
        );
        entity = new Team(
                null,
                "Nice",
                "OGCN",
                90_000_000f,
                List.of(new Player(null, "Jean", "Forward", null))
        );
    }

    @Test
    void save_shouldMapDtoAndPersistAndReturnDtoWithId() {
        // arrange
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).then(inv -> {
            Team e = inv.getArgument(0);
            e.setId(1L);
            e.getPlayers().forEach(p -> {
                p.setTeam(e);
                p.setId(10L);
            });
            return e;
        });
        TeamDto savedDto = new TeamDto(
                1L, "Nice", "OGCN", 90_000_000f,
                List.of(new PlayerDto(10L, "Jean", "Forward"))
        );
        when(mapper.toDto(any(Team.class))).thenReturn(savedDto);

        // act
        TeamDto result = service.save(dto);

        // assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPlayers()).hasSize(1)
                .first().extracting(PlayerDto::getId).isEqualTo(10L);

        InOrder inOrder = inOrder(mapper, repo, mapper);
        inOrder.verify(mapper).toEntity(dto);
        inOrder.verify(repo).save(entity);
        inOrder.verify(mapper).toDto(any(Team.class));
    }

    @Test
    void getAll_shouldReturnMappedList() {
        // arrange
        Pageable pageReq = PageRequest.of(0, 2, Sort.by("name"));
        List<Team> teamList = List.of(entity);
        Page<Team> page = new PageImpl<>(teamList, pageReq, teamList.size());
        when(repo.findAll(pageReq)).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(dto);

        // act
        List<TeamDto> dtos = service.getAll(pageReq, "name");

        // assert
        assertThat(dtos).containsExactly(dto);
        verify(repo).findAll(pageReq);
        verify(mapper).toDto(entity);
    }

    @Test
    void save_withEmptyPlayers_shouldPersistTeamWithoutErrors() {
        // arrange: DTO with empty players
        TeamDto emptyDto = new TeamDto(null, "Lyon", "OL", 60_000_000f, List.of());

        Team emptyEntity = new Team();
        emptyEntity.setName("Lyon");
        emptyEntity.setAcronym("OL");
        emptyEntity.setBudget(60_000_000f);
        emptyEntity.setPlayers(List.of());

        when(mapper.toEntity(emptyDto)).thenReturn(emptyEntity);
        when(repo.save(emptyEntity)).thenAnswer(inv -> {
            Team t = inv.getArgument(0);
            t.setId(5L);
            return t;
        });
        TeamDto savedEmptyDto = new TeamDto(5L, "Lyon", "OL", 60_000_000f, List.of());
        when(mapper.toDto(any(Team.class))).thenReturn(savedEmptyDto);

        // act
        TeamDto result = service.save(emptyDto);

        // assert
        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getPlayers()).isEmpty();

        InOrder inOrder = inOrder(mapper, repo, mapper);
        inOrder.verify(mapper).toEntity(emptyDto);
        inOrder.verify(repo).save(emptyEntity);
        inOrder.verify(mapper).toDto(any(Team.class));
    }
}
