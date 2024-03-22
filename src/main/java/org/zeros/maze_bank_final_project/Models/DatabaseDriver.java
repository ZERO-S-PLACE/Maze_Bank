package org.zeros.maze_bank_final_project.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            Path currentFolderPath = Paths.get(System.getProperty("user.dir"));
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + currentFolderPath + "\\mazebank.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*

     * ADMIN SECTION

     */
    public void checkAdminData(String username, String password) {
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM Admins WHERE Username=? AND Password=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                resultSet = preparedStatement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    Model.getInstance().setAdminLoginSuccessfulFlag(true);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createClient(Client client, String password) {

        try {
            String query = "INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, Date) VALUES (?,?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, client.firstNameProperty().get());
                preparedStatement.setString(2, client.lastNameProperty().get());
                preparedStatement.setString(3, client.clientIDProperty().get());
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, String.valueOf(client.dateCreatedProperty().get()));
                preparedStatement.executeUpdate();
                Model.getInstance().addClientToList(client);
            }
            Model.getInstance().setClientsList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getLastClientId() {
        int id = 0;
        ResultSet resultSet;
        try {
            String query = "SELECT seq FROM sqlite_sequence WHERE name='Clients';";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query);

                resultSet = statement.getResultSet();

                if (resultSet.next()) {
                    id = resultSet.getInt("seq");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public void createCheckingAccount(String owner, String numberAccount, double transactionLimit, double balance) {

        try {
            String query = "INSERT INTO CheckingAccounts (Owner, AccountNumber, TransactionLimit, Balance) VALUES (?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, owner);
                preparedStatement.setString(2, numberAccount);
                preparedStatement.setString(3, String.valueOf(transactionLimit));
                preparedStatement.setString(4, String.valueOf(balance));
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSavingsAccount(String owner, String numberAccount, double wLimit, double balance) {

        try {
            String query = "INSERT INTO SavingsAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) VALUES (?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, owner);
                preparedStatement.setString(2, numberAccount);
                preparedStatement.setString(3, String.valueOf(wLimit));
                preparedStatement.setString(4, String.valueOf(balance));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeClientFromDatabase(Client client){

        String payeeAddress =client.clientIDProperty().get();

            try {
                String query = "DELETE FROM SavingsAccounts WHERE Owner=?;";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, payeeAddress);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ignored) {}

        try {
            String query = "DELETE FROM CheckingAccounts WHERE Owner=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, payeeAddress);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ignored) {}

        try {
            String query = "DELETE FROM Clients WHERE PayeeAddress=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, payeeAddress);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ignored) {}


    }


    /*

     * CLIENT SECTION

     */
    public void updateTransactionsList(Transaction transaction) {
        try {
            String query = "INSERT INTO Transactions (Sender, Receiver,Amount,Date,Message) VALUES (?,?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, transaction.senderIDProperty().get());
                preparedStatement.setString(2, transaction.receiverIDProperty().get());
                preparedStatement.setString(3, String.valueOf(transaction.amountProperty().get()));
                preparedStatement.setString(4, String.valueOf(transaction.dateProperty().get()));
                preparedStatement.setString(5, transaction.messageProperty().get());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Transaction> getTransactions(String payeeAddress) {
        ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
        ResultSet resultSet;
        String query;

        query = "SELECT * FROM Transactions WHERE Sender='" + payeeAddress + "' OR Receiver='" + payeeAddress + "';";


        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute(query);

                resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    String sender = resultSet.getString("Sender");
                    String receiver = resultSet.getString("Receiver");
                    String message = resultSet.getString("Message");
                    double amount = resultSet.getDouble("Amount");
                    String[] dateParts = resultSet.getString("Date").split("-");
                    LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                    transactionsList.addFirst(new Transaction(sender, receiver, amount, date, message));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionsList;
    }



    /*

     * UTIL SECTION

     */

    public void updateSavingsAccountBalance(String payeeAddress, double amount) {
        double newBalance = getSavingsAccountData(payeeAddress).balanceProperty().get() + amount;
        try {
            String query = "UPDATE SavingsAccounts SET Balance=? WHERE Owner=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, String.valueOf(newBalance));
                preparedStatement.setString(2, payeeAddress);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ignored) {
        }
    }

    public void updateCheckingAccountBalance(String payeeAddress, double amount) {
        double newBalance = getCheckingAccountData(payeeAddress).balanceProperty().get() + amount;
        try {
            String query = "UPDATE CheckingAccounts SET Balance=? WHERE Owner=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, String.valueOf(newBalance));
                preparedStatement.setString(2, payeeAddress);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ignored) {
        }

    }

    public ObservableList<Client> getClientsData(String payeeAddress, String password) {
        ObservableList<Client> clientsList = FXCollections.observableArrayList();
        ResultSet resultSet;
        String query;
        if (password.isEmpty()) {
            if (payeeAddress.isEmpty()) {
                query = "SELECT * FROM Clients;";
            } else {
                query = "SELECT * FROM Clients WHERE PayeeAddress LIKE '%" + payeeAddress + "%';";
            }

        } else {
            query = "SELECT * FROM Clients WHERE PayeeAddress='" + payeeAddress + "' AND Password='" + password + "';";
        }

        try {

            try (Statement statement = connection.createStatement()) {
                statement.execute(query);

                resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String readPayeeAddress = resultSet.getString("PayeeAddress");
                    String[] dateParts = resultSet.getString("Date").split("-");
                    LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                    CheckingAccount checkingAccount = Model.getInstance().getCheckingAccount(readPayeeAddress);
                    SavingsAccount savingsAccount = Model.getInstance().getSavingsAccount(readPayeeAddress);
                    clientsList.add(new Client(firstName, lastName, readPayeeAddress, checkingAccount, savingsAccount, date));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientsList;
    }

    public CheckingAccount getCheckingAccountData(String payeeAddress) {
        CheckingAccount checkingAccount = null;
        ResultSet resultSet;

        try {
            String query = "SELECT * FROM CheckingAccounts WHERE Owner='" + payeeAddress + "';";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query);

                resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    String accountNumber = resultSet.getString("AccountNumber");
                    int transactionLimit = (int) resultSet.getDouble("TransactionLimit");
                    double balance = resultSet.getDouble("Balance");
                    checkingAccount = new CheckingAccount(payeeAddress, accountNumber, balance, transactionLimit);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkingAccount;

    }

    public SavingsAccount getSavingsAccountData(String payeeAddress) {
        ResultSet resultSet;
        SavingsAccount savingsAccount = null;

        try {
            String query = "SELECT * FROM SavingsAccounts WHERE Owner='" + payeeAddress + "';";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query);

                resultSet = statement.getResultSet();


                if (resultSet.next()) {
                    String accountNumber = resultSet.getString("AccountNumber");
                    double withdrawalLimit = resultSet.getDouble("WithdrawalLimit");
                    double balance = resultSet.getDouble("Balance");
                    savingsAccount = new SavingsAccount(payeeAddress, accountNumber, balance, withdrawalLimit);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savingsAccount;

    }



}
