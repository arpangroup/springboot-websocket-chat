package com.arpangroup.springbootwebsocket.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="HEARTBEAT_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartBeatDetails {
    @Id
    private Long userId;
    private Timestamp heartbeatTime;
    private String serverAddress;
}
