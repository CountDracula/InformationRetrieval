import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pepe on 13.12.2016.
 */
public class Document {
   volatile int id;
    int tf;



    ConcurrentHashMap<String, Integer> termFrequency;

    public Document()
    {
        this.tf = 0;
        this.id = IndexBuilder.atom.incrementAndGet();
        this.termFrequency = new ConcurrentHashMap<String, Integer>();
    }

    public int getId()
    {
        return id;
    }

    public ConcurrentHashMap<String, Integer> getTermFrequency()
    {
        return termFrequency;
    }



}
