package org.zeros.maze_bank_final_project.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Views.ClientMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();

    }

    private void addListeners() {
        dashboard_btn.setOnAction(event -> onDashBoard());
        transaction_btn.setOnAction(event -> onTransactions());
        accounts_btn.setOnAction(actionEvent -> onAccounts());
        logout_btn.setOnAction(actionEvent -> onLogout());
    }

    private void onDashBoard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItemProperty().set(ClientMenuOptions.DASHBOARD);
    }

    private void onTransactions() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItemProperty().set(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItemProperty().set(ClientMenuOptions.ACCOUNTS);
    }

    private void onLogout() {
        Stage stage = (Stage) accounts_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().setClientLoginSuccessfulFlag(false);
        Model.getInstance().getViewFactory().showLoginWindow();
    }


}
