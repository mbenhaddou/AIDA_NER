package aida.preparator.inputformat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import aida.config.PreparationSettings;
import aida.data.PreparedCandidates;
import aida.data.Type;
import aida.preparator.Preparator;
import aida.util.timing.RunningTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainPreparatorInputFormat implements PreparatorInputFormatInterface {
  private final Logger logger_ = LoggerFactory.getLogger(PlainPreparatorInputFormat.class);

  private Integer processedDocuments = 0;

  private final Map<String, Integer> processedDocuments_ =
      new HashMap<String, Integer>();

  /**
   * Convenience wrapper around the prepare() method, creating a docId
   * internally.
   * 
   * @param text  Text to prepare, i.e. tokenize and recognize mentions.
   * @param settings  Settings to use for the preparation.
   * @return  Text prepared for use with the Disambiguator.
   */
  public PreparedCandidates prepare(
      String text, PreparationSettings settings) {
    String docId = null;
    synchronized(processedDocuments) {
      ++processedDocuments;
      docId = String.valueOf(processedDocuments);
    }
    return prepare(docId, text, settings);
  }

  /**
   * Prepare an input text to be disambiguated. Mentions of named entities 
   * will be detected depending on the passed PreparationSettings.
   * 
   * @param text Distinct id for this document (must not be re-used)
   * @param docId  Text to prepare, i.e. tokenize and recognize mentions.
   * @param settings  Settings to use for the preparation.
   * @return  Text prepared for use with the Disambiguator.
   */

  /**
   * Prepare an input text to be disambiguated. Mentions of named entities 
   * will be detected depending on the passed PreparationSettings.
   * Word to ID mappings respect the external context.
   * 
   * @param docId Distinct id for this document (must not be re-used)
   * @param text  Text to prepare, i.e. tokenize and recognize mentions.
   * @param settings  Settings to use for the preparation.
   * @param externalContext External context to use for mapping words to ids.
   * @return  Text prepared for use with the Disambiguator.
   */
  @Override
  public PreparedCandidates prepare(
      String docId, String text, PreparationSettings settings) {
    Integer timerId = RunningTimer.recordStartTime("Preparator");
    Integer hash = text.hashCode();
    Integer processedHash = processedDocuments_.get(docId);
    if (processedHash != null) {
      if (hash != processedHash) {
        logger_.error("A document with the id '" + docId + "' has already " +
            "been processed, but the content has changed. Make sure to use " +
            "distinct docId parameters for distinct documents, otherwise " +
            "the disambiguation will not work properly!");
        throw new IllegalArgumentException("Same docId used for distinct " +
            "documents.");
      }
    } else {
      processedDocuments_.put(docId, hash);
    }

    PreparedCandidates preparedInput = Preparator.prepareInputData(text, docId, settings);

    Type[] types = settings.getFilteringTypes();
    if (types != null) {
      logger_.info("Entity Types Filter Set!");
      Set<Type> filteringTypes = new HashSet<Type>(Arrays.asList(settings.getFilteringTypes()));
      preparedInput.setMentionEntitiesTypes(filteringTypes);
    }
    RunningTimer.recordEndTime("Preparator", timerId);
    return preparedInput;
  }

}
