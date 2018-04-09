package com.atyukavkin.football;

import com.atyukavkin.football.model.Team;
import com.atyukavkin.football.service.TeamsService;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Andrey Tyukavkin.
 */
@Timed
@RestController
public class TeamController {

    @Autowired
    private TeamsService teamsService;

    @Autowired
    MetricRegistry metrics;

    private com.codahale.metrics.Meter counter;

    @PostConstruct
    public void init() {
        counter = metrics.meter("requests");
    }

    @RequestMapping("/teams")
    public String getTeams() throws JsonProcessingException {
        counter.mark();
        List<Team> teams = teamsService.findAllTeams();
        return new ObjectMapper().writeValueAsString(teams);
    }

    @RequestMapping("/teams/search")
    public String searchTeam(@RequestParam String teamTitle) throws JsonProcessingException {
        counter.mark();
        if (StringUtils.isNotBlank(teamTitle)) {
            Team team = teamsService.findTeamByTitle(teamTitle);
            if (team != null) {
                return new ObjectMapper().writeValueAsString(team);
            } else {
                return "";
            }
        }
        return "";

    }

    @RequestMapping("/teams/{id}")
    public String getTeamById(@PathVariable Integer id) throws JsonProcessingException {
        counter.mark();
        Team team = teamsService.findTeamById(id);
        return team != null ? new ObjectMapper().writeValueAsString(team) : "";
    }

    @RequestMapping("/version")
    public String version() {
        return "1.0";
    }
}
