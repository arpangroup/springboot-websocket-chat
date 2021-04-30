package com.arpangroup.springbootwebsocket.repository;

import com.arpangroup.springbootwebsocket.model.entity.HeartBeatDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartBeatRepository extends JpaRepository<HeartBeatDetails, Long> {
}
