package net.bican.iplib;

import com.google.common.net.InetAddresses;

/**
 * Netmask representation of an IP address range. It can only work on IPv4
 *
 * @author Can Bican
 */
public class Netmask {
  private static final IPAddress DEFAULT_NETMASK = new IPAddress(
      InetAddresses.forString("255.255.255.255")); //$NON-NLS-1$
  
  private static IPAddress checkValidNetmask(final IPAddress address) {
    final BitBuffer bb = BitBuffer.wrap(address.getAddress());
    boolean zeroFound = false;
    for (int i = 0; i < bb.size(); i++) {
      if (zeroFound) {
        if (bb.get(i)) {
          throw new IllegalArgumentException("invalid netmask"); //$NON-NLS-1$
        }
      }
      if (!bb.get(i)) {
        zeroFound = true;
      }
    }
    return address;
  }
  
  private IPAddress ipAddress;

  private IPAddress netmask;

  private Netmask() {
    super();
  }

  /**
   * @param ipAddress
   *          ip address to set
   * @param netmask
   *          netmask to set
   */
  public Netmask(final IPAddress ipAddress, final IPAddress netmask) {
    this();
    this.ipAddress = ipAddress;
    this.netmask = Netmask.checkValidNetmask(netmask);
  }

  /**
   * @param netmaskString
   *          String version of the netmask
   */
  public Netmask(final String netmaskString) {
    this();
    final String[] parts = netmaskString.split("/"); //$NON-NLS-1$
    if (parts.length > 2) {
      throw new IllegalArgumentException();
    }
    IPAddress nm = Netmask.DEFAULT_NETMASK;
    if (parts.length == 2) {
      nm = Netmask.checkValidNetmask(new IPAddress(InetAddresses
          .forString(parts[1])));
    }
    this.ipAddress = new IPAddress(InetAddresses.forString(parts[0]));
    this.netmask = nm;
  }

  /**
   * @return the ip address
   */
  public IPAddress getIpAddress() {
    return this.ipAddress;
  }

  /**
   * @return the netmask
   */
  public IPAddress getNetmask() {
    return this.netmask;
  }

  /**
   * @param ipAddress
   *          ip address to set
   */
  public void setIpAddress(final IPAddress ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * @param netmask
   *          netmask to set
   */
  public void setNetmask(final IPAddress netmask) {
    this.netmask = Netmask.checkValidNetmask(netmask);
  }

  /**
   * @return the CIDR type prefix of the netmask part
   */
  public int toPrefix() {
    int result = 0;
    final BitBuffer bb = BitBuffer.wrap(this.netmask.getAddress());
    for (int i = 0; i < bb.size(); i++) {
      if (!bb.get(i)) {
        break;
      }
      result++;
    }
    return result;
  }
}
