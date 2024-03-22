package org.zeros.maze_bank_final_project.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {
    private final StringProperty senderID;
    private final StringProperty receiverID;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;


    public Transaction(String senderID, String receiverID, double amount, LocalDate date, String message) {
        this.senderID = new SimpleStringProperty(this, "Sender", senderID);
        this.receiverID = new SimpleStringProperty(this, "Receiver", receiverID);
        this.amount = new SimpleDoubleProperty(this, "Amount", amount);
        this.date = new SimpleObjectProperty<>(this, "Date created", date);
        this.message = new SimpleStringProperty(this, "Message", message);
    }

    public StringProperty senderIDProperty() {
        return senderID;
    }
    public StringProperty receiverIDProperty() {
        return receiverID;
    }
    public DoubleProperty amountProperty() {
        return amount;
    }
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    public StringProperty messageProperty() {
        return message;
    }

}
