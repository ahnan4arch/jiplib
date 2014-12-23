package net.bican.iplib;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

/**
 * methods for translating from and to different IP address notations
 *
 * @author Can Bican
 */
public class IPAddresses {
  static Range<IPAddress> canonical(final Range<IPAddress> range,
      final LongDiscreteDomain<IPAddress> domain) {
    if (range.isEmpty()) {
      return null;
    }
    final boolean l = range.lowerBoundType() == BoundType.OPEN;
    final boolean u = range.upperBoundType() == BoundType.OPEN;
    final IPAddress s = range.lowerEndpoint();
    final IPAddress e = range.upperEndpoint();
    if (l && u) {
      Range.closed(domain.next(s), domain.previous(e));
    } else if (l) {
      return Range.closed(domain.next(s), e);
    } else if (u) {
      return Range.closed(s, domain.previous(e));
    }
    return range;
  }
  
  private static Set<Range<IPAddress>> findOneConnected(
      final Set<Range<IPAddress>> intervals) {
    Range<IPAddress> f1 = null;
    Range<IPAddress> f2 = null;
    for (final Range<IPAddress> s : intervals) {
      for (final Range<IPAddress> s2 : intervals) {
        final Range<IPAddress> sc = IPAddresses.canonical(s, s.lowerEndpoint()
            .getDomain());
        final Range<IPAddress> sc2 = IPAddresses.canonical(s, s2
            .lowerEndpoint().getDomain());
        if ((sc.equals(sc2)) || (s.isConnected(s2))) {
          f1 = s;
          f2 = s2;
          break;
        }
      }
      if (f1 != null) {
        break;
      }
    }
    if (f1 != null) {
      final Set<Range<IPAddress>> newIntervals = new TreeSet<>(
          IPAddressRangeComparator.getComparator());
      newIntervals.addAll(intervals);
      final Range<IPAddress> f = f1.span(f2);
      newIntervals.remove(f1);
      newIntervals.remove(f2);
      newIntervals.add(f);
      return newIntervals;
    }
    return intervals;
  }
  
  /**
   * creates a range of ip addresses from CIDR notation
   *
   * @param cidr
   *          CIDR range
   * @return the IPAddress range
   */
  public static Range<IPAddress> fromCIDR(final CIDR cidr) {
    Preconditions.checkNotNull(cidr, "cidr cannot be null"); //$NON-NLS-1$
    final Range<IPAddress> range = Range
        .closed(cidr.getFirst(), cidr.getLast());
    return range;
  }
  
  private static Set<Range<IPAddress>> fromConnectedInterval(
      final Range<IPAddress> interval) {
    if (interval.isEmpty()) {
      return null;
    }
    int prefix = 0;
    final LongDiscreteDomain<IPAddress> domain = interval.lowerEndpoint()
        .getDomain();
    while (prefix <= domain.maxPrefix()) {
      final Range<IPAddress> thisRange = IPAddresses
          .canonical(interval, domain);
      final Range<IPAddress> otherRange = IPAddresses.fromCIDR(new CIDR(
          thisRange.lowerEndpoint(), prefix));
      if (thisRange.equals(otherRange)) {
        TreeSet<Range<IPAddress>> result = new TreeSet<>(
            IPAddressRangeComparator.getComparator());
        result.add(otherRange);
        return result;
      } else if (thisRange.encloses(otherRange)) {
        final Set<Range<IPAddress>> result = new TreeSet<>(
            IPAddressRangeComparator.getComparator());
        result.add(otherRange);
        Range<IPAddress> newRange1 = Range.closedOpen(
            thisRange.lowerEndpoint(), otherRange.lowerEndpoint());
        Range<IPAddress> newRange2 = Range.openClosed(
            otherRange.upperEndpoint(), thisRange.upperEndpoint());
        final Set<Range<IPAddress>> results1 = IPAddresses
            .fromConnectedInterval(newRange1);
        if (results1 != null) {
          result.addAll(results1);
        }
        final Set<Range<IPAddress>> results2 = IPAddresses
            .fromConnectedInterval(newRange2);
        if (results2 != null) {
          result.addAll(results2);
        }
        return result;
      }
      prefix++;
    }
    return new TreeSet<>(Collections.singleton(interval));
  }
  
