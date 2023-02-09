package aida.preparator.inputformat;

import aida.config.PreparationSettings;
import aida.data.PreparedCandidates;


public interface PreparatorInputFormatInterface {
  /**
   * Public prepare method that needs to be overridden in the sub-classes. Returns prepared input for the given text.
   * 
   * @param docId The text to be disambiguated
   * @param text The document id associated with the text
   * @param prepSettings  Preparation settings
   * @return  PreparedInput instance.
   * @throws Exception
   */
  public PreparedCandidates prepare(String docId, String text, 
      PreparationSettings prepSettings) throws Exception;

}
