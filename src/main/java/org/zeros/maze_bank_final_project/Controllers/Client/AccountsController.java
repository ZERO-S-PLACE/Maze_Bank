package org.zeros.maze_bank_final_project.Controllers.Client;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.zeros.maze_bank_final_project.Models.CheckingAccount;
import org.zeros.maze_bank_final_project.Models.Client;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Models.SavingsAccount;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label checkingAccountNumber;
    public Label checkingAccountTransactions;
    public Label checkingAccountDate;
    public Label checkingAccountBalance;
    public Label savingsAccNumber;
    public Label savingsAccLimit;
    public Label savingsAccDate;
    public Label savingsAccBalance;
    public TextField moveToSavingsLbl;
    public Button moveToSavingsBtn;
    public TextField moveToCheckingLbl;
    public Button moveToCheckingBtn;
    Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = Model.getInstance().getClient();
        loadInitialLabelValues();
        moveToCheckingBtn.setOnAction(e -> moveToCheckingAccount());
        moveToSavingsBtn.setOnAction(e -> moveToSavingsAccount());
    }

    private void moveToCheckingAccount() {
        try {
            double amount = Double.parseDouble(moveToCheckingLbl.getText());
            if (amount > 0 && amount < client.savingsAccountProperty().get().balanceProperty().getValue()) {
                Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(client.clientIDProperty().get(), amount);
                client.checkingAccountProperty().get().updateBalanceProperty(amount);

                Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(client.clientIDProperty().get(), -1 * amount);
                moveToCheckingLbl.setText("");
                client.savingsAccountProperty().get().updateBalanceProperty(-1 * amount);
                return;
            }
            moveToCheckingLbl.setText("ERROR");
        } catch (Exception e) {
            moveToCheckingLbl.setText("ERROR");
        }
    }

    private void moveToSavingsAccount() {
        try {
            double amount = Double.parseDouble(moveToSavingsLbl.getText());
            if (amount < client.checkingAccountProperty().get().balanceProperty().getValue()) {
                Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(client.clientIDProperty().get(), amount);
                client.savingsAccountProperty().get().updateBalanceProperty(amount);
                Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(client.clientIDProperty().get(), -1 * amount);
                moveToSavingsLbl.setText("");
                client.checkingAccountProperty().get().updateBalanceProperty(-1 * amount);
                return;
            }
            moveToSavingsLbl.setText("ERROR");
        } catch (Exception e) {
            moveToSavingsLbl.setText("ERROR");
        }
    }

    private void loadInitialLabelValues() {
        checkingAccountNumber.setText(client.checkingAccountProperty().get().accountNumberProperty().get());
        checkingAccountTransactions.setText(String.valueOf(((CheckingAccount) (client.checkingAccountProperty().get())).transactionsLimitProperty().get()));
        checkingAccountDate.setText(String.valueOf(client.dateCreatedProperty().get()));
        checkingAccountBalance.textProperty().bind(Bindings.concat("$").concat(client.checkingAccountProperty().get().balanceProperty()));
        savingsAccNumber.setText(client.savingsAccountProperty().get().accountNumberProperty().get());
        savingsAccLimit.setText(String.valueOf(((SavingsAccount) (client.savingsAccountProperty().get())).withdrawalLimitProperty().get()));
        savingsAccDate.setText(String.valueOf(client.dateCreatedProperty().get()));
        savingsAccBalance.textProperty().bind(Bindings.concat("$").concat(client.savingsAccountProperty().get().balanceProperty()));
    }
}
