package org.zeros.maze_bank_final_project.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.zeros.maze_bank_final_project.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane adminParent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case CLIENTS_JUXTAPOSITION:
                    adminParent.setCenter(Model.getInstance().getViewFactory().getViewOfClientsJuxtaposition());
                    break;
                case DEPOSIT_PANEL:
                    adminParent.setCenter(Model.getInstance().getViewFactory().getViewOfDeposit());
                    break;
                case CREATE_CLIENT:
                    adminParent.setCenter(Model.getInstance().getViewFactory().getViewOfCreateClient());
                    break;
            }
        }));
    }
}
