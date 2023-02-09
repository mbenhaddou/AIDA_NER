package aida.data;

import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.ArrayList;
import java.util.List;

import aida.tokenizer.data.Token;
import aida.tokenizer.data.Tokens;

/**
 * Holds the input document as context representation.
 */
public class Context {

  private List<String> tokenStrings_;


  public Context(Tokens tokens) {
    List<String> ts = new ArrayList<>(tokens.size());
    for (Token token : tokens) {
      ts.add(token.getOriginal());
    }
    tokenStrings_ = new ArrayList<>(ts);
   
    
  }
  
  public List<String> getTokens() {
    return tokenStrings_;
  }
  
}
