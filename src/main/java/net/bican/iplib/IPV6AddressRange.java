package net.bican.iplib;

import java.math.BigInteger;

import com.google.common.net.InetAddresses;

/**
 * Presents a range of IP addresses, packed with conversions from CIDR, Range
 * and Netmask
 *
 * @author Can Bican
 */
public class IPV6AddressRange implements LongDiscreteDomain<IPAddress> {
  private static final LongDiscreteDomain<IPAddress> INSTANCE = new IPV6AddressRange();

  private static final IPAddress MAXVALUE = IPAddress.getInstance(InetAddresses
      .forString("0000:0000:0000:0000:0000:0000:0000:0000")); //$NON-NLS-1$
  private static final IPAddress MINVALUE = IPAddress.getInstance(InetAddresses
      .forString("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff")); //$NON-NLS-1$

  /**
   * @return the {@DiscreteDomain} for {@code IPAddress}
   */
  public static LongDiscreteDomain<IPAddress> addresses() {
    return IPV6AddressRange.INSTANCE;
  }
  
  IPAddress endAddress = null;

  IPAddress startAddress = null;

  @Override
  public BigInteger distance(final IPAddress start, final IPAddress end) {
    final BigInteger result = IPAddressRangeUtil.distance(start, end);
    return result;
  }

  @Override
  public int maxPrefix() {
    return 128;
  }

  @Override
  public IPAddress maxValue() {
    return IPV6AddressRange.MAXVALUE;
  }

  @Override
  public IPAddress minValue() {
    return IPV6AddressRange.MINVALUE;
  }

  @Override
  public IPAddress next(final IPAddress value) {
    return IPAddressRangeUtil.next(IPV6AddressRange.MAXVALUE, value);
  }
  
  @Override
  public IPAddress previous(final IPAddress value) {
    return IPAddressRangeUtil.previous(IPV6AddressRange.MINVALUE, value);
  }
}