package aida.preparator.offsetmapping.realign;

import aida.data.PreparedCandidates;


public class NoopOffsetRealigner implements OffsetRealignInterface {

  @Override
  public void process(String text) {
    // do nothing.
  }

  @Override
  public PreparedCandidates reAlign(PreparedCandidates pInp) {
    return pInp;
  }

  @Override
  public int getMappedOffset(int offset) {
    return offset;
  }

}
