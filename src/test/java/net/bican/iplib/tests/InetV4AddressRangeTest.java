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

@SuppressWarnings({ "javadoc", "static-method", "nls" })
public class InetV4AddressRangeTest {
  private static final IPAddress _1_2_3_4 = IPAddress.getInstance(InetAddresses
      .forString("1.2.3.4"));
  private static final IPAddress _1_2_4_4 = IPAddress.getInstance(InetAddresses
      .forString("1.2.4.4"));
  private static LongDiscreteDomain<IPAddress> domain = IPV4AddressRange
      .addresses();

  @Test
  public void testRangesInvalid() {
    try {
      Range.closed(InetV4AddressRangeTest._1_2_4_4,
          InetV4AddressRangeTest._1_2_3_4);
      Assert.fail("invalid range passed");
      Range.closed(InetV4AddressRangeTest._1_2_4_4,
          IPAddress.getInstance(InetAddresses.forString("256.0.0.0")));
      Assert.fail("invalid ipv4 address ending passed");
    } catch (final IllegalArgumentException e) {
      // go on
    }
  }

  @Test
  public void testRangesValid() {
    final Range<IPAddress> r1 = Range.closed(InetV4AddressRangeTest._1_2_3_4,
        InetV4AddressRangeTest._1_2_4_4);
    final AddressIterable<IPAddress> addresses1 = new AddressIterable<>(r1,
        InetV4AddressRangeTest.domain);
    Assert.assertNotNull(addresses1);
    Assert.assertEquals(BigInteger.valueOf(257l), addresses1.size());
    final Range<IPAddress> r2 = Range.closed(InetV4AddressRangeTest._1_2_3_4,
        InetV4AddressRangeTest._1_2_3_4);
    final AddressIterable<IPAddress> addresses2 = new AddressIterable<>(r2,
        InetV4AddressRangeTest.domain);
    Assert.assertNotNull(addresses2);
    Assert.assertEquals(BigInteger.ONE, addresses2.size());
    final Range<IPAddress> r3 = Range.closed(
        IPAddress.getInstance(InetAddresses.forString("0.0.0.0")),
        IPAddress.getInstance(InetAddresses.forString("255.255.255.255")));
    final AddressIterable<IPAddress> addresses3 = new AddressIterable<>(r3,
        InetV4AddressRangeTest.domain);
    Assert.assertNotNull(addresses3);
    Assert.assertEquals(BigInteger.valueOf(4294967296l), addresses3.size());
  }
}
