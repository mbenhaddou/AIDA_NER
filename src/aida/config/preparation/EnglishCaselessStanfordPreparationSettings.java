package aida.config.preparation;

import aida.config.PreparationSettings;
import aida.tokenizer.data.Tokenizer;

/**
 * Uses the caseless models of Stanford NLP tools (used for e.g. tweets)
 *
 */
public class EnglishCaselessStanfordPreparationSettings extends PreparationSettings {
  private static final long serialVersionUID = -6235813561267960131L;
  
  public EnglishCaselessStanfordPreparationSettings() {
    this.setTokenizerType(Tokenizer.type.ENGLISH_CASELESS_TOKENS);
  }
  
  
}
