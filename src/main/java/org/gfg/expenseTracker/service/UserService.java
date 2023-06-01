package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.exceptionHandling.CustomException;
import org.gfg.expenseTracker.model.GenericMessages;
import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.model.UserStatus;
import org.gfg.expenseTracker.model.UserType;
import org.gfg.expenseTracker.repository.UserRepository;
import org.gfg.expenseTracker.request.UserSignupRequest;
import org.gfg.expenseTracker.request.UpdateUserRequest;
import org.gfg.expenseTracker.response.CreateExpenseTypeResponse;
import org.gfg.expenseTracker.response.GenericResponse;
import org.gfg.expenseTracker.response.SignUpResponse;
import org.gfg.expenseTracker.response.UpdateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

import static org.gfg.expenseTracker.model.UserStatus.ACTIVE;
import static org.gfg.expenseTracker.model.UserType.ADMIN;


@Service
public class UserService implements UserServiceInterface, UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    private UserType getUserType(String typeOfUser){
        if(typeOfUser.equalsIgnoreCase(ADMIN.getUserType())){
            return ADMIN;
        }
        return UserType.USER;
    }
    public SignUpResponse userSignUp(UserSignupRequest userSignupRequest, String typeOfUser) {
        if(userRepository.findByEmail(userSignupRequest.getUserEmail()) != null){
            return SignUpResponse.builder().message(GenericMessages.ENTRY_ALREADY_PRESENT.getMessage()).build();
        }
        User userInDb = userSignupRequest.toUser();

        userInDb.setPassword( new BCryptPasswordEncoder().encode(userSignupRequest.getPassword()));
        // userInDb.setPassword(encoder.encode(userSignupRequest.getPassword()));
        UserType userType = getUserType(typeOfUser);
        userInDb.setUserType(userType);
        userInDb.setUserStatus(ACTIVE);
        try{
            userInDb = userRepository.save(userInDb);
        }catch (Exception e){
            e.printStackTrace();
            return SignUpResponse.builder().message(GenericMessages.SIGNUP_FAILURE.getMessage()).build();
        }

        return SignUpResponse.builder().
                message(GenericMessages.SIGNUP_SUCCESS.getMessage()).
                email(userInDb.getEmail()).
                userId(userInDb.getId()).
                build();
    }


    public SignUpResponse addUser(UserSignupRequest userSignupRequest) {
        User userFromDb = userRepository.findByEmail(userSignupRequest.getUserEmail());
        if(userFromDb == null){
            User user = userSignupRequest.toUser();
            user.setUserStatus(UserStatus.INACTIVE);
            userFromDb = userRepository.save(user);
        }
        SignUpResponse signupResponse = SignUpResponse.builder().userId(userFromDb.getId()).build();
        return signupResponse;

    }

    //only admins can see userList
    public List<User> getAllUser() {
//        List<User> list=userRepository.findAll();
//
//        if(list.isEmpty()){
//            throw new CustomException("No user is not found");
//        }
//
//        return list;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        User userFromDb=userRepository.findByEmail(user.getEmail());

        if(!userFromDb.getUserType().equals(ADMIN)){

            throw new CustomException("Permission not allowed!!!");
        }
        return userRepository.findAll();

    }

    public UpdateUserResponse updateUserProfile(UpdateUserRequest updateUserRequest) {

        //get current logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        User userFromDb=userRepository.findByEmail(user.getEmail());

        if(userFromDb==null){
            throw new CustomException("Given user does not exist");
        }

        //userFromDb=userRepository.findById(userId).get();
        userFromDb.setName(updateUserRequest.getName());
        userFromDb.setContact(updateUserRequest.getContact());


        try{
             userFromDb=userRepository.save(userFromDb);
        }catch (Exception e){
            e.printStackTrace();
            return UpdateUserResponse.builder().message(GenericMessages.PROFILE_UPDATE_FAILURE.getMessage()).build();
        }

        UpdateUserResponse updateUserResponse=UpdateUserResponse.builder().
                userId(userFromDb.getId()).
                name(userFromDb.getName()).Contact(userFromDb.getContact()).
                message(GenericMessages.PROFILE_UPDATE_SUCCESS.getMessage())
                .build();

        return updateUserResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }
}
