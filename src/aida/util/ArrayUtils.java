package aida.util;


public class ArrayUtils {

  /**
   * Returns the size of the intersection of two SORTED arrays.
   * The arrays NEED TO BE PASSED SORTED.
   * 
   * @param a First array
   * @param b Second array
   * @return  Size of intersection of a and b
   */
  public static int intersectArrays(int[] a, int[] b) {
    int intersectCount = 0;
    
    int aIndex = 0;
    int bIndex = 0;
    
    while (aIndex < a.length && bIndex < b.length) {
      if (a[aIndex] == b[bIndex]) {
        intersectCount++;
        
        aIndex++;
        bIndex++;
      } else if (a[aIndex] < b[bIndex]) {
        aIndex++;
      } else {
        bIndex++;
      }
    }
    
    return intersectCount;    
  }
}
