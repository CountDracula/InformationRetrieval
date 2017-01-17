
import java.util.Iterator;
import java.util.Map;
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
        System.out.println("Do you want to enter another query? Y/N?");
        String input2 = scanner.next();
        if (input2.equals("Y")||input2.equals("y")) {
        clearResults(ranker);

            scanner.nextLine(); // Needed for when user presses enter after inputting folder location
            promptQuery(ranker, scanner);
        }
        else {
            scanner.close();
            System.out.println("Exiting..");

        }

        }

public static void clearResults(Ranker ranker)
{
    ranker.finalScores.clear();
  for (Document doc : IndexBuilder.allDocuments.values())
  {
      doc.results.clear();
  }
}

public static void promptQuery(Ranker ranker, Scanner scanner)
    {
        System.out.println("Please enter your search words separated by space: ");
        String s = scanner.nextLine();
        String[] queryWords = s.split("\\s+");
        ranker.ranks(queryWords);
        ranker.printFinalResults(queryWords);

        System.out.println("Do you want to enter another query? Y/N?");
        String input = scanner.next();
        if (input.equals("Y")||input.equals("y")) {
            clearResults(ranker);
            scanner.nextLine(); // Needed for when user presses enter after inputting folder location

            promptQuery(ranker, scanner);
        }
        else {
            scanner.close();
            System.out.println("Exiting..");

        }

    }



    }



