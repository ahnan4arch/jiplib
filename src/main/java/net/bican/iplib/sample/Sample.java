/*
 * 
 * jiplib
 * https://github.com/canbican/jiplib
 * 
 * Copyright 2015 Can Bican <can@bican.net>
 * See the file 'LICENSE' in the distribution for licensing terms.
 * 
 */
package net.bican.iplib.sample;

import java.util.Set;

import net.bican.iplib.AddressIterable;
import net.bican.iplib.CIDR;
import net.bican.iplib.IPAddress;
import net.bican.iplib.IPAddresses;
import net.bican.iplib.Netmask;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Range;
import com.google.common.net.InetAddresses;

@SuppressWarnings({ "nls", "javadoc" })
public class Sample {
  public static void main(String[] args) {
    // creating a network range by CIDR address
    CIDR cidr1 = new CIDR("10.10.10.0/30");
    Range<IPAddress> range1 = IPAddresses.fromCIDR(cidr1);
    
    // iterating a network range
    Iterables.all(new AddressIterable(range1), new Predicate<IPAddress>() {
      @Override
      public boolean apply(IPAddress input) {
        System.out.println(input);
        return true;
      }
    });
    
    // creating a network range by netmask
    Netmask netmask1 = new Netmask("10.10.20.0/255.255.255.0");
    Range<IPAddress> range2 = IPAddresses.fromNetmask(netmask1);
    System.out.println("range contains ip: "
        + range2.contains(IPAddress.getInstance(InetAddresses
            .forString("10.10.20.20"))));
    System.out.println("range contains ip: "
        + range2.contains(IPAddress.getInstance(InetAddresses
            .forString("10.10.21.20"))));
    
    // creating a network range from an arbitrary interval
    Range<IPAddress> interval = Range.closed(
        IPAddress.getInstance(InetAddresses.forString("1.0.0.1")),
        IPAddress.getInstance(InetAddresses.forString("1.0.2.22")));
    Set<Range<IPAddress>> ips = IPAddresses.fromInterval(interval);
    for (Range<IPAddress> i : ips) {
      System.out.println(i);
    }
  }
}