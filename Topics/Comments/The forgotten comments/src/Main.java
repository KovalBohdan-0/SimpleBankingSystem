import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//The restriction: the class should have the name Main
public class Main {

    //The start point of your program
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\gfton\\Downloads\\data.txt");
        Scanner fileScanner = new Scanner(file);
        int evenCount = 0;

        while (fileScanner.hasNext()) {
            int number = Integer.parseInt(fileScanner.nextLine());

            if(number == 0) {
                break;
            } else if(number % 2 == 0){
                evenCount++;
            }
        }

        System.out.println(evenCount);
    }

}