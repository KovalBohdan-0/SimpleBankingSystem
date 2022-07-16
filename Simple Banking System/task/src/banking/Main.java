package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();

        //Connecting to database
        DBC dbc = new DBC(args);


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
                        dbc.addAccount(account);
                    } catch (SQLException e) {
                     //   throw new RuntimeException(e);
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

                    if (dbc.login(cardNumber, pin)) {
                        System.out.println("You have successfully logged in!");

                        label1:
                        while (true) {
                            System.out.println("1. Balance\n" +
                                    "2. Add income\n" +
                                    "3. Do transfer\n" +
                                    "4. Close account\n" +
                                    "5. Log out\n" +
                                    "0. Exit");
                            String accountInput = scanner.nextLine();

                            switch (accountInput) {
                                case "1":
                                    System.out.println("Balance: " + dbc.getBalance(cardNumber));
                                    break;
                                case "2":
                                    System.out.println("Enter income:");
                                    int balance = Integer.parseInt(scanner.nextLine());
                                    dbc.addBalance(balance, cardNumber);
                                    System.out.println("Income was added!");
                                    break;
                                case "3":
                                    System.out.println("Transfer\n" +
                                            "Enter card number:");
                                    dbc.transfer(cardNumber, Long.parseLong(scanner.nextLine()));
                                    break;
                                case "4":
                                    dbc.deleteCard(cardNumber);
                                    System.out.println("The account has been closed!");
                                    break label1;
                                case "5":
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
                    dbc.closeConnection();
                    scanner.close();
                    break label;
            }
        }
    }
}