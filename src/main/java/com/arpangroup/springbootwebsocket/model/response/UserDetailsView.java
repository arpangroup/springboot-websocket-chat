package com.arpangroup.springbootwebsocket.model.response;

import com.arpangroup.springbootwebsocket.model.entity.UserConnectionGraph;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserDetailsView {
    private String name;
    private String aliasName;
    private int phone;
    private String profileImage;
    private String thumbnail;
    private List<UserConnectionGraphView> connectedUsers;
}
