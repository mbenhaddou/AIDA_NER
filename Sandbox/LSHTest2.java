

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.hash.TIntObjectHashMap;


import aida.util.StringUtils;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import info.debatty.java.lsh.LSHMinHash;

public class LSHTest2 {
  
  public void testStrings() throws InterruptedException, IOException {

	    Set<String> names = new HashSet<String>();


      try (BufferedReader br = new BufferedReader(new FileReader("testdata/preparedinput/EntitiesList.txt")))
      {

          String sCurrentLine;

          while ((sCurrentLine = br.readLine()) != null) {        	  
        	  names.add(sCurrentLine);
          }

      } catch (IOException e) {
          e.printStackTrace();
      } 
  
      
    LSH<String> lsh = LSH.createLSH(names, new LSHStringNgramFeatureExtractor(3), 4, 10, 2);

//    System.out.println(lsh.getSimilarItems());

    while (true) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Enter name: ");
      String name = br.readLine();
      System.out.println("Similar names: \n" + lsh.getSimilarItemsForFeature(name));
    }
  }

  public static <T,F> LSH<T>
  createLSH(Map<T,Collection<F>> itemFeatures, LSHFeatureExtractor<F> featureExtractor,
            int bandSize, int bandCount, int threadCount) throws InterruptedException {
    // Transform features to int arrays.
    Map<T, int[]> itemFeatureIds = new HashMap<>(itemFeatures.size());

    for (Entry<T, Collection<F>> entry : itemFeatures.entrySet()) {
      T item = entry.getKey();
      Collection<F> features = entry.getValue();
      int[] featureIds = featureExtractor.convert(features);
      itemFeatureIds.put(item, featureIds);
    }
    LSHMinHash lsh = new LSHMinHash(bandCount, bandSize, threadCount);

    MinHasher<T> minHasher = new MinHasher<>(bandSize * bandCount, threadCount);
    Map<T, int[]> itemMinhashes = minHasher.createSignatures(itemFeatureIds);
    return new info.debatty.java.lsh.LSH(itemMinhashes, featureExtractor, bandSize, bandCount);
  }
  
  
  public int[] GetNgramFeatures(Collection<String> features, int ngramLength) {
	    List<Integer> featureIds = features.stream()
	            .flatMap(f -> StringUtils.getNgrams(f, ngramLength).stream())
	            .map(String::hashCode).collect(Collectors.toList());
	    int[] fIds = new int[featureIds.size()];
	    int i = 0;
	    for (Integer f : featureIds) {
	      fIds[i++] = f;
	    }
	    return fIds;
	  }
  
  public static void main(String[] args) throws InterruptedException, IOException {
    new LSHTest2().testStrings();
  }
}
