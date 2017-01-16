import com.sun.org.apache.xpath.internal.SourceTree;
import sun.reflect.generics.tree.Tree;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Pepe on 16.1.2017.
 */
public class Ranker {
    HashMap<Integer, Double> finalScores;

    public void ranks(String[] searchWords) {

        finalScores = new HashMap<>();

        for (int i = 0; i < searchWords.length; i++) {
            String searchWord = searchWords[i];
            Set<Integer> documents = IndexBuilder.documentMap.get(searchWord);

            for (Integer s : documents) {
                Document doc = IndexBuilder.allDocuments.get(s);

                double tf = doc.termFrequency.get(searchWord) + 0.1;


                double docSize = doc.getWordCount();


                double wordRank = tf / docSize;
                doc.results.add(wordRank);

                if (searchWords.length < 2) {
                    finalScores.put(doc.getId(), doc.results.get(0));

                }

                else
                {
                    double finalResult = 1;
                    for (Double d : doc.results)
                    {
                        finalResult *= d;

                    }
                    finalScores.put(doc.getId(), finalResult);

                }

            }

        }
    }
            /// Used to sort finalscores by value in descending (high -> low) order
            public Map<Integer, Double> sortResults(HashMap<Integer, Double> finalScores)
            {
                Set<Map.Entry<Integer, Double>> entries = finalScores.entrySet();
                List<Map.Entry<Integer,Double>> theList = new LinkedList<Map.Entry<Integer,Double>>(entries);

                Collections.sort(theList, new Comparator<Map.Entry<Integer,Double>>() {


                    @Override

                    public int compare(Map.Entry<Integer, Double> ele1,

                                       Map.Entry<Integer, Double> ele2) {



                        return ele2.getValue().compareTo(ele1.getValue());

                    }

                });

                Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
                for(Map.Entry<Integer,Double> entry: theList) {
                    sortedMap.put(entry.getKey(), entry.getValue());
                }
                return sortedMap;
            }

            public void printFinalResults(String[] searchWords)
        {
            String s = Arrays.toString(searchWords);
            DecimalFormat df = new DecimalFormat("####0.0000");
            System.out.println("The final ranking for your query: " + s + " is:");
            StringBuffer sb = new StringBuffer();

            sb.append("----------------------"+"\n");
            sb.append("DOCUMENT ID " + "\t" + "RANKING"+"\n");



            for (Map.Entry<Integer, Double> d : sortResults(finalScores).entrySet())
            {

               sb.append(d.getKey() + "\t\t\t\t" + df.format(d.getValue()) + "\n");
            }
            System.out.println(sb.toString());
            sb.setLength(0);

        }


        }







