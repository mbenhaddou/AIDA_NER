package aida.preparation.documentchunking;

import aida.data.Mentions;
import aida.data.PreparedCandidates;
import aida.tokenizer.data.Tokens;


public abstract class DocumentChunker {
  public abstract PreparedCandidates process(String docId, String text, Tokens tokens, Mentions mentions);
}
