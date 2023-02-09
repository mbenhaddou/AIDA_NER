package aida.preparator;

import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.ArrayList;
import java.util.List;

import aida.config.PreparationSettings;
import aida.config.PreparationSettings.DOCUMENT_INPUT_FORMAT;
import aida.data.Mention;
import aida.data.Mentions;
import aida.data.PreparedCandidates;
import aida.preparation.documentchunking.DocumentChunker;
import aida.preparation.mentionrecognition.MentionDetectionResults;
import aida.preparation.mentionrecognition.MentionsDetector;
import aida.preparator.inputformat.PlainPreparatorInputFormat;
import aida.preparator.inputformat.PreparatorInputFormatInterface;
import aida.preparator.inputformat.xml.*;
import aida.util.normalization.TextNormalizerManager;
import aida.tokenizer.data.Tokens;
import aida.util.Pair;
import  org.apache.commons.lang.NotImplementedException;

public class Preparator {

  private PreparatorInputFormatInterface inpFormatPreparator;

  private static MentionsDetector filterMention = new MentionsDetector();

  /**
   * Returns prepared input for the given text.
   * 
   * @param content The text to be disambiguated
   * @param prepSettings  Preparation settings
   * @return  PreparedInput instance.
   * @throws Exception
   */
  public PreparedCandidates prepareCandidates(String content, PreparationSettings prepSettings) throws Exception {
    int signature = content.substring(0, Math.min(content.length(), 100)).hashCode();
    return prepareCandidates(signature + "_" + System.currentTimeMillis(), content, prepSettings);
  }



  /**
   * Returns prepared input for the given text.
   * 
   * @param content The text to be disambiguated
   * @param docId The document id associated with the text
   * @param prepSettings  Preparation settings
   * @param externalContext External Entities Context
   * @return  PreparedInput instance.
   * @throws Exception
   */
  public PreparedCandidates prepareCandidates(String docId, String content, PreparationSettings prepSettings) throws Exception {
    content = TextNormalizerManager.normalize(content);
    DOCUMENT_INPUT_FORMAT docInpFormat = prepSettings.getDocumentInputFormat();
    switch (docInpFormat) {
      case XML:
       throw  new NotImplementedException("Not implemented Yet");
      case JSON:
    	  throw  new NotImplementedException("Not implemented yet");
      default:
        inpFormatPreparator = new PlainPreparatorInputFormat();
        break;
    }

    return inpFormatPreparator.prepare(docId, content, prepSettings);
  }


  public static PreparedCandidates prepareInputData(
      String text, String docId, 
      PreparationSettings settings) {
    text = TextNormalizerManager.normalize(text);
    MentionDetectionResults mdr = filterMention.detectMention(docId, text,
        settings.getTokenizerType(), settings.getMentionsDetectionType(), settings.getLanguage());

    // Drop mentions below min occurrence count.
    if (settings.getMinMentionOccurrenceCount() > 1) {
      dropMentionsBelowOccurrenceCount(mdr.getMentions(), settings.getMinMentionOccurrenceCount());
    }

    DocumentChunker chunker = settings.getDocumentChunker();

    Tokens tokens = mdr.getTokens();

    Mentions mentions = mdr.getMentions();

    PreparedCandidates preparedInput = 
        chunker.process(docId, mdr.getText(), tokens, mentions);

    return preparedInput;
  }

  private static void dropMentionsBelowOccurrenceCount(Mentions docMentions,
      int minMentionOccurrenceCount) {
    TObjectIntHashMap<String> mentionCounts = new TObjectIntHashMap<String>();
    for (Mention m : docMentions.getMentions()) {
      mentionCounts.adjustOrPutValue(m.getMention(), 1, 1);
    }
    List<Mention> mentionsToRemove = new ArrayList<Mention>();
    for (Mention m : docMentions.getMentions()) {
      if (mentionCounts.get(m.getMention()) < minMentionOccurrenceCount) {
        mentionsToRemove.add(m);
      }
    }
    for (Mention m : mentionsToRemove) {
      docMentions.remove(m);
    }
  }
}
