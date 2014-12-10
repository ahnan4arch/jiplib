package net.bican.iplib.tests;

import java.util.Iterator;
import java.util.Set;

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
      final int length, final String lower, final String upper,
      final int... prefixes) {
    final Set<Range<IPAddress>> ip = IPAddresses.fromInterval(interval);
    Assert.assertNotNull(ip);
    Assert.assertEquals(length, ip.size());
    final Iterator<Range<IPAddress>> it = ip.iterator();
    for (final int prefixe : prefixes) {
      Assert.assertEquals(IPAddresses.toCIDR(it.next()).getPrefix(), prefixe);
    }
  }

  @Test
  public void test25and23() {
    MultipleIntervalTest.testFromInterval(
        Range.<IPAddress> closed(MultipleIntervalTest._192_168_0_0,
            MultipleIntervalTest._192_168_2_128), 2, "192.168.0.0",
        "192.168.2.127", 25, 23);
  }

  @Test
  public void test32and31() {
    MultipleIntervalTest.testFromInterval(Range.<IPAddress> closed(
        MultipleIntervalTest._192_168_0_1, MultipleIntervalTest._192_168_0_3),
        2, "192.168.0.0", "192.168.2.127", 32, 31);
  }
}
