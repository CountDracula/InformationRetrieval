import java.io.*;
import java.util.*;

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
    String fileIdentifier;


    public FileParser(File file) {

        this.file = file;
        this.fileIdentifier = file.getName();


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
        document = new Document(fileIdentifier);


        while ((input = reader.readLine()) != null) {

            Collections.addAll(parsedWords, input.split("[ \n\t\r;:()'-.{}]"));

        }

    document.setWordCount(parsedWords.size());

    }

    public synchronized void buildIndex(ArrayList<String> parsedWords) throws InterruptedException {
        for (String s : parsedWords) {


            if (!document.termFrequency.containsKey(s)) {

                document.termFrequency.put(s, 1);


            } else {

                document.termFrequency.put(s, (document.termFrequency.get(s) + 1));

            }

            if (!IndexBuilder.documentMap.containsKey(s)) {
                HashSet set = new HashSet<Integer>();
                IndexBuilder.documentMap.put(s, set);
                IndexBuilder.documentMap.get(s).add(document.getId());
            } else {
                IndexBuilder.documentMap.get(s).add(document.getId());
            }

        }

        IndexBuilder.allDocuments.put(document.getId(), document);
    }


}