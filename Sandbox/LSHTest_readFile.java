import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Set;

public class LSHTest_readFile {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		executionTime timeObj = new executionTime();
		timeObj.setTimer();
		FileInputStream fi = new FileInputStream(new File("testdata/preparedinput/lsh.ser"));
		ObjectInputStream oi = new ObjectInputStream(fi);
		
		LSH lsh = (LSH) oi.readObject();
		System.out.println("Reading time is ");
		timeObj.getExecutionTime();

		String name = "BARACK OBAMA SPEECH";
	    Set<String> simiNames = lsh.getSimilarItemsForFeature(name);
	      System.out.println("Similar names: \n" + simiNames);
	      System.out.println("size = "+ simiNames.size());
	      
	      System.out.println("Query result time is ");
	      timeObj.getExecutionTime();
	      
	}

}
