package net.bican.iplib.tests;

import net.bican.iplib.CIDR;
import net.bican.iplib.IPAddress;
import net.bican.iplib.IPAddresses;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;

@SuppressWarnings({ "javadoc", "nls", "static-method" })
public class CIDRTest {

  private static void testFromCIDR(final String range, final String lower,
      final String upper) {
    final Range<IPAddress> ip = IPAddresses.fromCIDR(new CIDR(range));
    Assert.assertNotNull(ip);
    Assert.assertEquals(lower, ip.lowerEndpoint().toString());
    Assert.assertEquals(upper, ip.upperEndpoint().toString());
  }

  @Test
  public void test0() {
    CIDRTest.testFromCIDR("192.168.0.42/0", "0.0.0.0", "255.255.255.255");
  }

  @Test
  public void test20() {
    CIDRTest.testFromCIDR("192.168.0.42/20", "192.168.0.0", "192.168.15.255");
  }

  @Test
  public void test24() {
    CIDRTest.testFromCIDR("192.168.0.42/24", "192.168.0.0", "192.168.0.255");
  }

  @Test
  public void test28() {
    CIDRTest.testFromCIDR("192.168.0.42/28", "192.168.0.32", "192.168.0.47");
  }

  @Test
  public void test32() {
    CIDRTest.testFromCIDR("192.168.0.42/32", "192.168.0.42", "192.168.0.42");
  }
}
