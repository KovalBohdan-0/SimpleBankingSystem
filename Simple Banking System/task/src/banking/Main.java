package banking;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Account> accounts = new ArrayList<>();

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

                    for (Account account: accounts) {
                        if(account.getCardNumber() == cardNumber && account.getPin().equals(pin)) {
                            loggedAccount =account;
                        }
                    }

                    if (loggedAccount != null) {
                        System.out.println("You have successfully logged in!");
                        boolean toBreak = false;

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
                                    toBreak = true;
                                    System.out.println("Bye!");
                                    break label1;
                            }
                        }

                        if (toBreak) {
                            break label;
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