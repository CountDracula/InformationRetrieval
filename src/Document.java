import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pepe on 13.12.2016.
 */
public class Document implements Comparable<Document> {
    volatile Integer id;
    String fileName;


    ConcurrentHashMap<String, Integer> termFrequency;

    public String getFileName() {
        return fileName;
    }

    public Document(String fileName) {
        this.fileName = fileName;
        this.id = IndexBuilder.atom.incrementAndGet();
        this.termFrequency = new ConcurrentHashMap<String, Integer>();
    }

    public int getId() {
        return id;
    }

    public ConcurrentHashMap<String, Integer> getTermFrequency() {
        return termFrequency;
    }

    @Override
    public int compareTo(Document other) {
        return this.id.compareTo(other.id);
    }
}

