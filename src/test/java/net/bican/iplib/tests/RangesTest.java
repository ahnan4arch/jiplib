package net.bican.iplib.tests;

import java.math.BigInteger;

import net.bican.iplib.AddressIterable;
import net.bican.iplib.IPAddress;
import net.bican.iplib.IPV4AddressRange;
import net.bican.iplib.LongDiscreteDomain;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.net.InetAddresses;

@SuppressWarnings({ "javadoc", "nls", "static-method" })
public class RangesTest {
  private static LongDiscreteDomain<IPAddress> domain = IPV4AddressRange
      .addresses();
  private static final IPAddress end = IPAddress.getInstance(InetAddresses
      .forString("255.255.255.255"));
  private static final IPAddress start1 = IPAddress.getInstance(InetAddresses
      .forString("255.255.255.254"));

  @Test
  public void testCC() {
    final Range<IPAddress> r1 = Range.closed(RangesTest.start1, RangesTest.end);
    final AddressIterable<IPAddress> addresses1 = new AddressIterable<>(r1,
        RangesTest.domain);
    Assert.assertNotNull(addresses1);
    Assert.assertEquals(BigInteger.valueOf(2l), addresses1.size());
  }

  @Test
  public void testCO() {
    final Range<IPAddress> r1 = Range.closedOpen(RangesTest.start1,
        RangesTest.end);
    final AddressIterable<IPAddress> addresses1 = new AddressIterable<>(r1,
        RangesTest.domain);
    Assert.assertNotNull(addresses1);
    Assert.assertEquals(BigInteger.valueOf(1l), addresses1.size());
  }

}
