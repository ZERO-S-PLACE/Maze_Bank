package org.zeros.maze_bank_final_project.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.zeros.maze_bank_final_project.Views.ViewFactory;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private Client client;
    private ObservableList<Client> clientsList;
    private ObservableList<Transaction> transactionsList;



    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        this.client = new Client("", "", "", null, null, null);
        this.clientsList = FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /*

            LOGIN HANDLING

     */
    private boolean clientLoginSuccessfulFlag = false;
    private boolean adminLoginSuccessfulFlag = false;
    public void evaluateClientCred(String payeeAddress, String password) {
        ObservableList<Client> temp = databaseDriver.getClientsData(payeeAddress, password);
        if (temp.size() == 1) {
            this.client = temp.getFirst();
            setClientLoginSuccessfulFlag(true);
        }
    }

    public void evaluateAdminCred(String username, String password) {
        databaseDriver.checkAdminData(username, password);

    }
    public void setClientLoginSuccessfulFlag(boolean clientLoginSuccessfulFlag) {
        this.clientLoginSuccessfulFlag = clientLoginSuccessfulFlag;
    }
    public void setAdminLoginSuccessfulFlag(boolean adminLoginSuccessfulFlag) {
        this.adminLoginSuccessfulFlag = adminLoginSuccessfulFlag;
    }
    public boolean isClientLoginSuccessfulFlag() {
        return clientLoginSuccessfulFlag;
    }
    public boolean isAdminLoginSuccessfulFlag() {
        return adminLoginSuccessfulFlag;
    }



    /*

            UTIL METHODS

     */

    public void setClientsList() {
        clientsList = Model.getInstance().databaseDriver.getClientsData("", "");
    }

    public void deleteClient(Client client){
        clientsList.remove(client);
        Model.getInstance().databaseDriver.removeClientFromDatabase(client);
    }
    public CheckingAccount getCheckingAccount(String payeeAddress) {
        return Model.getInstance().databaseDriver.getCheckingAccountData(payeeAddress);
    }

    public SavingsAccount getSavingsAccount(String payeeAddress) {
        return Model.getInstance().databaseDriver.getSavingsAccountData(payeeAddress);
    }
    public ObservableList<Client> searchClient(String payeeAddress) {
        return Model.getInstance().databaseDriver.getClientsData(payeeAddress, "");
    }

    public ObservableList<Transaction> getTransactionsList(int limit) {
        ObservableList<Transaction> temp = FXCollections.observableArrayList();
        if (limit == -1) {
            return transactionsList;
        }
        for (int i = 0; i < limit && i < transactionsList.size(); i++) {
            temp.add(transactionsList.get(i));
        }
        return temp;
    }

    public void createTransactionsList() {
        this.transactionsList = Model.getInstance().databaseDriver.getTransactions(this.client.clientIDProperty().get());

    }
    public void addTransaction(Transaction transaction) {
        this.transactionsList.addFirst(transaction);
        Model.getInstance().databaseDriver.updateTransactionsList(transaction);
    }

/*
            GETTERS AND SETTERS

 */
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public ObservableList<Client> getClientsList() {
        return clientsList;
    }
    public void addClientToList(Client client) {
        clientsList.add(client);
    }


}
