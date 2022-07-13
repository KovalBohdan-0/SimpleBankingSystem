import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        String[] elements = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (String element : elements) {
            result.append(element);
        }

        System.out.println(result);
    }
}