package org.zeros.maze_bank_final_project.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Account {

    private final StringProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;


    public Account(String owner, String accountNumber, double balance) {
        this.owner = new SimpleStringProperty(this, "owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "account number", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "balance", balance);
    }

    public StringProperty ownerProperty() {
        return owner;
    }
    public StringProperty accountNumberProperty() {
        return accountNumber;
    }
    public DoubleProperty balanceProperty() {
        return balance;
    }
    public void updateBalanceProperty(double amount) {
        balanceProperty().setValue(balance.getValue() + amount);
    }

}
