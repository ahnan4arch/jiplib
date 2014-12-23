package net.bican.iplib.tests;

import static org.junit.Assert.*;

import net.bican.iplib.AddressIterable;
import net.bican.iplib.IPAddress;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.net.InetAddresses;

@SuppressWarnings({ "javadoc", "nls", "static-method" })
public class AddressIterableTest {
  private IPAddress lower = IPAddress.getInstance(InetAddresses
      .forString("1.2.3.4"));
  private IPAddress lowerplus1 = IPAddress.getInstance(InetAddresses
      .forString("1.2.3.5"));
  private IPAddress upper = IPAddress.getInstance(InetAddresses
      .forString("1.2.4.4"));
  private IPAddress upperminus1 = IPAddress.getInstance(InetAddresses
      .forString("1.2.4.3"));
  
  @Test
  public void testCC() {
    Range<IPAddress> range = Range.closed(this.lower, this.upper);
    testRange(range, this.lower, this.upper);
  }
  
  private void testRange(Range<IPAddress> r, IPAddress low, IPAddress up) {
    AddressIterable it = new AddressIterable(r);
    assertNotNull(it);
    assertTrue(it.hasNext());
    assertEquals(low, it.next());
    IPAddress item = null;
    while (it.hasNext()) {
      item = it.next();
    }
    assertNotNull(item);
    assertEquals(up, item);
  }
  
  @Test
  public void testOC() {
    Range<IPAddress> range = Range.openClosed(this.lower, this.upper);
    testRange(range, this.lowerplus1, this.upper);
  }
  
  @Test
  public void testCO() {
    Range<IPAddress> range = Range.closedOpen(this.lower, this.upper);
    testRange(range, this.lower, this.upperminus1);
  }
  
  @Test
  public void testOO() {
    Range<IPAddress> range = Range.open(this.lower, this.upper);
    testRange(range, this.lowerplus1, this.upperminus1);
  }
  
}
