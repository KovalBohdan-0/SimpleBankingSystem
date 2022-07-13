import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        String[] numbers = scanner.nextLine().split(" ");
        for(int i = 0;i<4;i++){
            System.out.print(Integer.parseInt(numbers[i])-1 + " ");
        }
    }
}