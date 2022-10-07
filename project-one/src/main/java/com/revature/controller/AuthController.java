package com.revature.controller;

import com.revature.exception.AlreadyRegisteredException;
import com.revature.exception.InvalidLoginException;
import com.revature.exception.PasswordNotIncluded;
import com.revature.model.User;
import com.revature.service.AuthService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;

public class AuthController {

    private AuthService authService = new AuthService();

    public void mapEndpoints(Javalin app){

        app.post("/login", (ctx) -> {

            User credentials = ctx.bodyAsClass(User.class);

            try {

                User user = authService.login(credentials.getUsername(), credentials.getPassword());

                HttpSession session = ctx.req.getSession();

                session.setAttribute("user", user);
                ctx.result("Welcome " + user.getUsername());

            } catch (InvalidLoginException e){
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });

        app.post("/register", (ctx) -> {

            User addUser = ctx.bodyAsClass(User.class);

            try{

                User addedUser = authService.register(addUser);

                ctx.json(addedUser);
                ctx.status(201);
            } catch (AlreadyRegisteredException e){
                ctx.result(e.getMessage());
                ctx.status(400);
            } catch (PasswordNotIncluded e){
                ctx.result(e.getMessage());
                ctx.status(400);
            }
        });



        app.post("/logout", (ctx) -> {

            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");

            ctx.req.getSession().invalidate();
            ctx.result("You have logged out. GoodBye " + user.getUsername());
        });
    }

}
