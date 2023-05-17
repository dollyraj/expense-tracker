package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.exceptionHandling.NoResourceFoundException;
import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.model.UserStatus;
import org.gfg.expenseTracker.repository.UserRepository;
import org.gfg.expenseTracker.request.CreateUserRequest;
import org.gfg.expenseTracker.request.UpdateUserRequest;
import org.gfg.expenseTracker.response.CreateUserResponse;
import org.gfg.expenseTracker.response.GenericResponse;
import org.gfg.expenseTracker.response.UpdateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public CreateUserResponse addUser(CreateUserRequest createUserRequest) {
        User userFromDb = userRepository.findByEmail(createUserRequest.getEmail());
        if(userFromDb == null){
            User user = createUserRequest.toUser();
            user.setUserStatus(UserStatus.ACTIVE);
            userFromDb = userRepository.save(user);
        }
        CreateUserResponse createUserResponse = CreateUserResponse.builder().userId(userFromDb.getId()).build();
        return createUserResponse;

    }


    public List<User> getAllUser() {
        List<User> list=userRepository.findAll();

        if(list.isEmpty()){
            throw new NoResourceFoundException("No user is not found");
        }

        return list;

    }

    public UpdateUserResponse updateUserProfile(UpdateUserRequest updateUserRequest,Integer userId) {

        if(userRepository.findById(userId).isEmpty()){
            throw new NoResourceFoundException("Given user does not exist");
        }

       User userFromDb=userRepository.findById(userId).get();
        userFromDb.setName(updateUserRequest.getName());
        userFromDb.setContact(updateUserRequest.getContact());
        User updatedUser=userRepository.save(userFromDb);

        UpdateUserResponse updateUserResponse=UpdateUserResponse.builder().
                userId(updatedUser.getId()).
                name(updatedUser.getName()).Contact(updatedUser.getContact())
                .build();

        return updateUserResponse;
    }
}
