package aida.preparator.offsetmapping.realign;

import aida.data.PreparedCandidates;


public interface OffsetRealignInterface {
  public void process(String text);
  public PreparedCandidates reAlign(PreparedCandidates pInp);
  public int getMappedOffset(int offset);
}
