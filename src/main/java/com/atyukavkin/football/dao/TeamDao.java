package com.atyukavkin.football.dao;

import com.atyukavkin.football.model.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Andrey Tyukavkin.
 */
@Repository
public interface TeamDao extends CrudRepository<Team, Long> {

    @Query("select t from Team t where t.title = ?1")
    Team findTeamByTitle(String title);

    @Query("select t from Team t where t.id = ?1")
    Team findTeamById(Integer id);

    @Query("select t from Team t")
    List<Team> findAllTeams();
}
