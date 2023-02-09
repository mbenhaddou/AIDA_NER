

import aida.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.Serializable;
/**
 * Creates features by extracting ngrams from the given strings, then hashes them to get an int representation.
 */
public class LSHStringNgramFeatureExtractor extends LSHFeatureExtractor<String> implements Serializable{
//public class LSHStringNgramFeatureExtractor extends LSHFeatureExtractor<String>{

  private int ngramLength_;

  /**
   * Uses ngrams of length 3.
   */
  public LSHStringNgramFeatureExtractor() {
    this(3);
  }

  /**
   * Creates ngrams of the specified length.
   *
   * @param ngramLength Length of the ngram to extract.
   */
  public LSHStringNgramFeatureExtractor(int ngramLength) {
    ngramLength_ = ngramLength;
  }

  public Set<String> cleanNgram(Set<String> ngrams)
  {
	  Set<String>newNgrams = new HashSet<String>();
	  for(String s: ngrams)
		{
			if(!s.contains("_"))
			{
				//System.out.println(s);
				//ngrams.remove(s);
				newNgrams.add(s);
			}
		}
			//System.out.println(newNgrams);
	  return newNgrams;
  }
  
//  @Override
//  public int[] convert(Collection<String> features) {
//    List<Integer> featureIds = features.stream()
//            .flatMap(f -> StringUtils.getNgrams(f, ngramLength_).stream())
//            .map(String::hashCode).collect(Collectors.toList());
//    int[] fIds = new int[featureIds.size()];
//    int i = 0;
//    for (Integer f : featureIds) {
//      fIds[i++] = f;
//    }
//    return fIds;
//  }
  
  
  @Override
  public int[] convert(Collection<String> features) {
    List<Integer> featureIds = features.stream()
            .flatMap(f -> cleanNgram(StringUtils.getNgrams(f, ngramLength_)).stream())
            .map(String::hashCode).collect(Collectors.toList());
    int[] fIds = new int[featureIds.size()];
    int i = 0;
    for (Integer f : featureIds) {
      fIds[i++] = f;
    }
    return fIds;
  }
  
}
