/*
 * 
 * jiplib
 * https://github.com/canbican/jiplib
 * 
 * Copyright 2015 Can Bican <can@bican.net>
 * See the file 'LICENSE' in the distribution for licensing terms.
 * 
 */
package net.bican.iplib.tests;

import java.math.BigInteger;

import net.bican.iplib.AddressIterable;
import net.bican.iplib.IPAddress;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.net.InetAddresses;

@SuppressWarnings({ "javadoc", "static-method", "nls" })
public class InetV6AddressRangeTest {
  @Test
  public void testRangesInvalid() {
    try {
      Range.closed(IPAddress.getInstance(InetAddresses
          .forString("2001:05c0:9168:0000:0000:0000:0001:0001")), IPAddress
          .getInstance(InetAddresses
              .forString("2001:05c0:9168:0000:0000:0000:0000:0001")));
      Assert.fail("invalid range passed");
      Range.closed(IPAddress.getInstance(InetAddresses
          .forString("2001:05c0:9168:0000:0000:0000:0000:0001")), IPAddress
          .getInstance(InetAddresses
              .forString("2001:05c0:9168f:0000:0000:0000:0000:0001")));
      Assert.fail("invalid ipv4 address ending passed");
    } catch (final IllegalArgumentException e) {
      // go on
    }
  }
  
  @Test
  public void testRangesValid() {
    final Range<IPAddress> r1 = Range.closed(IPAddress
        .getInstance(InetAddresses
            .forString("2001:05c0:9168:0000:0000:0000:0000:0001")), IPAddress
        .getInstance(InetAddresses
            .forString("2001:05c0:9168:0000:0000:0000:0001:0001")));
    final AddressIterable addresses1 = new AddressIterable(r1);
    Assert.assertNotNull(addresses1);
    Assert.assertEquals(BigInteger.valueOf(65537l), addresses1.size());
    final Range<IPAddress> r2 = Range.closed(IPAddress
        .getInstance(InetAddresses
            .forString("2001:05c0:9168:0000:0000:0000:0000:0001")), IPAddress
        .getInstance(InetAddresses
            .forString("2001:05c0:9168:0000:0000:0000:0000:0001")));
    final AddressIterable addresses2 = new AddressIterable(r2);
    Assert.assertNotNull(addresses2);
    Assert.assertEquals(BigInteger.ONE, addresses2.size());
    final Range<IPAddress> r3 = Range.closed(IPAddress
        .getInstance(InetAddresses
            .forString("0000:0000:0000:0000:0000:0000:0000:0000")), IPAddress
        .getInstance(InetAddresses
            .forString("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff")));
    final AddressIterable addresses3 = new AddressIterable(r3);
    Assert.assertNotNull(addresses3);
    Assert.assertEquals(new BigInteger(
        "340282366920938463463374607431768211456"), addresses3.size());
    final Range<IPAddress> r4 = Range.closed(IPAddress
        .getInstance(InetAddresses
            .forString("ffff:ffff:ffff:ffff:ffff:ffff:ffff:0000")), IPAddress
        .getInstance(InetAddresses
            .forString("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff")));
    final AddressIterable addresses4 = new AddressIterable(r4);
    Assert.assertNotNull(addresses4);
    Assert.assertEquals(new BigInteger("65536"), addresses4.size());
    final Range<IPAddress> r5 = Range.closed(IPAddress
        .getInstance(InetAddresses
            .forString("ffff:ffff:ffff:ffff:ffff:ffff:0000:0000")), IPAddress
        .getInstance(InetAddresses
            .forString("ffff:ffff:ffff:ffff:ffff:ffff:0000:000f")));
    final AddressIterable addresses5 = new AddressIterable(r5);
    Assert.assertNotNull(addresses5);
    Assert.assertEquals(new BigInteger("16"), addresses5.size());
  }
}
