package aida.preparation.documentchunking;

import java.util.ArrayList;
import java.util.List;

import aida.data.Mentions;
import aida.data.PreparedCandidates;
import aida.data.PreparedInputChunk;
import aida.tokenizer.data.Token;
import aida.tokenizer.data.Tokens;

public class PageBasedDocumentChunker extends DocumentChunker {

  @Override
  public PreparedCandidates process(String docId, String text, Tokens tokens, Mentions mentions) {
    List<PreparedInputChunk> chunks = new ArrayList<PreparedInputChunk>(1);
    Tokens pageTokens = null;
    Mentions pageMentions = null;
    int prevPageNum = -1;

    PreparedInputChunk singleChunk = null;
    String chunkId = null;
    for(Token token : tokens.getTokens()){
      int pageNumber = token.getPageNumber();
      if(prevPageNum != pageNumber){
        if(prevPageNum != -1){
          chunkId = docId + "_" + prevPageNum;
          singleChunk = 
            new PreparedInputChunk(chunkId, pageTokens, pageMentions);
          chunks.add(singleChunk);
        }        
        pageTokens = new Tokens();
        pageTokens.setPageNumber(pageNumber);
        pageMentions = new Mentions();                
      }
            
      pageTokens.addToken(token);
      if(mentions.containsOffset(token.getBeginIndex())){
        pageMentions.addMention(mentions.getMentionForOffset(token.getBeginIndex()));        
      }
      prevPageNum = pageNumber;
    }
    
    // Need to add the last page processed to chunk list
    chunkId = docId + "_" + prevPageNum;
    singleChunk = 
        new PreparedInputChunk(chunkId, pageTokens, pageMentions);
      chunks.add(singleChunk);
    
    PreparedCandidates preparedInput = new PreparedCandidates(docId, text, chunks);
    return preparedInput;
  }
}
