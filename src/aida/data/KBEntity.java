package aida.data;

public class KBEntity implements Comparable<KBEntity> {

  /** original knowledgebase identifier */
  private String identifier;

  /** knowledgbase from which entity is coming  */
  private String knowledgebase;

  
  public KBEntity(String identifier, String knowledgebase) {
    super();
    this.identifier = identifier;
    this.knowledgebase = knowledgebase;
  }
  
  public KBEntity(String knowledgebaseIdentifier) {
    super();
    String[] kbId = knowledgebaseIdentifier.split(":");
    if (kbId.length != 2) {
      throw new IllegalArgumentException(
          "knowledgebaseIdentifier needs to be of format "
          + "'KBIdentifier:EntityIdentifier'");
    }
    this.identifier = kbId[1];
    this.knowledgebase = kbId[0];
  }
  
  public String getIdentifier() {
    return identifier;
  }
  
  public String getKnowledgebase() {
    return knowledgebase;
  }
  
  @Override
  public String toString() {
    return getDictionaryKey();
  }
  
  @Override
  public boolean equals(Object obj) {
    if(obj instanceof KBEntity) {
      KBEntity entity = (KBEntity) obj; 
    return knowledgebase.equals(entity.knowledgebase) && identifier.equals(entity.identifier);
    } else {
      return false;
    }
  }
  
  @Override
  public int hashCode() {
    return knowledgebase.hashCode() * identifier.hashCode();
  }
  
  public String getDictionaryKey() {
    return knowledgebase + ":" + identifier;
  }

public int compareTo(KBEntity kbIdentifiedEntity) {
    return getDictionaryKey().compareTo(kbIdentifiedEntity.getDictionaryKey());
  }
}
