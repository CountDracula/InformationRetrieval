
import java.util.Scanner;

/**
 * Created by Pepe on 13.12.2016.
 */
public class ParseTester {



    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the folder where your files are located in: ");
        String folderPath = scanner.next();
        scanner.nextLine(); // Needed for when user presses enter after inputting folder location
        System.out.println("Thank you. Proceeding with building the inverted index ...");
        final IndexBuilder builder = new IndexBuilder();
        final Ranker ranker = new Ranker();

        builder.readFiles(folderPath);
        builder.printResults();
        System.out.println("Please enter your search words separated by space: ");
        String input = scanner.nextLine();
        String[] searchWords = input.split("\\s+");
        ranker.ranks(searchWords);
        ranker.printFinalResults(searchWords);






    }



}