package com.revature.controller;

import com.revature.exception.InvalidSubmissionException;
import com.revature.exception.ReimbursementNotFound;
import com.revature.exception.UnathorizedSubmissionException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ReimbursementController {

    private ReimbursementService reimbursementService = new ReimbursementService();

    public void mapEndpoints(Javalin app) {

        //Only a manager should be able to access this
        app.get("/reimbursements", (ctx) -> {

            HttpSession httpSession = ctx.req.getSession();

            User user = (User) httpSession.getAttribute("user");

            if (user != null) {

                if (user.getRole().equals("Manager")) {

                    List<Reimbursement> reimbursements = reimbursementService.getPendingReimbursements();

                    ctx.json(reimbursements);

                } else if (user.getRole().equals("Employee")) {
                    int employeeId = user.getId();

                    List<Reimbursement> reimbursements = reimbursementService.getAllReimbursementsForUser(employeeId);

                    if(reimbursements.size() == 0){
                        ctx.result("You don't currently have any reimbursements submitted");
                    } else {
                        ctx.json(reimbursements);
                    }
                }
            } else {
                ctx.result("You must be logged in to see Reimbursements");
                ctx.status(401);
            }
        });

        app.post("/reimbursements", (ctx) -> {

            Reimbursement submitReimbursement = ctx.bodyAsClass(Reimbursement.class);


            HttpSession httpSession = ctx.req.getSession();

            User user = (User) httpSession.getAttribute("user");

            try {
                Reimbursement reimbursementSubmission = reimbursementService.submitReimbursement(submitReimbursement, user);

                ctx.json(reimbursementSubmission);
                ctx.status(201);
            } catch (InvalidSubmissionException e){
                ctx.result(e.getMessage());
                ctx.status(400);
            }
        });



        app.patch("/reimbursements/{reimbursementId}", (ctx) -> {

            HttpSession httpSession = ctx.req.getSession();

            User user = (User) httpSession.getAttribute("user");

            if(user != null) {

                if(user.getRole().equals("Manager")){

                    int reimbursementId = Integer.parseInt(ctx.pathParam("reimbursementId"));

                    String userRole = user.getRole();

                    Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);

                    String status = reimbursement.getStatus();

                    try {
                        reimbursementService.changeReimbursementStatus(reimbursementId, status, userRole);

                        ctx.result("Reimbursement updated successfully!");

                    } catch (ReimbursementNotFound e) {
                        ctx.result(e.getMessage());
                        ctx.status(404);
                    } catch (UnathorizedSubmissionException e) {
                        ctx.result(e.getMessage());
                        ctx.status(404);
                    }
                } else {
                    ctx.result("You are not logged in as a Manager");
                    ctx.status(401);
                }
            } else {
                ctx.result("Please login to access this data");
                ctx.status(401);
            }
        });
    }
}
