package com.atyukavkin.football.dao;

import com.atyukavkin.football.model.monitoring.MonitorData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Andrey Tyukavkin.
 */
@Repository
public interface MonitorDao extends CrudRepository<MonitorData, Long> {

    @Modifying
    MonitorData save(MonitorData data);

    @Query
    List<MonitorData> findAll();

}
