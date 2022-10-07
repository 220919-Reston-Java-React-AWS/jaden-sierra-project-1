package com.revature.service;

import com.revature.exception.AlreadyRegisteredException;
import com.revature.exception.InvalidLoginException;
import com.revature.exception.PasswordNotIncluded;
import com.revature.repository.UserRepository;
import com.revature.model.User;

import java.sql.SQLException;

public class AuthService {

    private UserRepository userRepo = new UserRepository();

    public User login(String username, String password) throws SQLException, InvalidLoginException {

        User user = userRepo.getUserByUsernameAndPassword(username,password);

        if(user == null){

            throw new InvalidLoginException("Invalid username or password");
        }
        return user;
    }


    public User register(User user) throws AlreadyRegisteredException, SQLException, PasswordNotIncluded {

        if (userRepo.getUserByUsername(user.getUsername()) != null) {

            throw new AlreadyRegisteredException("User with username " + user.getUsername() + " already exists! Choose a different username");
        }

        if (user.getPassword() == null){

            throw new PasswordNotIncluded("You must enter a password in order to register!");
        }

        User addedUser = userRepo.addUser(user);

        return addedUser;
    }

}
