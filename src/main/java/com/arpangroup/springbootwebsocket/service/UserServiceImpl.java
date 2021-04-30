package com.arpangroup.springbootwebsocket.service;

import com.arpangroup.springbootwebsocket.exception.UserNotFoundException;
import com.arpangroup.springbootwebsocket.model.entity.UserConnectionGraph;
import com.arpangroup.springbootwebsocket.model.entity.UserDetails;
import com.arpangroup.springbootwebsocket.model.request.UserCreationRequest;
import com.arpangroup.springbootwebsocket.model.response.UserDetailsView;
import com.arpangroup.springbootwebsocket.repository.UserConnectionRepository;
import com.arpangroup.springbootwebsocket.repository.UserDetailsRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserConnectionRepository userConnectionRepository;

    /*
    @Override
    public UserDetailsView createNewConnection(@NotNull Long userAId, @NotNull Long userBId) throws UserNotFoundException {
        // First check userB is valid or not
        UserDetails userBDetails = userDetailsRepository.findById(userBId).orElse(null);
        if(userBDetails == null) throw new UserNotFoundException("friend_id" + userBId + "not found");

        UserConnectionGraph graph = new UserConnectionGraph();
        graph.setUserAId(userAId);
        graph.setUserBId(userBId);
        graph.setEncryptionKey(String.valueOf(new Date().getTime()));

        List<UserConnectionGraph> connections = userConnectionRepository.findAllConnections(userAId);



        // First check whether there is any existing connection for userA

    }
     */


    @Override
    public UserDetailsView register(UserCreationRequest request) throws Exception {
        validateUserCreationRequest();
        UserDetails userDetails = userDetailsRepository.save(mapFrom(request));
        return null;
    }

    @Override
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    @Override
    public UserDetails findUserById(Long userId) {
        return userDetailsRepository.findById(userId).get();
    }


    private UserDetails mapFrom(UserCreationRequest userCreationRequest){
        UserDetails userDetails = new UserDetails();
        userDetails.setName(userCreationRequest.getName());
        userDetails.setProfileImage(null);
        return userDetails;
    }

    private void validateUserCreationRequest() throws Exception{

    }
}
