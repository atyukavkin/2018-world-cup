package com.atyukavkin.football.service;

import com.atyukavkin.football.dao.TeamDao;
import com.atyukavkin.football.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrey Tyukavkin.
 */
@Service
public class TeamsService {

    @Autowired
    private TeamDao teamDao;

    public Team findTeamByTitle(String title) {
        return teamDao.findTeamByTitle(title);
    }


    public Team findTeamById(Integer id) {
        return teamDao.findTeamById(id);
    }

    public List<Team> findAllTeams() {
        return teamDao.findAllTeams();
    }
}
