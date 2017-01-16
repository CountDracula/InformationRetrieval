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
   // public static HashMap<Integer, Document> documentID;
   // public static CopyOnWriteArrayList<Document> allDocuments;

    public static ConcurrentSkipListMap<Integer, Document> allDocuments;



    public IndexBuilder()
    {
        documentMap = new ConcurrentSkipListMap<String, Set<Integer>>();
        allDocuments = new ConcurrentSkipListMap<Integer, Document>();
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


        StringBuffer sb2 = new StringBuffer();
        printDocumentMap();
        System.out.println("TERM|DF|(ID,TF)");
        //Collections.sort(allDocuments);


        for (Map.Entry<String, Set<Integer>> entry : documentMap.entrySet()) {

            String s = entry.getKey();
            sb2.append(s + "|" + entry.getValue().size() + "|");

           for (Map.Entry<Integer, Document> entry2 : allDocuments.entrySet())
           {
                Document d = entry2.getValue();
                sb2.append("(" + d.getId() + "," + d.getTermFrequency().get(s) + ")" + "");

            }

            System.out.println(sb2.toString());

            sb2.setLength(0);
        }
    }



        public void printDocumentMap()
    {
        StringBuffer sb1 = new StringBuffer();
        sb1.append("DOCUMENT MAPPING: " +"\n");
        sb1.append("----------------------"+"\n");
        sb1.append("ID " + "\t" + "Name"+"\n");



        for (Map.Entry<Integer, Document> d : allDocuments.entrySet())
        {

            sb1.append(d.getKey() + "\t" + d.getValue().getFileName()+"\n");
        }
        System.out.println(sb1.toString());
        sb1.setLength(0);
    }
    }
