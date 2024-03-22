package org.zeros.maze_bank_final_project.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Views.AdminMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button createClientBtn;
    public Button clientsJuxtapositionBtn;
    public Button depositBtn;
    public Button logoutBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        createClientBtn.setOnAction(event -> onCreateClient());
        clientsJuxtapositionBtn.setOnAction(event -> onClientsJuxtaposition());
        depositBtn.setOnAction(event -> onDeposit());
        logoutBtn.setOnAction(event -> onLogout());

    }

    private void onCreateClient() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty().set(AdminMenuOptions.CREATE_CLIENT);
    }

    private void onDeposit() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty().set(AdminMenuOptions.DEPOSIT_PANEL);
    }

    private void onClientsJuxtaposition() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty().set(AdminMenuOptions.CLIENTS_JUXTAPOSITION);
    }

    private void onLogout() {
        Stage stage = (Stage) createClientBtn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().setAdminLoginSuccessfulFlag(false);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
