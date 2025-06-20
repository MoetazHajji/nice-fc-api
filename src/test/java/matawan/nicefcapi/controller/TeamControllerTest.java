package matawan.nicefcapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import matawan.nicefcapi.dto.TeamDto;
import matawan.nicefcapi.interfaces.ITeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @MockitoBean ITeamService service;

    @Test
    void postTeams_shouldReturnCreated() throws Exception {
        TeamDto input = new TeamDto(null, "Nice", "OGCN", 90_000_000f, List.of());
        TeamDto saved = new TeamDto(1L, "Nice", "OGCN", 90_000_000f, List.of());

        when(service.save(input)).thenReturn(saved);

        mvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nice"));

        verify(service).save(input);
    }

    @Test
    void getTeams_shouldReturnList() throws Exception {
        TeamDto t = new TeamDto(1L, "Nice", "OGCN", 90_000_000f, List.of());
        Pageable pageReq = PageRequest.of(0, 10);
        when(service.getAll(pageReq, "name")).thenReturn(List.of(t));

        mvc.perform(get("/api/teams")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Nice"));

        verify(service).getAll(pageReq, "name");
    }

    @Test
    void postTeams_withEmptyPlayersList_shouldReturnOk() throws Exception {
        TeamDto emptyInput = new TeamDto(null, "Nice", "OGCN", 80_000_000f, List.of());
        TeamDto emptySaved = new TeamDto(42L, "Nice", "OGCN", 80_000_000f, List.of());

        when(service.save(emptyInput)).thenReturn(emptySaved);

        mvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(emptyInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(42));

        verify(service).save(emptyInput);
    }

    @Test
    void postTeams_withoutPlayersField_shouldReturnOk() throws Exception {
        String withoutPlayersJson = """
          {
            "name":"Nice",
            "acronym":"OGCN",
            "budget":80000000
          }
          """;
        when(service.save(any())).thenReturn(new TeamDto(43L, "Nice", "OGCN", 80_000_000f, List.of()));

        mvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withoutPlayersJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(43));
    }
}
