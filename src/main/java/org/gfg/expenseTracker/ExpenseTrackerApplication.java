package org.gfg.expenseTracker;

import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseTrackerApplication {


	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {

		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

	//implements CommandLineRunner
//	@Override
//	public void run(String... args) throws Exception {
//
//		User user =userRepository.findByEmail("user1@gmail.com");
//		System.out.println(user);
//	}

	// 1 way of testing is controller -> service -> repository -> model

	// test one repository method

}
