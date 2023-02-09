package aida.data;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityMetaData implements Serializable {
  
  private static Logger logger = LoggerFactory.getLogger(EntityMetaData.class);
  
  private static final long serialVersionUID = -5254220574529910760L;

  private int id;

  private String url;
  
  private String knowledgebase;
  
  private String imageUrl;
  
  private String description;
  
  public EntityMetaData(int id, String url, 
      String knowledgebase, String depictionurl, String description) {
    super();
    this.id = id;
    this.url = url;
    this.knowledgebase = knowledgebase;
    this.imageUrl = depictionurl;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  
  public String getKnowledgebase() {
    return knowledgebase;
  }

  
  public void setKnowledgebase(String knowledgebase) {
    this.knowledgebase = knowledgebase;
  }

  
  public String getDepictionurl() {
    return imageUrl;
  }

  
  public void setDepictionurl(String depictionurl) {
    this.imageUrl = depictionurl;
  }

  public String getDepictionthumbnailurl() {
    return getDepictionthumbnailurl(200);
  }
  
  
  
  
  public String getDescription() {
    return description;
  }

  
  public void setDescription(String description) {
    this.description = description;
  }

  public String getDepictionthumbnailurl(int widthInPixels) {
    if (imageUrl == null) {
      return null;
    }
    String thumbnailUrl = imageUrl;
    
    int insertIndex = -1; 
    if (thumbnailUrl.contains("/commons")) {
      insertIndex = imageUrl.indexOf("/commons") + "/commons".length();
    } else if (thumbnailUrl.contains("/en")) {
      insertIndex = imageUrl.indexOf("/en") + "/en".length();
    }
    
    if (insertIndex != -1) {
      thumbnailUrl = imageUrl.substring(0, insertIndex);
      thumbnailUrl += "/thumb";
      thumbnailUrl += imageUrl.substring(insertIndex + "/thumb".length());
      
      // Add the last part twice
      String imageName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
      thumbnailUrl += "/" + widthInPixels + "px-" + imageName;
      return thumbnailUrl;
    } else {
      // URL does not conform to expected schema.
      logger.warn("DepictionUrl does not conform to expected schema: '" + 
          imageUrl + "'.");
      return null;
    }
  }
}
