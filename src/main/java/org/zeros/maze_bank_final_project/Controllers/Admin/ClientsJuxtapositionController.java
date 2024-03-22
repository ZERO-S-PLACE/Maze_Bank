package org.zeros.maze_bank_final_project.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.zeros.maze_bank_final_project.Models.Client;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Views.ClientCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsJuxtapositionController implements Initializable {
    public ListView<Client> clientsJuxtaposition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeData();
        clientsJuxtaposition.setItems(Model.getInstance().getClientsList());
        clientsJuxtaposition.setCellFactory(e -> new ClientCellFactory());
    }

    private void initializeData() {
        if (Model.getInstance().getClientsList().isEmpty()) {
            Model.getInstance().setClientsList();
        }
    }

}
