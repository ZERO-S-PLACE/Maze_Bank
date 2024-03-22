package org.zeros.maze_bank_final_project.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Models.Transaction;
import org.zeros.maze_bank_final_project.Views.TransactionCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction> transactionsListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transactionsListView.setItems(Model.getInstance().getTransactionsList(-1));
        transactionsListView.setCellFactory(e -> new TransactionCellFactory());
    }
}
