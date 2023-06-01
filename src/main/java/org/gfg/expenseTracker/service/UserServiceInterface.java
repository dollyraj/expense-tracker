package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.request.UserSignupRequest;
import org.gfg.expenseTracker.request.UpdateUserRequest;
import org.gfg.expenseTracker.response.SignUpResponse;
import org.gfg.expenseTracker.response.UpdateUserResponse;

import java.util.List;

public interface UserServiceInterface {
    SignUpResponse addUser(UserSignupRequest createUserRequest);
    List<User> getAllUser();
    UpdateUserResponse updateUserProfile(UpdateUserRequest updateUserRequest);
}
