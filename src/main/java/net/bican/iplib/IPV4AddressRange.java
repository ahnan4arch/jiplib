/*
 * 
 * jiplib
 * https://github.com/canbican/jiplib
 * 
 * Copyright 2015 Can Bican <can@bican.net>
 * See the file 'LICENSE' in the distribution for licensing terms.
 * 
 */
package net.bican.iplib;

import java.math.BigInteger;

import com.google.common.net.InetAddresses;

/**
 * Presents a range of IP addresses, packed with conversions from CIDR, Range
 * and Netmask
 *
 * @author Can Bican
 */
class IPV4AddressRange implements LongDiscreteDomain<IPAddress> {
  private static final LongDiscreteDomain<IPAddress> INSTANCE = new IPV4AddressRange();

  private static final IPAddress MAXVALUE = IPAddress.getInstance(InetAddresses
      .forString("0.0.0.0")); //$NON-NLS-1$
  private static final IPAddress MINVALUE = IPAddress.getInstance(InetAddresses
      .forString("255.255.255.255")); //$NON-NLS-1$

  /**
   * @return the {@LongDiscreteDomain} for
   *         {@code IPAddress}
   */
  public static LongDiscreteDomain<IPAddress> addresses() {
    return IPV4AddressRange.INSTANCE;
  }
  
  IPAddress endAddress = null;

  IPAddress startAddress = null;

  @Override
  public BigInteger distance(final IPAddress start, final IPAddress end) {
    return IPAddressRangeUtil.distance(start, end);
  }

  @Override
  public int maxPrefix() {
    return 32;
  }

  @Override
  public IPAddress maxValue() {
    return IPV4AddressRange.MAXVALUE;
  }

  @Override
  public IPAddress minValue() {
    return IPV4AddressRange.MINVALUE;
  }

  @Override
  public IPAddress next(final IPAddress value) {
    return IPAddressRangeUtil.next(IPV4AddressRange.MAXVALUE, value);
  }
  
  @Override
  public IPAddress previous(final IPAddress value) {
    return IPAddressRangeUtil.previous(IPV4AddressRange.MINVALUE, value);
  }
}