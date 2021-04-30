package com.arpangroup.springbootwebsocket.controller;

import com.arpangroup.springbootwebsocket.model.entity.UserDetails;
import com.arpangroup.springbootwebsocket.model.request.UserCreationRequest;
import com.arpangroup.springbootwebsocket.model.response.ErrorResponse;
import com.arpangroup.springbootwebsocket.service.UserService;
import com.arpangroup.springbootwebsocket.storage.UserStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/user")
@ApiOperation(value = "/api/v1", tags = "Ëmployee Profile Controller")
public class UserController {
    Logger LOG = LoggerFactory.getLogger(getClass().getSimpleName());

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserCreationRequest userCreationRequest){
        return new ResponseEntity<>(null);
    }

    @GetMapping("/searchFriend")
    public ResponseEntity<Void> searchFriend(@RequestBody UserCreationRequest userCreationRequest){
        return new ResponseEntity<>(null);
    }

    @GetMapping("/friendRequest/send")
    public ResponseEntity<Void> sendFriendRequest(@RequestBody UserCreationRequest userCreationRequest){
        return new ResponseEntity<>(null);
    }

    @GetMapping("/friendRequest/accept")
    public ResponseEntity<Void> acceptFriendRequest(@RequestBody UserCreationRequest userCreationRequest){
        return new ResponseEntity<>(null);
    }




    @ApiOperation(value = "fetch all users", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UserDetails.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZED", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "NOT FOUND"),
    })
    @GetMapping("/users")
    public List<UserDetails> users(){
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Fetch user by Id", response = UserDetails.class)
    @GetMapping("/user/{userId}")
    public UserDetails getUserDetails(@PathVariable Long userId){
        return userService.findUserById(userId);
    }



}
