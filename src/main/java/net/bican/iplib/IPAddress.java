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

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;

import com.google.common.base.Preconditions;

/**
 * {@code IPAddress} is a wrapper around {@code InetAddress} to provide a
 * comparable frontend.
 *
 * @author Can Bican
 */
public class IPAddress implements Comparable<IPAddress> {
  
  /**
   * return a new instance of {@code IPAddress}.
   *
   * @param addr
   *          address to set
   * @return the wrapper object for addr
   */
  public static IPAddress getInstance(final InetAddress addr) {
    return new IPAddress(addr);
  }
  
  private static int unsignedByteToInt(final byte b) {
    return b & 0xFF;
  }
  
  private InetAddress address;
  
  private IPAddress() {
    // not much here
  }
  
  IPAddress(final InetAddress address) {
    this();
    Preconditions.checkNotNull(address, "address cannot be null"); //$NON-NLS-1$
    this.address = address;
  }
  
  @Override
  public int compareTo(final IPAddress o) {
    final IPAddress adr1 = this;
    final IPAddress adr2 = o;
    final byte[] ba1 = adr1.getAddress();
    final byte[] ba2 = adr2.getAddress();
    
    // general ordering: ipv4 before ipv6
    if (ba1.length < ba2.length) {
      return -1;
    }
    if (ba1.length > ba2.length) {
      return 1;
    }
    
    // we have 2 ips of the same type, so we have to compare each byte
    for (int i = 0; i < ba1.length; i++) {
      final int b1 = IPAddress.unsignedByteToInt(ba1[i]);
      final int b2 = IPAddress.unsignedByteToInt(ba2[i]);
      if (b1 == b2) {
        continue;
      }
      if (b1 < b2) {
        return -1;
      } else {
        return 1;
      }
    }
    return 0;
  }
  
  @Override
  public boolean equals(final Object obj) {
    IPAddress o = (IPAddress) obj;
    return (o.getAddress() != null) ? Arrays.equals(this.getAddress(),
        o.getAddress()) : (this.getAddress() == null);
  }
  
  /**
   * @see InetAddress#getAddress() getAddress
   * @return the result
   */
  public byte[] getAddress() {
    return this.address.getAddress();
  }
  
  /**
   * @return the enclosing {@code InetAddress}
   */
  public InetAddress getAddressInstance() {
    return this.address;
  }
  
  /**
   * @see InetAddress#getCanonicalHostName() getCanonicalHostName
   * @return the result
   */
  public String getCanonicalHostName() {
    return this.address.getCanonicalHostName();
  }
  
  /**
   * @return the discrete domain of the ip address space
   */
  public LongDiscreteDomain<IPAddress> getDomain() {
    if (this.address instanceof Inet4Address) {
      return IPV4AddressRange.addresses();
    }
    return IPV6AddressRange.addresses();
  }
  
  /**
   * @see InetAddress#getHostAddress() getHostAddress
   * @return the result
   */
  public String getHostAddress() {
    return this.address.getHostAddress();
  }
  
  /**
   * @see InetAddress#getHostName() getHostName
   * @return the result
   */
  public String getHostName() {
    return this.address.getHostName();
  }
  
  @Override
  public int hashCode() {
    return this.address.hashCode();
  }
  
  /**
   * @see InetAddress#isAnyLocalAddress() isAnyLocalAddress
   * @return the result
   */
  public boolean isAnyLocalAddress() {
    return this.address.isAnyLocalAddress();
  }
  
  /**
   * @see InetAddress#isLinkLocalAddress() isLinkLocalAddress
   * @return the result
   */
  public boolean isLinkLocalAddress() {
    return this.address.isLinkLocalAddress();
  }
  
  /**
   * @see InetAddress#isLoopbackAddress() isLoopbackAddress
   * @return the result
   */
  public boolean isLoopbackAddress() {
    return this.address.isLoopbackAddress();
  }
  
  /**
   * @see InetAddress#isMCGlobal() isMCGlobal
   * @return the result
   */
  public boolean isMCGlobal() {
    return this.address.isMCGlobal();
  }
  
  /**
   * @see InetAddress#isMCLinkLocal() isMCLinkLocal
   * @return the result
   */
  public boolean isMCLinkLocal() {
    return this.address.isMCLinkLocal();
  }
  
  /**
   * @see InetAddress#isMCNodeLocal() isMCNodeLocal
   * @return the result
   */
  public boolean isMCNodeLocal() {
    return this.address.isMCNodeLocal();
  }
  
  /**
   * @see InetAddress#isMCOrgLocal() isMCOrgLocal
   * @return the result
   */
  public boolean isMCOrgLocal() {
    return this.address.isMCOrgLocal();
  }
  
  /**
   * @see InetAddress#isMCSiteLocal() isMCSiteLocal
   * @return the result
   */
  public boolean isMCSiteLocal() {
    return this.address.isMCSiteLocal();
  }
  
  /**
   * @see InetAddress#isMulticastAddress() isMulticastAddress
   * @return the result
   */
  public boolean isMulticastAddress() {
    return this.address.isMulticastAddress();
  }
  
  /**
   * @see InetAddress#isReachable() isReachable
   * @param timeout
   * @return the result
   * @throws IOException
   */
  public boolean isReachable(final int timeout) throws IOException {
    return this.address.isReachable(timeout);
  }
  
  /**
   * @see InetAddress#isReachable() isReachable
   * @param netif
   * @param ttl
   * @param timeout
   * @return the result
   * @throws IOException
   */
  public boolean isReachable(final NetworkInterface netif, final int ttl,
      final int timeout) throws IOException {
    return this.address.isReachable(netif, ttl, timeout);
  }
  
  /**
   * @see InetAddress#isSiteLocalAddress() isSiteLocalAddress
   * @return the result
   */
  public boolean isSiteLocalAddress() {
    return this.address.isSiteLocalAddress();
  }
  
  @Override
  public String toString() {
    return this.address.getHostAddress();
  }
  
}
