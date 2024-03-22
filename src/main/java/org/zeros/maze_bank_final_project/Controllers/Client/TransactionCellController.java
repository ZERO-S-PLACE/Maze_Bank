package org.zeros.maze_bank_final_project.Controllers.Client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Models.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView inIcon;
    public FontAwesomeIconView outIcon;
    public Label dateLbl;
    public Label senderLbl;
    public Label receiverLbl;
    public Label valueLbl;
    public Button messageBtn;
    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setInitialValues();

        messageBtn.setOnAction(e -> Model.getInstance().getViewFactory().createMessageWindow(transaction));

    }

    private void setInitialValues() {
        dateLbl.setText(String.valueOf(transaction.dateProperty().get()));
        valueLbl.setText(String.valueOf(transaction.amountProperty().get()));
        receiverLbl.setText(transaction.receiverIDProperty().get());
        senderLbl.setText(transaction.senderIDProperty().get());
        if (receiverLbl.getText().equals(Model.getInstance().getClient().clientIDProperty().get())) {
            outIcon.setVisible(false);
        } else {
            inIcon.setVisible(false);
        }
    }
}
