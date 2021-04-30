package com.arpangroup.springbootwebsocket.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONVERSATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    @Id
    private Long id;
    private Long conversationId;
    private Long time;
    private String text;
    private Long senderId;
    private String url;

}
