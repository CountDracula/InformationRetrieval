
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pepe on 13.12.2016.
 */
public class Document implements Comparable<Document> {
    volatile Integer id;
    String fileName;
    int wordCount;
    ArrayList<Double> results;


    ConcurrentHashMap<String, Integer> termFrequency;

    public String getFileName() {
        return fileName;
    }

    public Document(String fileName) {
        this.fileName = fileName;
        this.id = IndexBuilder.atom.incrementAndGet();
        this.termFrequency = new ConcurrentHashMap<String, Integer>();
        this.results = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getWordCount(){
        return wordCount;
    }

    public void setWordCount(Integer wordCount)
    {
        this.wordCount = wordCount;
    }

    public ConcurrentHashMap<String, Integer> getTermFrequency() {
        return termFrequency;
    }

    @Override
    public int compareTo(Document other) {
        return this.id.compareTo(other.id);
    }
}

