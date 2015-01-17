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

import java.util.Iterator;
import java.util.Set;

import net.bican.iplib.CIDR;
import net.bican.iplib.IPAddress;
import net.bican.iplib.IPAddresses;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.net.InetAddresses;

@SuppressWarnings({ "javadoc", "nls", "static-method" })
public class MultipleIntervalTest {
  
  private static final IPAddress _192_168_0_0 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.0"));
  private static final IPAddress _192_168_0_1 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.1"));
  private static final IPAddress _192_168_0_3 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.3"));
  private static final IPAddress _192_168_2_128 = IPAddress
      .getInstance(InetAddresses.forString("192.168.2.127"));
  
  private static void testFromInterval(final Range<IPAddress> interval,
      final int length, final int... prefixes) {
    final Set<Range<IPAddress>> ip = IPAddresses.fromInterval(interval);
    testI(ip, length, prefixes);
  }
  
  private static void testI(Set<Range<IPAddress>> is, final int length,
      final int... prefixes) {
    Assert.assertNotNull(is);
    Assert.assertEquals(length, is.size());
    final Iterator<Range<IPAddress>> it = is.iterator();
    for (final int prefix : prefixes) {
      CIDR cidr = IPAddresses.toCIDR(it.next());
      Assert.assertEquals(cidr.getPrefix(), prefix);
    }
    if (it.hasNext()) {
      Assert.fail("there are still networks not compared");
    }
  }
  
  @Test
  public void testArbitrary0() {
    MultipleIntervalTest.testFromInterval(Range.closed(
        IPAddress.getInstance(InetAddresses.forString("1.0.0.1")),
        IPAddress.getInstance(InetAddresses.forString("1.0.2.22"))), 13, 32,
        31, 30, 29, 28, 27, 26, 25, 24, 28, 30, 31, 32);
  }
  
  @Test
  public void test25and23() {
    MultipleIntervalTest.testFromInterval(
        Range.<IPAddress> closed(MultipleIntervalTest._192_168_0_0,
            MultipleIntervalTest._192_168_2_128), 2, 23, 25);
  }
  
  @Test
  public void test32and31() {
    MultipleIntervalTest.testFromInterval(Range.<IPAddress> closed(
        MultipleIntervalTest._192_168_0_1, MultipleIntervalTest._192_168_0_3),
        2, 32, 31);
  }
}
