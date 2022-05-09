package com.swe265.bank.entity;


/**
 *  This is an entity to record user info
 */
public class Account {
    private double amount;
    private long id;

    public Account() {
        // TODO
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
