package banking;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Account {
    private final long cardNumber;
    private final String pin;
    private int balance;

    public Account() {
        //Create valid card number and pin
        this.cardNumber = createCardNumber();
        this.pin = createPin();
    }

    private long createCardNumber() {
        long cardNumber = ThreadLocalRandom.current().nextLong(9999999999L) + 4000000000000000L;
        StringBuilder numbers = new StringBuilder(Long.toString(cardNumber));
        int[] cardNumbers = new int[numbers.length()];

        //Luhn algorithm
        int sum = 0;

        for (int i = 0; i < cardNumbers.length; i++) {
            cardNumbers[i] = numbers.charAt(i) - '0';

            if (i != cardNumbers.length - 1) {
                if ((i + 1) % 2 != 0) {
                    cardNumbers[i] *= 2;

                    if (cardNumbers[i] > 9) {
                        cardNumbers[i] -= 9;
                    }
                }
                sum += cardNumbers[i];
            }

        }

        cardNumbers[cardNumbers.length - 1] = (10 - (sum % 10)) % 10;

        for (int i = 0; i < cardNumbers.length - 1; i++) {
            cardNumbers[i] = numbers.charAt(i) - '0';
        }

        //Transform int array to int
        numbers = new StringBuilder();

        for (int number : cardNumbers) {
            numbers.append(number);
        }

        cardNumber = Long.parseLong(numbers.toString());

        return cardNumber;
    }

    private String createPin() {
        Random random = new Random();

        return String.format("%04d", random.nextInt(9999));
    }

    public static boolean validateNumber (long cardNumber) {
        StringBuilder numbers = new StringBuilder(Long.toString(cardNumber));
        int[] cardNumbers = new int[numbers.length()];

        int sum = 0;

        for (int i = 0; i < cardNumbers.length; i++) {
            cardNumbers[i] = numbers.charAt(i) - '0';

            if (i != cardNumbers.length - 1) {
                if ((i + 1) % 2 != 0) {
                    cardNumbers[i] *= 2;

                    if (cardNumbers[i] > 9) {
                        cardNumbers[i] -= 9;
                    }
                }
                sum += cardNumbers[i];
            }

        }

        if ((cardNumbers[cardNumbers.length - 1]+sum) % 10 == 0) {
            return true;
        }

        return false;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
