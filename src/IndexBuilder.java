import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Pepe on 13.12.2016.
 */
public class IndexBuilder {
    private ExecutorService threadPool;


public void readFiles(String folderPath)
{
    File folder = new File(folderPath);
    File[] allFiles = folder.listFiles();

    threadPool = Executors.newFixedThreadPool(allFiles.length);


    for (File file : allFiles)
    {
        FileParser parser = new FileParser(file);
        threadPool.execute(parser);

    }


}

}
