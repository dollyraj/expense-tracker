package org.gfg.expenseTracker.repository;

import org.gfg.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u from user u where user.email =:email", nativeQuery = true) // query which runs on sql
    public User findByEmailAddress(String email);
    @Query(value = "select u from User u where u.email =:email") // runs on hibernate uses class name model names
    public User findByEmailAddressJPQL(String email);

    // we dont even have to write the queries -> standard ways by which we can simply write methods and we dont need to write query

    public User findByEmail(String email);

    // search user data -> where name starts with A

    public User findByNameLike(String pattern); //-> jpa will write query for us.

    public  User findByCreatedAtGreaterThanAndNameLike(Date CreateAt, String name);

}
