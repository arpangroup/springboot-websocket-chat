package com.arpangroup.springbootwebsocket.repository;

import com.arpangroup.springbootwebsocket.model.entity.UserConnectionGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnectionGraph, Long> {

    @Query("SELECT g1 FROM UserConnectionGraph g1 where g1.userAId = :userAId or g1.userBId = :userAId")
    List<UserConnectionGraph> findAllConnections(@Param("userAId") Long userAId);

}
