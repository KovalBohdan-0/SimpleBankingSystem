package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();

        //Connecting to database
        String url = "jdbc:sqlite:" + args[1];

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(id INTEGER,number TEXT,pin TEXT,balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        label:
        while (true) {
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");

            String input = scanner.nextLine();

            switch (input) {
                case "1": {
                    Account account = new Account();
                    accounts.add(account);

                    try {
                        Connection connection = dataSource.getConnection();
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(String.format("INSERT INTO card(number, pin, balance) VALUES (%d, %s, %d)"
                                , account.getCardNumber(), account.getPin(), account.getBalance()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Your card has been created");
                    System.out.println("Your card number:\n" + account.getCardNumber());
                    System.out.println("Your card PIN:\n" + account.getPin());
                    break;
                }
                case "2": {
                    System.out.println("Enter your card number:");
                    long cardNumber = Long.parseLong(scanner.nextLine());
                    System.out.println("Enter your PIN:");
                    String pin = scanner.nextLine();

                    Account loggedAccount = null;

                    for (Account account : accounts) {
                        if (account.getCardNumber() == cardNumber && account.getPin().equals(pin)) {
                            loggedAccount = account;
                        }
                    }

                    if (loggedAccount != null) {
                        System.out.println("You have successfully logged in!");

                        label1:
                        while (true) {
                            System.out.println("1. Balance\n" +
                                    "2. Log out\n" +
                                    "0. Exit");
                            String accountInput = scanner.nextLine();

                            switch (accountInput) {
                                case "1":
                                    System.out.println("Balance: " + loggedAccount.getBalance());
                                    break;
                                case "2":
                                    System.out.println("You have successfully logged out!");
                                    break label1;
                                case "0":
                                    System.out.println("Bye!");
                                    break label;
                            }
                        }

                    } else {
                        System.out.println("Wrong card number or PIN!");
                    }
                    break;
                }
                case "0":
                    System.out.println("Bye!");
                    break label;
            }
        }
    }
}