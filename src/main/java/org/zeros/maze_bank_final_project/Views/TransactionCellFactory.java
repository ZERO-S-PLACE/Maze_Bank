package org.zeros.maze_bank_final_project.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import org.zeros.maze_bank_final_project.Controllers.Client.TransactionCellController;
import org.zeros.maze_bank_final_project.Models.Transaction;

public class TransactionCellFactory extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/TransactionCell.fxml"));
            TransactionCellController controller = new TransactionCellController(transaction);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

