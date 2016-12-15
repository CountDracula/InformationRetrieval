import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.util.Pair;

import javax.print.Doc;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pepe on 13.12.2016.
 */
public class IndexBuilder {
    private ExecutorService threadPool;
    public static ConcurrentSkipListMap<String, Set<Integer>> documentMap;
    public static CopyOnWriteArrayList<Document> allDocuments;



    public IndexBuilder()
    {
        documentMap = new ConcurrentSkipListMap<String, Set<Integer>>();
        allDocuments = new CopyOnWriteArrayList<Document>();
    }



    public static AtomicInteger atom = new AtomicInteger();

    public void readFiles(String folderPath) throws InterruptedException {



        File folder = new File(folderPath);
        File[] allFiles = folder.listFiles();

        threadPool = Executors.newFixedThreadPool(allFiles.length);

        // Send each file to a separate thread
        for (File file : allFiles) {
            FileParser parser = new FileParser(file);
            threadPool.execute(parser);
        }
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);





    }

    public synchronized void printResults() throws InterruptedException {


        StringBuffer sb1 = new StringBuffer();

        System.out.println("TERM|DF|(ID,TF)");
        Collections.sort(allDocuments);


        for (Map.Entry<String, Set<Integer>> entry : documentMap.entrySet()) {
            String s = entry.getKey();
            sb1.append(s + "|" + entry.getValue().size() + "|");


            // The ID + value still gets printed out in wrong order sometimes..
            // No idea if it's the ArrayList or ConcurrentHashMap that causes the error
            for (Document d : allDocuments) {
                {

                    sb1.append("(" + d.getId() + "," + d.getTermFrequency().get(s) + ")" + "");

                }

            }
            System.out.println(sb1.toString());
            sb1.setLength(0);

        }
    }
}