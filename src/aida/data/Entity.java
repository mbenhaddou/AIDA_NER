package aida.data;

import java.io.Serializable;
import java.util.List;

import aida.tools.parsers.Char;

public class Entity implements Serializable, Comparable<Entity>, Cloneable {

 
  private KBEntity kbEntity;
  
  private List<String> surroundingMentionNames;
  
  private int internalId = 0;


  
  /**
   * Use this field to represent the mention-entity similarity computed with 
   * some method (not the score stored in the DB). This field will not be set 
   * in the constructor. We set it later on, when we compute the similarity.
   */
  private double similarityMentionEntity;

  public Entity(String entityId, String knowledgebase, int internalId) {
    this.kbEntity = new KBEntity(entityId, knowledgebase);
    this.similarityMentionEntity = -1.0;
    this.internalId = internalId;
  }

  public Entity(KBEntity entity, int internalId) {
    this(entity.getIdentifier(), entity.getKnowledgebase(), internalId);
  }

  @Override
public String toString() {
    return kbEntity + " (" + internalId + ")";
  }

  public int getId() {
    return internalId;
  }

  public double getMentionEntitySimilarity() {
    return this.similarityMentionEntity;
  }

  public void setMentionEntitySimilarity(double mes) {
    this.similarityMentionEntity = mes;
  }

public int compareTo(Entity e) {
    return getKbIdentifiedEntity().compareTo(e.getKbIdentifiedEntity());
  }
  
  @Override
public boolean equals(Object o) {
    if (o instanceof Entity) {
      Entity e = (Entity) o;
      return internalId == e.getId();
    } else {
      return false;
    }
  }
  
  @Override
public int hashCode() {
    return internalId;
  }


  public List<String> getSurroundingMentionNames() {
    return surroundingMentionNames;
  }

  public void setSurroundingMentionNames(List<String> surroundingMentionNames) {
    this.surroundingMentionNames = surroundingMentionNames;
  }

  public String getKnowledgebase() {
    return kbEntity.getKnowledgebase();
  }
  
  public String getIdentifierInKb() {
    return kbEntity.getIdentifier();
  }
  
  public KBEntity getKbIdentifiedEntity() {
    return kbEntity;
  }
}
