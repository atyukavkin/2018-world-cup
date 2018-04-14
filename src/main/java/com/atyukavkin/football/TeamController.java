package com.atyukavkin.football;

import com.atyukavkin.football.model.Team;
import com.atyukavkin.football.service.TeamsService;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Andrey Tyukavkin.
 */

@Timed
@RestController
@RequestMapping(value = "/football")
@Api(value = "Teams", description = "Football teams operations")
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

    @ApiOperation(value = "List of available teams", response = Team.class, responseContainer = "List")
    @RequestMapping(value = "/teams", method = RequestMethod.GET, produces = "application/json")
    public String getTeams() throws JsonProcessingException {
        counter.mark();
        List<Team> teams = teamsService.findAllTeams();
        return new ObjectMapper().writeValueAsString(teams);
    }

    @ApiOperation(value = "Search team by title", response = Team.class)
    @RequestMapping(value = "/teams/search", method = RequestMethod.GET, produces = "application/json")
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

    @ApiOperation(value = "Search team by ID", response = Team.class)
    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getTeamById(@PathVariable Integer id) throws JsonProcessingException {
        counter.mark();
        Team team = teamsService.findTeamById(id);
        return team != null ? new ObjectMapper().writeValueAsString(team) : "";
    }

    @ApiOperation(value = "Version", response = String.class)
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String version() {
        return "1.0";
    }
}
