package com.arpangroup.springbootwebsocket.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_CONNECTION_GRAPH")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConnectionGraph {
    @Id
    private Long conversationId;
    private Long userAId;
    private Long userBId;
    private String encryptionKey;
}
