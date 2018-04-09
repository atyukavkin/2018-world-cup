package com.atyukavkin.football.service;

import com.atyukavkin.football.model.Team;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FootballApplicationTests {

    private static final String BRAZIL = "Brazil";

    @Test
    public void searchTeamByTitleTest() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(String.format("http://localhost:%s/teams/search?teamTitle=" + BRAZIL, 2018),
                HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        Team team = new ObjectMapper().readValue(response.getBody(), Team.class);
        Assert.assertEquals(BRAZIL, team.getTitle());
    }

    @Test
    public void getBrazilTeamByIdTest() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(String.format("http://localhost:%s/teams/211", 2018),
                HttpMethod.GET, entity, String.class);
        Team team = new ObjectMapper().readValue(response.getBody(), Team.class);
        Assert.assertEquals(BRAZIL, team.getTitle());
    }

    @Test
    public void getAllTeams() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(String.format("http://localhost:%s/teams", 2018),
                HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<Team> teams = objectMapper.readValue(response.getBody(), typeFactory.constructCollectionType(List.class, Team.class));
        Assert.assertTrue(teams.size() > 0);
    }

    @Test
    public void checkVersionTest() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(String.format("http://localhost:%s/version", 2018),
                HttpMethod.GET, entity, String.class);
        Assert.assertEquals("1.0", response.getBody());
    }
}
