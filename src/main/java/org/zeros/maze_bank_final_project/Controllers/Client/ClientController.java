package org.zeros.maze_bank_final_project.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.zeros.maze_bank_final_project.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItemProperty().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case TRANSACTIONS ->
                        client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                case DASHBOARD -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());

            }
        }));
    }
}
