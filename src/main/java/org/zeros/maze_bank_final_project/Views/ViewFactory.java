package org.zeros.maze_bank_final_project.Views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.zeros.maze_bank_final_project.Controllers.Admin.AdminController;
import org.zeros.maze_bank_final_project.Controllers.Client.ClientController;
import org.zeros.maze_bank_final_project.Models.Model;
import org.zeros.maze_bank_final_project.Models.Transaction;

import java.io.IOException;

public class ViewFactory {
    /*
     *CLIENT*OBJECTS
     */
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AccountType loginAccountType;
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;
    /*
    *ADMIN*OBJECTS
    */
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane viewOfCreateClient;
    private AnchorPane viewOfClientsJuxtaposition;
    private AnchorPane viewOfDeposit;


    public ViewFactory() {
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>(ClientMenuOptions.DASHBOARD);
        this.adminSelectedMenuItem = new SimpleObjectProperty<>(AdminMenuOptions.CREATE_CLIENT);
    }
    private static void createStage(FXMLLoader loader) {
        Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(ViewFactory.class.getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Maze Bank");
        stage.show();
    }

    /*
     *LOGIN*WINDOWS
     */

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }


    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
     *CLIENT*WINDOWS
     */
    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        Model.getInstance().createTransactionsList();
        createStage(loader);
    }
    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItemProperty() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/DashBoard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }
    public AnchorPane getTransactionsView() {
        if (transactionsView == null) {
            try {
                transactionsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transactions.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transactionsView;
    }
    public AnchorPane getAccountsView() {
        if (accountsView == null) {
            try {
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Accounts.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accountsView;
    }
    public void createMessageWindow(Transaction transaction) {

        StackPane pane = new StackPane();
        Label sender = new Label(transaction.senderIDProperty().get());
        Label message = new Label(transaction.messageProperty().get());
        VBox box = new VBox(5);
        box.getChildren().addAll(sender, message);
        box.setAlignment(Pos.CENTER);
        pane.getChildren().add(box);
        Scene scene = new Scene(pane, 300, 100);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Message");
        stage.setScene(scene);
        stage.show();
    }


    /*
     *ADMIN*WINDOWS
     */

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItemProperty() {
        return adminSelectedMenuItem;
    }
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);

        createStage(loader);
    }

    public AnchorPane getViewOfCreateClient() {
        if (viewOfCreateClient == null) {
            try {
                viewOfCreateClient = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateClient.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return viewOfCreateClient;
    }

    public AnchorPane getViewOfDeposit() {
        if (viewOfDeposit == null) {
            try {
                viewOfDeposit = new FXMLLoader(getClass().getResource("/Fxml/Admin/Deposit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return viewOfDeposit;
    }

    public AnchorPane getViewOfClientsJuxtaposition() {
        if (viewOfClientsJuxtaposition == null) {
            try {
                viewOfClientsJuxtaposition = new FXMLLoader(getClass().getResource("/Fxml/Admin/ClientsJuxtaposition.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return viewOfClientsJuxtaposition;
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
