package net.bican.iplib.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import net.bican.iplib.CIDR;
import net.bican.iplib.IPAddress;
import net.bican.iplib.IPAddresses;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.net.InetAddresses;

@SuppressWarnings({ "nls", "javadoc" })
public class SpeedTest {
  
  @Test
  public void testv4speed() {
    testip("inetnums");
  }
  
  @Test
  public void testv6speed() {
    testip("inet6nums");
  }
  
  private void testip(String file) {
    Date d1 = new Date();
    int tick = 0;
    try (FileReader fr = new FileReader(new File(this.getClass()
        .getResource(file).toURI()));
        BufferedReader br = new BufferedReader(fr)) {
      for (String line; (line = br.readLine()) != null;) {
        if (line.contains("/")) {
          IPAddresses.fromCIDR(new CIDR(line));
        } else {
          String[] split = line.split("-");
          IPAddress lower = IPAddress.getInstance(InetAddresses
              .forString(split[0]));
          IPAddress upper = IPAddress.getInstance(InetAddresses
              .forString(split[1]));
          IPAddresses.fromInterval(Range.<IPAddress> closed(lower, upper));
        }
        tick++;
        if (tick == 260000) {
          break;
        }
      }
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    Date d2 = new Date();
    long diff = (d2.getTime() - d1.getTime());
    System.out.println(String.format(
        "it took %s seconds to process %s %s: %s %s/s", diff / 1000.0, tick,
        file, tick / (diff / 1000.0), file));
  }
  
}
