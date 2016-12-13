import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pepe on 13.12.2016.
 */
public class FileParser implements Runnable {

    BufferedReader reader;
    File file;
    String input;
    ArrayList<String> parsedWords;
    StringBuilder sb;

    public FileParser(File file) {
        this.file = file;
    }

    @Override
    public void run() {

        try {
            parseFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseFiles() throws IOException {

        sb = new StringBuilder();
        input = null;
        parsedWords = new ArrayList<String>();

        reader = new BufferedReader(new FileReader(file));

        while ((input = reader.readLine()) != null) {

            Collections.addAll(parsedWords, input.split("[ \n\t\r;:()'-.{}]"));

        }
        String a = ("Words in DOC " + file.getName() + " are: ");
        sb.append(a);

        for (String s : parsedWords)
        {
            sb.append(s + ", ");
        }
        sb.deleteCharAt(sb.length()-2); //Remove comma (and whitespace) after last word
        System.out.println(sb.toString());
        sb.setLength(0);
    }
}

