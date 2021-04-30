package com.arpangroup.springbootwebsocket.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {
    private String name;
    private String aliasName;
    private int phone;

    public UserCreationRequest(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }
}
