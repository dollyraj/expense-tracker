package org.gfg.expenseTracker.controller;

import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.request.CreateUserRequest;
import org.gfg.expenseTracker.request.UpdateUserRequest;
import org.gfg.expenseTracker.response.CreateUserResponse;
import org.gfg.expenseTracker.response.GenericResponse;
import org.gfg.expenseTracker.response.UpdateUserResponse;
import org.gfg.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/expenseTracker")
public class UserController {

    @Autowired
    private UserService userService;

    // create user
    @PostMapping("/addUser")
    public GenericResponse<CreateUserResponse> addUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        CreateUserResponse createUserResponse = userService.addUser(createUserRequest);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(createUserResponse)
                .build();
        return genericResponse;

    }



    //get all users
    @GetMapping("/getAllUsers")
    public GenericResponse<User> getAllUsers(){
        List<User> user= userService.getAllUser();
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been fetched").
                statusCode(0).data(user)
                .build();
        return genericResponse;

    }

    //update user profile
    @PutMapping("/updateUserProfile")
    public GenericResponse<UpdateUserResponse> updateUserProfile(@RequestBody @Valid UpdateUserRequest updateUserRequest,@RequestParam Integer userId){
        UpdateUserResponse updateUserResponse= userService.updateUserProfile(updateUserRequest,userId);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been updated").
                statusCode(0).data(updateUserResponse)
                .build();
        return genericResponse;
    }
}
