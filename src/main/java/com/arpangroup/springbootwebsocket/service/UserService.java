package com.arpangroup.springbootwebsocket.service;

import com.arpangroup.springbootwebsocket.model.entity.UserDetails;
import com.arpangroup.springbootwebsocket.model.request.UserCreationRequest;
import com.arpangroup.springbootwebsocket.model.response.UserDetailsView;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDetailsView register(UserCreationRequest request) throws Exception;


    List<UserDetails> getAllUsers();
    UserDetails findUserById(Long userId);
}
