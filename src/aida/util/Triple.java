package aida.util;

/** Represents a triple*/
public class Triple<F, S, T> extends Pair<F, S> {

  /** Holds the second component */
  public T third;

  /** Returns the second */
  public T third() {
    return third;
  }
  
  /** Constructs a Pair*/
  public Triple(F first, S second, T third) {
    super(first, second);
    this.third = third;
  }

  @Override
public int hashCode() {
    return (super.hashCode() ^ third.hashCode());
  }

  @Override
public boolean equals(Object obj) {
    return (obj instanceof Triple) && ((Triple<?,?,?>) obj).first().equals(first) && ((Triple<?,?,?>) obj).second().equals(second)
        && ((Triple<?,?,?>) obj).third().equals(third);
  }

  /** Returns "first/second"*/
  @Override
public String toString() {
    return first + "/" + second +"/" +third;
  }
}
