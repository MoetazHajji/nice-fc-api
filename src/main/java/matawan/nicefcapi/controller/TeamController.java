package matawan.nicefcapi.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.interfaces.ITeamService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teams")
@RequiredArgsConstructor
@Validated
public class TeamController {
    private final ITeamService teamService;
    private static final Logger log = LoggerFactory.getLogger(TeamController.class);


    @PostMapping
    public TeamDto create(@RequestBody TeamDto request) {
        return teamService.save(request);
    }

    @GetMapping
    public List<TeamDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return teamService.getAll(pageable, sortBy);
    }
}
