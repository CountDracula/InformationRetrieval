

import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by Pepe on 16.1.2017.
 */
public class Ranker {
    HashMap<Integer, Double> finalScores;

    public void ranks(String[] searchWords) {

        finalScores = new HashMap<>();

        for (int i = 0; i < searchWords.length; i++) {
            String searchWord = searchWords[i];

            // If the searchWord doesn't appear in our documentMap, add it so the program doesn't hang
            if (!IndexBuilder.documentMap.containsKey(searchWord)) {
                HashSet set = new HashSet<Integer>();
                IndexBuilder.documentMap.put(searchWord, set);



            }

            Set<Integer> documents = IndexBuilder.documentMap.get(searchWord);


            for (Integer s : documents) {
                Document doc = IndexBuilder.allDocuments.get(s);



                        // Some error here, we never enter this block so can't modify TF of zero occurrence terms
                       if (!doc.getTermFrequency().containsKey(searchWord))
                {
                    System.out.println("WHY DO WE NOT GET HERE?");
                    IndexBuilder.documentMap.get(searchWord).add(doc.getId());
                                        doc.termFrequency.put(searchWord, 0);
                    doc.setWordCount(doc.getWordCount()+1);
                }





                // Add 0.1 to all results (Laplace smoothing)
                double tf = doc.termFrequency.get(searchWord) + 0.1;


                double docSize = doc.getWordCount();


                double wordRank = tf / docSize;
                doc.results.add(wordRank);

                /// If there is only 1 term to search for, we don't need to multiply with probability of 2nd term so we can return the wordRank of the first term, i.e. first result in the result array
                if (searchWords.length < 2) {
                    finalScores.put(doc.getId(), doc.results.get(0));

                }

                else
                {
                    double finalResult = 1;

                    // P(q|d) = wordRank term1 (tf (+ laplacian smoother) / docSize) * wordRank term2 * ... * wordRank term n
                    for (Double d : doc.results)
                    {
                        finalResult *= d;

                    }
                    finalScores.put(doc.getId(), finalResult);

                }

            }

        }
    }
            /// Used to sort the final scores by value in descending (high -> low) order
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

            // Round the result to four decimal places
            DecimalFormat df = new DecimalFormat("####0.0000");
            System.out.println("The final ranking for your query: " + s + " is:");
            StringBuffer sb = new StringBuffer();

            sb.append("----------------------"+"\n");
            sb.append("DOCUMENT ID " + "\t" + "DOCUMENT NAME" + "\t" + "RANKING"+"\n");



            for (Map.Entry<Integer, Double> d : sortResults(finalScores).entrySet())
            {

               sb.append(d.getKey() + "\t\t\t\t" + IndexBuilder.allDocuments.get(d.getKey()).getFileName() +"\t\t\t" + df.format(d.getValue()) + "\n");
            }
            System.out.println(sb.toString());
            sb.setLength(0);

        }


        }







