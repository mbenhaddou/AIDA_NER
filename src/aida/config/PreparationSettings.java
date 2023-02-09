package aida.config;

import java.io.Serializable;

import aida.config.AidaConfig;
import aida.data.Type;
import aida.preparation.documentchunking.DocumentChunker;
import aida.preparation.documentchunking.FixedLengthDocumentChunker;
import aida.preparation.documentchunking.PageBasedDocumentChunker;
import aida.preparation.documentchunking.SingleChunkDocumentChunker;
import aida.preparation.mentionrecognition.MentionsDetector;
import aida.tokenizer.data.Tokenizer;

/**
 * Settings for the preparator. Predefined settings are available in
 * {@see aida.config.settings.preparation}.
 */
public class PreparationSettings implements Serializable {

  private static final long serialVersionUID = -2825720730925914648L;

  private MentionsDetector.type mentionsDetectionType = MentionsDetector.type.AUTOMATIC_AND_MANUAL;
  
  private Tokenizer.type tokenizerType = Tokenizer.type.ENGLISH_TOKENS;

  /** 
   * Minimum number of mention occurrence to be considered in disambiguation.
   * Default is to consider all mentions.
   */
  private int minMentionOccurrenceCount = 1;

  private Type[] filteringTypes = AidaConfig.getFilteringTypes();
  
  //default to the language in AIDA configuration
  private LANGUAGE language = AidaConfig.getLanguage();
  
  private DOCUMENT_CHUNK_STRATEGY docChunkStrategy = AidaConfig.getDocumentChunkStrategy();
  
  // default input format is PLAIN
  private DOCUMENT_INPUT_FORMAT documentInputFormat = DOCUMENT_INPUT_FORMAT.PLAIN;

  private String encoding = "UTF-8";
  
  private String documentId;
  private String documentTitle;
  private String documentField;
  
  public static enum DOCUMENT_CHUNK_STRATEGY {
    SINGLE, PAGEBASED, MULTIPLE_FIXEDLENGTH
  }
  
  public static enum DOCUMENT_INPUT_FORMAT {
    PLAIN, XML, JSON
  }
  
  public static enum LANGUAGE {
    en, de, fr, nl, es, ar, multi
  }
  
  public MentionsDetector.type getMentionsDetectionType() {
    return mentionsDetectionType;
  }

  public void setMentionsDetectionType(MentionsDetector.type mentionsDetectionType) {
    this.mentionsDetectionType = mentionsDetectionType;
  }

  
  public Tokenizer.type getTokenizerType() {
    return tokenizerType;
  }

  
  public void setTokenizerType(Tokenizer.type tokenizerType) {
    this.tokenizerType = tokenizerType;
  }

  public Type[] getFilteringTypes() {
    return filteringTypes;
  }

  public void setFilteringTypes(Type[] filteringTypes) {
    this.filteringTypes = filteringTypes;
  }
  
  public LANGUAGE getLanguage() {
    return language;
  }
  
  public void setLanguage(LANGUAGE language) {
    this.language = language;
  }


  public int getMinMentionOccurrenceCount() {
    return minMentionOccurrenceCount;
  }

  public void setMinMentionOccurrenceCount(int minMentionOccurrenceCount) {
    this.minMentionOccurrenceCount = minMentionOccurrenceCount;
  }

  public void setDocumentInputFormat(DOCUMENT_INPUT_FORMAT docInpFormat) {
    this.documentInputFormat = docInpFormat;
  }

  public DOCUMENT_INPUT_FORMAT getDocumentInputFormat() {
    return this.documentInputFormat;
  }

  
  public DocumentChunker getDocumentChunker() {
    DocumentChunker chunker = null;
    switch (docChunkStrategy) {
      case SINGLE:
        chunker = new SingleChunkDocumentChunker();
        break;
      case PAGEBASED:
        chunker = new PageBasedDocumentChunker();
        break;
      case MULTIPLE_FIXEDLENGTH:
        chunker = new FixedLengthDocumentChunker();
        break;
    }
    return chunker;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  
  public String getDocumentId() {
    return documentId;
  }

  
  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  
  public String getDocumentTitle() {
    return documentTitle;
  }

  
  public void setDocumentTitle(String documentTitle) {
    this.documentTitle = documentTitle;
  }

  
  public String getDocumentField() {
    return documentField;
  }

  
  public void setDocumentField(String documentField) {
    this.documentField = documentField;
  }
  
  
}
