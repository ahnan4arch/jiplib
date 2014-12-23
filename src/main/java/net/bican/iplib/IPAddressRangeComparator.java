package net.bican.iplib;

import java.util.Comparator;

import com.google.common.collect.Range;

class IPAddressRangeComparator implements Comparator<Range<IPAddress>> {
  
  private final static IPAddressRangeComparator instance = new IPAddressRangeComparator();
  
  @Override
  public int compare(Range<IPAddress> o1, Range<IPAddress> o2) {
    if (o1.equals(o2)) {
      return 0;
    }
    Range<IPAddress> oo1 = IPAddresses.canonical(o1, o1.lowerEndpoint()
        .getDomain());
    Range<IPAddress> oo2 = IPAddresses.canonical(o2, o2.lowerEndpoint()
        .getDomain());
    return oo1.lowerEndpoint().compareTo(oo2.lowerEndpoint());
  }
  
  private IPAddressRangeComparator() {
    // forbidden
  }
  
  public static Comparator<Range<IPAddress>> getComparator() {
    return instance;
  }
  
}
