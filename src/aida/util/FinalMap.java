package aida.util;
import java.util.TreeMap;


public class FinalMap<T1 extends Comparable,T2> extends TreeMap<T1,T2>{
	private static final long serialVersionUID = 1L;

/** Constructs a FinalMap from an array that contains key/value sequences */  
  @SuppressWarnings("unchecked")
  public FinalMap(Object... a) {
    super();    
    for(int i=0;i<a.length-1;i+=2) {
      if(containsKey(a[i])) throw new RuntimeException("Duplicate key in FinalMap: "+a[i]);
      put((T1)a[i],(T2)a[i+1]);
    }
  }
  
  /** Test routine */
  public static void main(String[] args) {
    FinalMap<String,Integer> f=new FinalMap<String,Integer>("a",1,"b",2);
    System.out.println(f.get("b"));
  }
}
