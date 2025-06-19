package matawan.nicefcapi.controller;

import lombok.RequiredArgsConstructor;
import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.entity.Team;
import matawan.nicefcapi.interfaces.ITeamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final ITeamService teamService;

    @PostMapping
    public TeamDto create(@RequestBody TeamDto request) {
        return teamService.save(request);
    }
}
