import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.hash.TIntObjectHashMap;


import aida.util.StringUtils;
import java.io.Reader;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LSHTest {
  
  public void testStrings() throws InterruptedException, IOException {

	  executionTime timeObj = new executionTime();
	  
	  timeObj.setTimer();
	  
	    Set<String> names = new HashSet<String>();


      //try (BufferedReader br = new BufferedReader(new FileReader("testdata/preparedinput/EntitiesList_v3.txt")))
	    try (BufferedReader br = new BufferedReader(new FileReader("testdata/preparedinput/EntitiesList.txt")))
	  //try (BufferedReader br = new BufferedReader(new FileReader("testdata/preparedinput/nameList.txt")))
      {

          String sCurrentLine;

          while ((sCurrentLine = br.readLine()) != null) {        	  
        	  names.add(sCurrentLine);
          }

      } catch (IOException e) {
          e.printStackTrace();
      } 
  
      
      int ngram = 4;
      int bandsize = 3;
      int bandCount = 5;
      
//      for(int i=ngram;i<=10;i++)
//    	  for(int j=bandsize;j<=5;j++)
//    		  for(int k=bandCount;k<=10;k++)
      {
 //   			  System.out.println("******** Ngram = "+ i +" bandsize = "+j+" , and BandCount = "+k+" *************");
    LSH<String> lsh = LSH.createLSH(names, new LSHStringNgramFeatureExtractor(3), 4, 10, 2);
//      LSH<String> lsh = LSH.createLSH(names, new LSHStringNgramFeatureExtractor(i), j, 	k, 2);

    
    System.out.println("Time for LSH ");
    timeObj.getExecutionTime();
//    System.out.println(lsh.getSimilarItems());

//    while (true) {
    //  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      //System.out.print("Enter name: ");
      //String name = br.readLine();
    
    
    //write lsh object in a file
    
    FileOutputStream fout = null;
	ObjectOutputStream oos = null;

	try {

		fout = new FileOutputStream("testdata/preparedinput/lsh.ser");
		oos = new ObjectOutputStream(fout);
		oos.writeObject(lsh);

		System.out.println("Done");

	} catch (Exception ex) {

		ex.printStackTrace();

	} finally {

		if (fout != null) {
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (oos != null) {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
    
	System.out.println("Time to write in file ");
	timeObj.getExecutionTime();
	
    String name = "BARACK OBAMA SPEECH";
    Set<String> simiNames = lsh.getSimilarItemsForFeature(name);
      System.out.println("Similar names: \n" + simiNames);
      System.out.println("size = "+ simiNames.size());
  //  }
      
      
      System.out.println("Search time ");
      timeObj.getExecutionTime();
      
      }
      
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    new LSHTest().testStrings();
  }
}
