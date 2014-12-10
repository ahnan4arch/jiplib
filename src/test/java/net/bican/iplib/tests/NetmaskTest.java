package net.bican.iplib.tests;

import net.bican.iplib.IPAddress;
import net.bican.iplib.IPAddresses;
import net.bican.iplib.Netmask;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;

@SuppressWarnings({ "javadoc", "nls", "static-method" })
public class NetmaskTest {

  private static void testFromNetmask(final String range, final String lower,
      final String upper) {
    final Range<IPAddress> ip = IPAddresses.fromNetmask(new Netmask(range));
    Assert.assertNotNull(ip);
    Assert.assertEquals(lower, ip.lowerEndpoint().toString());
    Assert.assertEquals(upper, ip.upperEndpoint().toString());
  }

  @Test
  public void test0() {
    NetmaskTest.testFromNetmask("192.168.0.42/0.0.0.0", "0.0.0.0",
        "255.255.255.255");
  }

  @Test
  public void test20() {
    NetmaskTest.testFromNetmask("192.168.0.42/255.255.240.0", "192.168.0.0",
        "192.168.15.255");
  }

  @Test
  public void test24() {
    NetmaskTest.testFromNetmask("192.168.0.42/255.255.255.0", "192.168.0.0",
        "192.168.0.255");
  }

  @Test
  public void test28() {
    NetmaskTest.testFromNetmask("192.168.0.42/255.255.255.240", "192.168.0.32",
        "192.168.0.47");
  }

  @Test
  public void test32() {
    NetmaskTest.testFromNetmask("192.168.0.42/255.255.255.255", "192.168.0.42",
        "192.168.0.42");
  }
}
