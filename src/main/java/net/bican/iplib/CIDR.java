package net.bican.iplib;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.google.common.base.Preconditions;
import com.google.common.net.InetAddresses;

/**
 * CIDR representation of an IP address range
 *
 * @author Can Bican
 */
public class CIDR {
  /**
   * @param netmask
   *          Netmask notation ip address range
   * @return CIDR type representation
   */
  public static CIDR fromNetmask(final Netmask netmask) {
    final CIDR result = new CIDR();
    result.setIpAddress(netmask.getIpAddress());
    result.setPrefix(netmask.toPrefix());
    return result;
  }
  
  private IPAddress ipAddress = null;

  private int prefix = -1;

  private CIDR() {
    super();
  }

  /**
   * @param ipAddress
   *          ip address to set
   * @param prefix
   *          prefix to set
   */
  public CIDR(final IPAddress ipAddress, final int prefix) {
    this();
    Preconditions.checkNotNull(ipAddress, "IP address cannot be null"); //$NON-NLS-1$
    Preconditions.checkArgument(prefix <= ipAddress.getDomain().maxPrefix(),
        "invalid prefix"); //$NON-NLS-1$
    this.ipAddress = ipAddress;
    this.prefix = prefix;
  }

  /**
   * @param cidrString
   *          String version of the CIDR
   */
  public CIDR(final String cidrString) {
    this();
    Preconditions.checkNotNull(cidrString, "String cannot be null"); //$NON-NLS-1$
    final String[] parts = cidrString.split("/"); //$NON-NLS-1$
    if (parts.length > 2) {
      throw new IllegalArgumentException();
    }
    int cidrPrefix = 0;
    if (parts.length == 2) {
      cidrPrefix = Integer.parseInt(parts[1]);
    }
    this.ipAddress = IPAddress.getInstance(InetAddresses.forString(parts[0]));
    this.prefix = cidrPrefix;
  }

  /**
   * @return the first ip address in the range
   */
  public IPAddress getFirst() {
    return this.getMasked((byte) 0);
  }

  /**
   * @return the ip address
   */
  public IPAddress getIpAddress() {
    return this.ipAddress;
  }

  /**
   * @return the last ip address in the range
   */
  public IPAddress getLast() {
    return this.getMasked((byte) 255);
  }

  private IPAddress getMasked(final byte mask) {
    final byte[] b = this.ipAddress.getAddressInstance().getAddress();
    final byte[] resultB = new byte[b.length];
    Arrays.fill(resultB, mask);
    final BitBuffer target = BitBuffer.wrap(resultB);
    target.put(b, this.prefix);
    InetAddress result = null;
    try {
      result = InetAddress.getByAddress(target.array());
    } catch (final UnknownHostException e) {
      // skip and set null
    }
    return IPAddress.getInstance(result);
  }

  /**
   * @return the prefix
   */
  public int getPrefix() {
    return this.prefix;
  }

  /**
   * @param ipAddress
   *          ip address to set
   */
  public void setIpAddress(final IPAddress ipAddress) {
    Preconditions.checkNotNull(ipAddress, "IP address cannot be null"); //$NON-NLS-1$
    this.ipAddress = ipAddress;
  }

  /**
   * @param prefix
   *          prefix to set
   */
  public void setPrefix(final int prefix) {
    Preconditions.checkArgument(prefix <= this.ipAddress.getDomain()
        .maxPrefix(), "invalid prefix"); //$NON-NLS-1$
    this.prefix = prefix;
  }

  @Override
  public String toString() {
    return this.ipAddress + "/" + this.prefix; //$NON-NLS-1$
  }
}
