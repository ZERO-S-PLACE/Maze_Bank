package org.zeros.maze_bank_final_project.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account {
    private final IntegerProperty transactionsLimit;


    public CheckingAccount(String owner, String accountNumber, double balance, int transactionsLimit) {
        super(owner, accountNumber, balance);
        this.transactionsLimit = new SimpleIntegerProperty(this, "Transactions limit", transactionsLimit);

    }

    public IntegerProperty transactionsLimitProperty() {
        return transactionsLimit;
    }
}
