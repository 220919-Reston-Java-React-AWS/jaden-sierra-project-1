package com.revature.model;

import java.util.Objects;

public class Reimbursement {
    private int id;
    private double amount;
    private String description;
    private String status;

    private int userId;

    public Reimbursement(){

    }

    public Reimbursement(int id, double amount, String description, String status, int userId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reimbursement)) return false;
        Reimbursement that = (Reimbursement) o;
        return getId() == that.getId() && Double.compare(that.getAmount(), getAmount()) == 0 && getUserId() == that.getUserId() && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmount(), getDescription(), getStatus(), getUserId());
    }
}
