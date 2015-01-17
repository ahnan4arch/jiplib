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

import java.util.Set;

import net.bican.iplib.IPAddress;
import net.bican.iplib.IPAddresses;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.net.InetAddresses;

@SuppressWarnings({ "javadoc", "nls", "static-method" })
public class IntervalTest {

  private static final IPAddress _0_0_0_0 = IPAddress.getInstance(InetAddresses
      .forString("0.0.0.0"));
  private static final IPAddress _192_168_0_0 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.0"));
  private static final IPAddress _192_168_0_255 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.255"));
  private static final IPAddress _192_168_0_32 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.32"));
  private static final IPAddress _192_168_0_42 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.42"));
  private static final IPAddress _192_168_0_47 = IPAddress
      .getInstance(InetAddresses.forString("192.168.0.47"));
  private static final IPAddress _192_168_15_255 = IPAddress
      .getInstance(InetAddresses.forString("192.168.15.255"));
  private static final IPAddress _255_255_255_255 = IPAddress
      .getInstance(InetAddresses.forString("255.255.255.255"));

  private static void testFromInterval(final Range<IPAddress> interval,
      final String lower, final String upper) {
    final Set<Range<IPAddress>> ip = IPAddresses.fromInterval(interval);
    Assert.assertNotNull(ip);
    Assert.assertEquals(1, ip.size());
    final Range<IPAddress> i = ip.iterator().next();
    Assert.assertEquals(lower, i.lowerEndpoint().toString());
    Assert.assertEquals(upper, i.upperEndpoint().toString());
  }

  @Test
  public void test0() {
    IntervalTest.testFromInterval(Range.<IPAddress> closed(
        IntervalTest._0_0_0_0, IntervalTest._255_255_255_255), "0.0.0.0",
        "255.255.255.255");
  }

  @Test
  public void test20() {
    IntervalTest.testFromInterval(Range.<IPAddress> closed(
        IntervalTest._192_168_0_0, IntervalTest._192_168_15_255),
        "192.168.0.0", "192.168.15.255");
  }

  @Test
  public void test24() {
    IntervalTest.testFromInterval(Range.<IPAddress> closed(
        IntervalTest._192_168_0_0, IntervalTest._192_168_0_255), "192.168.0.0",
        "192.168.0.255");
  }

  @Test
  public void test28() {
    IntervalTest.testFromInterval(Range.<IPAddress> closed(
        IntervalTest._192_168_0_32, IntervalTest._192_168_0_47),
        "192.168.0.32", "192.168.0.47");
  }

  @Test
  public void test32() {
    IntervalTest.testFromInterval(Range.<IPAddress> closed(
        IntervalTest._192_168_0_42, IntervalTest._192_168_0_42),
        "192.168.0.42", "192.168.0.42");
  }
}
