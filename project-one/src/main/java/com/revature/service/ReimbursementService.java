package com.revature.service;

import com.revature.exception.InvalidSubmissionException;
import com.revature.exception.ReimbursementNotFound;
import com.revature.exception.UnathorizedSubmissionException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.repository.ReimbursementRepository;

import java.sql.SQLException;
import java.util.List;

public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();

    public List<Reimbursement> getAllReimbursements() throws SQLException {
        return reimbursementRepository.getAllReimbursements();
    }

    public List<Reimbursement> getPendingReimbursements() throws SQLException {
        return reimbursementRepository.getPendingReimbursements();
    }

    public List<Reimbursement> getAllReimbursementsForUser(int userId) throws SQLException {
        return reimbursementRepository.getAllReimbursementsForUser(userId);
    }

    public boolean changeReimbursementStatus(int reimbursementId, String status, String userRole) throws SQLException, UnathorizedSubmissionException, ReimbursementNotFound {

        Reimbursement reimbursement = reimbursementRepository.getReimbursementById(reimbursementId);

        if (reimbursement == null) {
            throw new ReimbursementNotFound("Reimbursement with id " + reimbursementId + " was not found");
        }

        if (reimbursement.getStatus().equals("Approved")) {
            throw new UnathorizedSubmissionException("Reimbursement with id " + reimbursementId + " has already been Processed");
        }

        if(reimbursement.getStatus().equals("Denied")){
            throw new UnathorizedSubmissionException("Reimbursement with id " + reimbursementId + " has already been Processed");
        }


        if((!userRole.equals("Manager"))){
            throw new UnathorizedSubmissionException("Reimbursement with id " + reimbursementId + " has already been Processed");
        }

        // Grading assignment
        return reimbursementRepository.changeReimbursementStatus(reimbursementId, status);
    }

    public Reimbursement submitReimbursement(Reimbursement reimbursement, User user) throws SQLException, InvalidSubmissionException {

        if(reimbursement.getDescription() == null){
            throw new InvalidSubmissionException("In order to submit a reimbursement request, you must have both an amount and description");
        }

        if(reimbursement.getAmount() <= 0){
            throw new InvalidSubmissionException("In order to submit a reimbursement request, you must have both an amount and description");
        }

        Reimbursement reimbursementSubmitted = reimbursementRepository.submitReimbursement(reimbursement, user);

        return reimbursementSubmitted;
    }
}
