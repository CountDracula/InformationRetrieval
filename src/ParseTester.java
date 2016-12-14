import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Pepe on 13.12.2016.
 */
public class ParseTester {



    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the folder where your files are located in: ");
        String folderPath = scanner.next();
        scanner.close();
        System.out.println("Thank you. Proceeding with building the inverted index ...");
        final IndexBuilder builder = new IndexBuilder();

        builder.readFiles(folderPath);
        builder.printResults();




    }



}