  private static Set<Range<IPAddress>> fromConnectedInterval(
      final Set<Range<IPAddress>> intervals) {
    final Set<Range<IPAddress>> result = new TreeSet<>(
        IPAddressRangeComparator.getComparator());
    for (final Range<IPAddress> interval : intervals) {
      result.addAll(IPAddresses.fromConnectedInterval(interval));
    }
    return result;
  }
  
  /**
   * creates a set of ranges that fit into CIDR ranges from the interval of ip
   * addresses
   *
   * @param interval
   *          the address interval
   * @return the set of ranges
   */
  public static Set<Range<IPAddress>> fromInterval(
      final Range<IPAddress> interval) {
    Preconditions.checkNotNull(interval, "interval cannot be null"); //$NON-NLS-1$
    final Set<Range<IPAddress>> sourceRange = new TreeSet<>(
        IPAddressRangeComparator.getComparator());
    sourceRange.add(interval);
    return IPAddresses.fromInterval(sourceRange);
  }
  
  /**
   * creates a set of ranges that fit into CIDR ranges from the interval of ip
   * addresses
   *
   * @param intervals
   *          address intervals
   * @return the set of ranges
   */
  static Set<Range<IPAddress>> fromInterval(
      final Set<Range<IPAddress>> intervals) {
    Preconditions.checkNotNull(intervals, "intervals cannot be null"); //$NON-NLS-1$
    Preconditions.checkArgument(intervals.size() > 0,
        "intervals cannot be an empty set"); //$NON-NLS-1$
    final Set<Range<IPAddress>> intervalsCleaned = IPAddresses
        .removeEmptyIntervals(intervals);
    if (intervalsCleaned.size() > 1) {
      final Set<Range<IPAddress>> newIntervals = IPAddresses
          .findOneConnected(intervalsCleaned);
      if (intervalsCleaned.size() == newIntervals.size()) {
        return IPAddresses.fromConnectedInterval(intervalsCleaned);
      } else {
        return IPAddresses.fromInterval(newIntervals);
      }
    } else {
      return IPAddresses.fromConnectedInterval(intervalsCleaned);
    }
  }
  
  /**
   * creates a range of ip addresses from netmask notation
   *
   * @param netmask
   *          Netmask range
   * @return the IPAddress range
   */
  public static Range<IPAddress> fromNetmask(final Netmask netmask) {
    Preconditions.checkNotNull(netmask, "netmask cannot be null"); //$NON-NLS-1$
    final CIDR cidr = CIDR.fromNetmask(netmask);
    return IPAddresses.fromCIDR(cidr);
  }
  
  private static Set<Range<IPAddress>> removeEmptyIntervals(
      final Set<Range<IPAddress>> intervals) {
    final Set<Range<IPAddress>> result = new TreeSet<>(
        IPAddressRangeComparator.getComparator());
    for (final Range<IPAddress> i : intervals) {
      if (!i.isEmpty()) {
        result.add(i);
      }
    }
    return result;
  }
  
  /**
   * transforms the range to CIDR notation, if any is possible
   *
   * @param range
   *          the range to transform
   * @return transformed CIDR range
   */
  public static CIDR toCIDR(final Range<IPAddress> range) {
    Preconditions.checkNotNull(range, "range cannot be null"); //$NON-NLS-1$
    if (range.isEmpty()) {
      return null;
    }
    int prefix = 0;
    final LongDiscreteDomain<IPAddress> domain = range.lowerEndpoint()
        .getDomain();
    final Range<IPAddress> addrRange = IPAddresses.canonical(range, domain);
    while (prefix <= domain.maxPrefix()) {
      final CIDR cidr = new CIDR(addrRange.lowerEndpoint(), prefix);
      final Range<IPAddress> a = IPAddresses.fromCIDR(cidr);
      if (a.equals(addrRange)) {
        return cidr;
      }
      prefix++;
    }
    return null;
  }
}
