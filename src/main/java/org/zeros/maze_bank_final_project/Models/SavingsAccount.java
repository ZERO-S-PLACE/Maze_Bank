package org.zeros.maze_bank_final_project.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsAccount extends Account {


    private final DoubleProperty withdrawalLimit;
    public SavingsAccount(String owner, String accountNumber, double balance, double withdrawalLimit) {
        super(owner, accountNumber, balance);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "Transactions limit", withdrawalLimit);

    }
    public DoubleProperty withdrawalLimitProperty() {
        return withdrawalLimit;
    }
}
