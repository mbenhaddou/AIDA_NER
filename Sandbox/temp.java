import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aida.util.StringUtils;

public class temp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String f="Niraj sh";
		Set<String>ngrams = StringUtils.getNgrams(f, 3);
		System.out.println(ngrams);
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
			System.out.println(newNgrams);
	}

}
