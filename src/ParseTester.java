import java.util.Scanner;

/**
 * Created by Pepe on 13.12.2016.
 */
public class ParseTester {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the folder where your files are located in: ");
        String folderPath = scanner.next();
        scanner.close();
        System.out.println("Thank you. Proceeding with building the inverted index ...");
        IndexBuilder builder = new IndexBuilder();


        builder.readFiles(folderPath);
    }
}
