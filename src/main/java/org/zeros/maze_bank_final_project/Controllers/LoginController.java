package org.zeros.maze_bank_final_project.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Views.AccountType;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> account_selector;
    public Label payee_adress_label;
    public TextField payee_address_text;
    public PasswordField password_text;
    public Button login_button;
    public Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_button.setOnAction(event -> onLogin());
        account_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT,AccountType.ADMIN));
        account_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        account_selector.valueProperty().addListener(observable->changeLoginAccountType());
    }

    private void changeLoginAccountType() {
        Model.getInstance().getViewFactory().setLoginAccountType(account_selector.getValue());
        if(account_selector.getValue().equals(AccountType.ADMIN)){
            payee_adress_label.setText("Username:");
        }
        else {
            payee_adress_label.setText("Payee address:");
        }
    }

    private void onLogin() {
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            tryToLoginClient();

        } else {
            tryToLoginAdmin();
        }
    }

    private void tryToLoginAdmin() {
        Model.getInstance().evaluateAdminCred(payee_address_text.getText(),password_text.getText());

        if(Model.getInstance().isAdminLoginSuccessfulFlag())
        {
            Model.getInstance().getViewFactory().showAdminWindow();
            Stage stage = (Stage) error_label.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
        else {
            displayInvalidLoginMessage();
        }
    }

    private void displayInvalidLoginMessage() {
        payee_address_text.setText("");
        password_text.setText("");
        error_label.setText("Invalid login data");
    }

    private void tryToLoginClient() {
        Model.getInstance().evaluateClientCred(payee_address_text.getText(),password_text.getText());

        if(Model.getInstance().isClientLoginSuccessfulFlag())
        {
            Model.getInstance().getViewFactory().showClientWindow();
            Stage stage = (Stage) error_label.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
        else {
            displayInvalidLoginMessage();
        }
    }
}
