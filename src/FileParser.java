import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pepe on 13.12.2016.
 */
public class FileParser implements Runnable {

    BufferedReader reader;
    File file;
    String input;
    ArrayList<String> parsedWords;
    StringBuilder sb;
    Document document;
   IndexBuilder builder;


    public FileParser(File file) {
        this.file = file;
        this.builder = new IndexBuilder();

    }

    @Override
    public void run() {

        try {
            parseFiles();
            buildIndex(parsedWords);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void parseFiles() throws IOException {

        sb = new StringBuilder();
        input = null;
        parsedWords = new ArrayList<String>();

        reader = new BufferedReader(new FileReader(file));
        document = new Document();

        while ((input = reader.readLine()) != null) {

            Collections.addAll(parsedWords, input.split("[ \n\t\r;:()'-.{}]"));

        }


    }

    public synchronized void buildIndex(ArrayList<String> parsedWords) throws InterruptedException {
        for (String s : parsedWords) {


            if (!document.termFrequency.containsKey(s)) {

                document.termFrequency.put(s, 1);



            } else {

                document.termFrequency.put(s, (document.termFrequency.get(s) + 1));

            }

            if (!builder.documentMap.containsKey(s)) {
                HashSet set = new HashSet<Integer>();
                builder.documentMap.put(s, set);
                builder.documentMap.get(s).add(document.getId());
            }

            else {
                builder.documentMap.get(s).add(document.getId());
            }

        }

       builder.allDocuments.add(document);
    }


    }





