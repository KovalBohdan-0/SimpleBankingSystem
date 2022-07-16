package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBC {
    private final Connection connection;
    private final Statement statement;
    private final String[] args;


    public DBC(String[] args) {
        String url = "jdbc:sqlite:" + args[1];
        this.args = args;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(id INTEGER PRIMARY KEY,number TEXT,pin TEXT,balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAccount(Account account) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO card(number, pin, balance) VALUES (%d, %s, %d)"
                , account.getCardNumber(), account.getPin(), account.getBalance()));
    }

    public boolean login(long cardNumber, String pin) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("SELECT pin FROM card WHERE number='%s'", cardNumber));
        boolean validLogin = resultSet.next() && resultSet.getString("pin").equals(pin);
        resultSet.close();
        return validLogin;
    }

    public int getBalance(long cardNumber) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("SELECT balance FROM card WHERE number='%s'", cardNumber));
        if (resultSet.next()) {
            int balance = resultSet.getInt("balance");
            resultSet.close();
            return balance;
        }
        throw new SQLException();
    }

    public boolean cardExist(long cardNumber) throws SQLException {
        ResultSet resultSet = statement
                .executeQuery(String.format("SELECT exists (SELECT 1 FROM card WHERE number = '%s' LIMIT 1);", cardNumber));
        resultSet.next();
        boolean exist = resultSet.getString(1).equals("1");
        resultSet.close();
        return exist;
    }

    public void addBalance(int balance, long cardNumber) throws SQLException {
        int currentBalance = balance + getBalance(cardNumber);
        if (cardExist(cardNumber)) {
            statement.executeUpdate(String.format("UPDATE card SET balance=%d WHERE number='%s';", currentBalance, cardNumber));
        }
    }

    public void transfer(long cardNumber, long receiversCardNumber) throws SQLException {
        DBC dbc = new DBC(args);

        if (!Account.validateNumber(receiversCardNumber)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else if (!dbc.cardExist(receiversCardNumber)) {
            System.out.println("Such a card does not exist.");
        } else if (cardNumber == receiversCardNumber) {
            System.out.println("You can't transfer money to the same account!");
        } else {

            System.out.println("Enter how much money you want to transfer:");
            Scanner scanner = new Scanner(System.in);
            int moneyToTransfer = Integer.parseInt(scanner.nextLine());

            if (moneyToTransfer > dbc.getBalance(cardNumber)) {
                System.out.println("Not enough money!");
            } else {
                dbc.addBalance(-moneyToTransfer, cardNumber);
                dbc.addBalance(moneyToTransfer, receiversCardNumber);
                System.out.println("Success!");
            }
        }
    }

    public void deleteCard(long cardNumber) throws SQLException {
        statement.executeUpdate(String.format("DELETE FROM card WHERE number='%s'", cardNumber));
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
