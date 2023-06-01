package org.gfg.expenseTracker.controller;

import org.gfg.expenseTracker.model.GenericMessages;
import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.request.UserSignupRequest;
import org.gfg.expenseTracker.request.UpdateUserRequest;
import org.gfg.expenseTracker.response.SignUpResponse;
import org.gfg.expenseTracker.response.GenericResponse;
import org.gfg.expenseTracker.response.UpdateUserResponse;
import org.gfg.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/expenseTracker")
public class UserController {

    @Autowired
    private UserService userService;

    // create user
//    @PostMapping("/addUser")
//    public GenericResponse<SignUpResponse> addUser(@Valid @RequestBody UserSignupRequest userSignupRequest){
//        SignUpResponse signupResponse = userService.addUser(userSignupRequest);
//        GenericResponse genericResponse = GenericResponse.builder().
//                code(HttpStatus.OK.value()).
//                message("user details has been saved").
//                statusCode(0).data(signupResponse)
//                .build();
//        return genericResponse;
//
//    }



    @PostMapping("/signup/{type}")
    public GenericResponse<Object> userSignUp(@PathVariable String type,
                                              @Valid @RequestBody UserSignupRequest userSignupRequest){

        SignUpResponse signUpResponse = userService.userSignUp(userSignupRequest, type);

        if(signUpResponse.getMessage().equalsIgnoreCase(GenericMessages.SIGNUP_SUCCESS.getMessage())) {
            return GenericResponse.builder().
                    code(HttpStatus.OK.value()).
                    message(GenericMessages.SIGNUP_SUCCESS.getMessage()).
                    statusCode(0).
                    data(signUpResponse).build();
        }

        else if (signUpResponse.getMessage().equalsIgnoreCase(GenericMessages.ENTRY_ALREADY_PRESENT.getMessage())) {
            return GenericResponse.builder().
                    code(HttpStatus.OK.value()).
                    message(GenericMessages.ENTRY_ALREADY_PRESENT.getMessage()).
                    statusCode(1).
                    data(signUpResponse).build();

        }
        return GenericResponse.builder().
                code(HttpStatus.INTERNAL_SERVER_ERROR.value()).
                message(GenericMessages.SIGNUP_FAILURE.getMessage()).
                statusCode(1).
                data(null).
                build();
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
    @PutMapping("/updateUserProfile")//@RequestParam Integer userId
    public GenericResponse<UpdateUserResponse> updateUserProfile(@RequestBody @Valid UpdateUserRequest updateUserRequest){
        UpdateUserResponse updateUserResponse= userService.updateUserProfile(updateUserRequest);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been updated").
                statusCode(0).data(updateUserResponse)
                .build();
        return genericResponse;
    }
}
