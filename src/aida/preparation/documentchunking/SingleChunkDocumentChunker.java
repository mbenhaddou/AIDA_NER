package aida.preparation.documentchunking;

import java.util.ArrayList;
import java.util.List;

import aida.data.Mentions;
import aida.data.PreparedCandidates;
import aida.data.PreparedInputChunk;
import aida.tokenizer.data.Tokens;

public class SingleChunkDocumentChunker extends DocumentChunker {

  @Override
  public PreparedCandidates process(String docId, String text, Tokens tokens, Mentions mentions) {
     String chunkId = docId + "_singlechunk";
     PreparedInputChunk singleChunk = 
         new PreparedInputChunk(chunkId, tokens, mentions);
          
     List<PreparedInputChunk> chunks = new ArrayList<PreparedInputChunk>(1);
     chunks.add(singleChunk);
     PreparedCandidates preparedInput = new PreparedCandidates(docId, text, chunks);
     return preparedInput;
  }

  
  @Override
  public String toString() {
    return "SingleChunk";
  }
}
