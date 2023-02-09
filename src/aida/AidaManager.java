package aida;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aida.tokenizer.data.TokenizerManager;

public class AidaManager {

  static {
    // Always need to do this.
    init();
  }

  private static Logger slogger_ = LoggerFactory.getLogger(AidaManager.class);

  // This is more couple to SQL than it should be. Works for now.


  public static final String WIKIPEDIA_PREFIX = "http://en.wikipedia.org/wiki/";

  
  private static Map<String, String> dbIdToConfig = new HashMap<String, String>();

  private static Map<String, Properties> dbIdToProperties = new HashMap<String, Properties>();

  static {
 
  }

  private static AidaManager tasks = null;

  public static enum language {
    english, german, french, spanish, dutch
  }

  public static void init() {
    getTasksInstance();
  }

  private static synchronized AidaManager getTasksInstance() {
    if (tasks == null) {
      tasks = new AidaManager();
    }
    return tasks;
  }  



  private AidaManager() {
    TokenizerManager.init();
  }

  /**
   * Uppercases a token of more than 4 characters. This is
   * used as pre-processing method during name recognition
   * and dictionary matching mainly.
   *
   * @param token Token to check for conflation.
   * @return  ALL-UPPERCASE token if token is longer than 4 characters.
   */
  public static String conflateToken(String token) {
    if (token.length() >= 4) {
      token = token.toUpperCase(Locale.ENGLISH);
    }

    return token;
  }

}
