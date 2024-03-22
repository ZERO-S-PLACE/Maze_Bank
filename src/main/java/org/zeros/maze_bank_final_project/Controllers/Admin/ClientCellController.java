package org.zeros.maze_bank_final_project.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.zeros.maze_bank_final_project.Models.Client;
import org.zeros.maze_bank_final_project.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    public Label creationDateLbl;
    public Button deleteClientBtn;
    public Label checkingAccountNumberLbl;
    public Label savingsAccountNumberLbl;
    public Label addressLbl;
    public Label surnameLbl;
    public Label nameLbl;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        displayClientData();
        displayInOrOutArrow();
        deleteClientBtn.setOnAction(e->deleteClient());


    }

    private void deleteClient(){
        Model.getInstance().deleteClient(client);
    }

    private void displayClientData() {
        creationDateLbl.setText(String.valueOf(client.dateCreatedProperty().get()));
        nameLbl.setText(client.firstNameProperty().get());
        surnameLbl.setText(client.lastNameProperty().get());
        addressLbl.setText(client.clientIDProperty().get());
    }

    private void displayInOrOutArrow() {
        if (client.savingsAccountProperty().get() != null) {
            savingsAccountNumberLbl.setText(client.savingsAccountProperty().get().accountNumberProperty().get());
        }
        else {
            savingsAccountNumberLbl.setText("");
        }

        if (client.checkingAccountProperty().get() != null) {
            checkingAccountNumberLbl.setText(client.checkingAccountProperty().get().accountNumberProperty().get());
        }
        else {
            checkingAccountNumberLbl.setText("");
        }
    }

}
