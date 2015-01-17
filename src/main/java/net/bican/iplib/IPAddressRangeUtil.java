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
import java.net.InetAddress;

import com.google.common.net.InetAddresses;

class IPAddressRangeUtil {
  static BigInteger distance(final IPAddress start, final IPAddress end) {
    final BigInteger bi1 = new BigInteger(1, start.getAddress());
    final BigInteger bi2 = new BigInteger(1, end.getAddress());
    return bi2.subtract(bi1);
  }

  static IPAddress next(final IPAddress endAddress, final IPAddress address) {
    if (address.equals(endAddress)) {
      return null;
    }
    try {
      final InetAddress result = InetAddresses.increment(address
          .getAddressInstance());
      return IPAddress.getInstance(result);
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  static IPAddress previous(final IPAddress startAddress,
      final IPAddress address) {
    if (address.equals(startAddress)) {
      return null;
    }
    try {
      final InetAddress result = InetAddresses.decrement(address
          .getAddressInstance());
      return IPAddress.getInstance(result);
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }
}
