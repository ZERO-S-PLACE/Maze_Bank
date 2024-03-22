package org.zeros.maze_bank_final_project.Controllers.Admin;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.zeros.maze_bank_final_project.Models.Client;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Views.ClientCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {

    public TextField searchField;
    public Button searchBtn;

    public TextField depositAmountField;
    public Button depositAmountBtn;
    public ListView<Client> searchResultListView;
    Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBtn.setOnAction(actionEvent -> onClientSearch());
        depositAmountBtn.setOnAction(actionEvent -> onDeposit());
    }

    private void onClientSearch() {
        ObservableList<Client> searchResults = Model.getInstance().searchClient(searchField.getText());
        searchResultListView.setItems(searchResults);
        searchResultListView.setCellFactory(e -> new ClientCellFactory());
    }


    private void onDeposit() {
        double amount = Double.parseDouble(depositAmountField.getText());
        if (amount > 0) {
            client = searchResultListView.getSelectionModel().getSelectedItem();
            Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(client.clientIDProperty().get(), amount);
            emptyFields();
            client = null;
        }
    }

    private void emptyFields() {
        depositAmountField.setText("");
        searchField.setText("");
        searchResultListView.setItems(null);
    }

}

