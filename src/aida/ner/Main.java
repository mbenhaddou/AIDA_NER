


package aida.ner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import aida.AidaManager;
import aida.data.Mention;
import aida.data.Mentions;
import aida.config.AidaConfig;
import aida.config.PreparationSettings;
import aida.data.PreparedCandidates;
import aida.data.ResultMention;
import aida.preparation.mentionrecognition.MentionsDetector.type;
import aida.preparator.Preparator;
import aida.tokenizer.data.Tokenizer;
import aida.tools.filehandlers.FileLines;
import aida.util.FileUtils;
import experiment.measure.PerformanceMeasure;
public class Main {

	  public static final double DEFAULT_ALPHA = 0.6;
	  public static final double DEFAULT_COH_ROBUSTNESS = 0.9;
	  public static final int DEFAULT_SIZE = 5;
	  
	public static void main(String[] args) throws Exception {  

			    AidaConfig.set("dataAccess", "testing");
			    AidaConfig.set(AidaConfig.CACHE_WORD_DATA, "false");
			    AidaConfig.set(AidaConfig.RECONCILER_PERSON_MERGE, "false");
			    AidaManager.init();
			 //   testPageKashmir();
			    TestPerformance();
}

	
	 
	  public static void testPageKashmir() throws Exception {
	    Preparator p = new Preparator();

	    String docId = "testPageKashmir1";
	    String content = "When [[Page]] played Kashmir at Knebworth, his Les Paul was uniquely tuned.";
	    PreparationSettings prepSettings = new PreparationSettings();
	    prepSettings.setTokenizerType(Tokenizer.type.ENGLISH_TOKENS);
	    prepSettings.setMentionsDetectionType(type.AUTOMATIC_AND_MANUAL);

	    PreparedCandidates preparedCandidates = p.prepareCandidates(docId, content, new PreparationSettings());

		   System.out.println(preparedCandidates.getMentions().toString());
	  }
	  

	  public static void TestPerformance() throws Exception {
			PerformanceMeasure pm = new  PerformanceMeasure();
			Preparator p = new Preparator();
			 String docId = "testText";
			  File testFile = new File("testdata/preparedinput/text.txt");
			  String content = FileUtils.getFileContent(testFile);
			  PreparationSettings prepSettings = new PreparationSettings();
			  prepSettings.setTokenizerType(Tokenizer.type.ENGLISH_TOKENS);
			  prepSettings.setMentionsDetectionType(type.AUTOMATIC);

			  PreparedCandidates preparedCandidates = p.prepareCandidates(docId, content, prepSettings);
			  int mentionsSize =preparedCandidates.getMentions().getMentions().size();
			  
			  
			  
			  //load manually tagged entities from file
			  FileLines fe = new FileLines( new File("testdata/preparedinput/entities_results.txt"));
			  Mentions manualMentions = new Mentions();
			  while(fe.hasNext())
			  {
				  String[] mentionValues=fe.next().split(",");
				  Mention m = new Mention();
				  m.setMention(mentionValues[0].split("\\[")[0]);
				  try{
				  String offset=mentionValues[3].split(":")[1];
				  m.setCharOffset(Integer.parseInt(offset.trim()));
				  m.setStartToken(Integer.parseInt(mentionValues[1].split("/")[1].trim()));
				  m.setEndToken(Integer.parseInt(mentionValues[2].split("/")[1].trim()));
				  m.setCharLength(Integer.parseInt(mentionValues[4].split(":")[1].trim()));
				  m.setId(Integer.parseInt(mentionValues[3].split(":")[1].trim()));
				  } catch(NumberFormatException e) {
				      System.out.println(e);
				}
				  manualMentions.addMention(m);
			  }
			  
		        for (Mention m: preparedCandidates.getMentions().getMentions())
		        {
		        	if(manualMentions.containsOffset(m.getCharOffset()))
		        			pm.tp++;
		        	else
		        		pm.fp++;
		        }
		        for (Mention m : manualMentions.getMentions())
		        {
		        	if(!preparedCandidates.getMentions().containsOffset(m.getCharOffset()))
		        			pm.fn++;
		        }
		            
		        System.out.println(pm.getPrecision());
		   }
	}

