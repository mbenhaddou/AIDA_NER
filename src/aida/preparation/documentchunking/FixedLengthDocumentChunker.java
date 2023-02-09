package aida.preparation.documentchunking;

import java.util.ArrayList;
import java.util.List;

import aida.config.AidaConfig;
import aida.data.Mentions;
import aida.data.PreparedCandidates;
import aida.data.PreparedInputChunk;
import aida.util.timing.RunningTimer;
import aida.tokenizer.data.Token;
import aida.tokenizer.data.Tokens;


public class FixedLengthDocumentChunker extends DocumentChunker {

  private int maxSentencesPerChunk;
  
  public FixedLengthDocumentChunker() {
    maxSentencesPerChunk = AidaConfig.getFixedChunkSize();
  }
  
  public FixedLengthDocumentChunker(int fixedLength) {
    maxSentencesPerChunk = fixedLength;
  }
  
  @Override
  public PreparedCandidates process(String docId, String content, Tokens tokens, Mentions mentions) {
    
    Integer runId = RunningTimer.recordStartTime("FixedLengthChunker");
    List<PreparedInputChunk> chunks = new ArrayList<PreparedInputChunk>(1);
    // 0-based sentence id returned by Stanford NLP
    int processedSentence = -1;
    int totalProcessedChunk = 0;
    PreparedInputChunk singleChunk = null;
    String chunkId = null;
    Tokens currentChunkTokens = new Tokens();
    Mentions currentChunkMentions = new Mentions();
    
    for(Token token : tokens.getTokens()) {
      int currentTokenSentence = token.getSentence();
      if(currentTokenSentence != processedSentence) {
        if(processedSentence != -1 && currentTokenSentence != 0 && (currentTokenSentence % maxSentencesPerChunk) == 0) {
          chunkId = docId + "_" + (totalProcessedChunk++);
          singleChunk = new PreparedInputChunk(chunkId, currentChunkTokens, currentChunkMentions);
          chunks.add(singleChunk);
          currentChunkTokens = new Tokens();
          currentChunkMentions = new Mentions();
        }
        processedSentence = currentTokenSentence;
      }
            
      currentChunkTokens.addToken(token);
      if(mentions.containsOffset(token.getBeginIndex())){
        currentChunkMentions.addMention(mentions.getMentionForOffset(token.getBeginIndex()));
      }      
    }
    
    // Need to add the last page processed to chunk list
    chunkId = docId + "_" + totalProcessedChunk;
    singleChunk = 
        new PreparedInputChunk(chunkId, currentChunkTokens, currentChunkMentions);
      chunks.add(singleChunk);
    
    PreparedCandidates preparedInput = new PreparedCandidates(docId, content, chunks);
    RunningTimer.recordEndTime("FixedLengthChunker", runId);
    return preparedInput;
  }

  @Override
  public String toString() {
    return "MultiChunk";
  }
}
