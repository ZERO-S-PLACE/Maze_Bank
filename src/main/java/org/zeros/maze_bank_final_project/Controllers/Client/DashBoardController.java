package org.zeros.maze_bank_final_project.Controllers.Client;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.zeros.maze_bank_final_project.Models.Client;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Models.Transaction;
import org.zeros.maze_bank_final_project.Views.TransactionCellFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {


    public Label date_label;
    public Label acc_1_value;
    public Label acc_1_number_p1;
    public Label acc1_number_p2;
    public Label acc_2_value;
    public Label acc2_number_p1;
    public Label acc2_number_p2;
    public Label income_value;
    public Label expenses_value;
    public ListView<Transaction> transactions_listView;
    public TextField payee_address;
    public TextField payee_money;
    public TextArea payee_message;
    public Button send_money_btn;
    public Label userName_lbl;

    private Client client;
    DoubleProperty income;
    DoubleProperty expenses;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().createTransactionsList();
        client = Model.getInstance().getClient();
        income = new SimpleDoubleProperty(0);
        expenses = new SimpleDoubleProperty(0);
        calculateIncomeAndExpenses();
        bindData();
        send_money_btn.setOnAction(e -> performTransaction());
    }


    private void bindData() {

        userName_lbl.textProperty().bind(Bindings.concat("Hi, ").concat(client.firstNameProperty()));
        date_label.textProperty().bind(Bindings.concat("Date: ").concat(LocalDate.now()));
        acc1_number_p2.setText(client.checkingAccountProperty().get().accountNumberProperty().get().substring(5));
        acc2_number_p2.setText(client.savingsAccountProperty().get().accountNumberProperty().get().substring(5));
        acc_1_value.textProperty().bind(Bindings.concat("$ ").concat(client.checkingAccountProperty().get().balanceProperty().get()));
        acc_2_value.textProperty().bind(Bindings.concat("$ ").concat(client.savingsAccountProperty().get().balanceProperty().get()));
        transactions_listView.setItems(Model.getInstance().getTransactionsList(4));
        transactions_listView.setCellFactory(e -> new TransactionCellFactory());
        income_value.textProperty().bind(Bindings.concat("$ ").concat(income));
        expenses_value.textProperty().bind(Bindings.concat("$ ").concat(expenses));

    }

    private void calculateIncomeAndExpenses() {
        ObservableList<Transaction> transactions = Model.getInstance().getTransactionsList(-1);
        for (Transaction transaction : transactions) {
            if (transaction.dateProperty().get().getYear() == LocalDate.now().getYear()) {
                updateIncomeAndExpenses(transaction);
            }
        }
    }

    private void updateIncomeAndExpenses(Transaction transaction) {

        if (transaction.receiverIDProperty().get().equals(Model.getInstance().getClient().clientIDProperty().get())) {
            income.setValue(income.getValue() + transaction.amountProperty().getValue());
        } else {
            expenses.setValue(expenses.getValue() + transaction.amountProperty().getValue());
        }

    }

    private void performTransaction() {
        if (checkIfLegal()) {
            double amount = Double.parseDouble(payee_money.getText());
            String receiver = payee_address.getText();
            Transaction transaction = new Transaction(Model.getInstance().getClient().clientIDProperty().get(), receiver, amount, LocalDate.now(), payee_message.getText());
            Model.getInstance().addTransaction(transaction);
            Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(receiver, amount);
            Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(Model.getInstance().getClient().clientIDProperty().get(), -1 * amount);
            emptyTextFields();
            payee_message.setText("$" + amount + "send succesfully to " + receiver);
            transactions_listView.setItems(Model.getInstance().getTransactionsList(4));
            updateIncomeAndExpenses(transaction);

        }
        else {
            emptyTextFields();
            payee_message.setText("ERROR");
        }

    }

    private void emptyTextFields() {
        payee_money.setText("");
        payee_address.setText("");
        payee_message.setText("");
    }

    private boolean checkIfLegal() {
        try {
            if (payee_address.getText() != null && payee_money.getText() != null) {

                if (Model.getInstance().searchClient(payee_address.getText()).size() == 1) {
                    double amount = Double.parseDouble(payee_money.getText());
                    if (amount > 0 && client.checkingAccountProperty().get().balanceProperty().getValue() >= amount) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
