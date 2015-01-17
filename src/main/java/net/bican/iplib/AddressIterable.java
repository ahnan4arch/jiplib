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
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

/**
 * An {@code Iterable} for IP addresses, with size information
 *
 * @author Can Bican
 */
public class AddressIterable implements Iterable<IPAddress>,
    Iterator<IPAddress> {
  
  private IPAddress current;
  private LongDiscreteDomain<IPAddress> domain;
  private Range<IPAddress> range;
  
  private AddressIterable() {
    // nothing here
  }
  
  /**
   * @param range
   *          the address range
   * @param domain
   *          addressing domain
   */
  public AddressIterable(final Range<IPAddress> range) {
    this();
    this.range = range;
    this.domain = (LongDiscreteDomain<IPAddress>) range.lowerEndpoint()
        .getDomain();
    this.current = range.lowerEndpoint();
    if (range.lowerBoundType() == BoundType.OPEN) {
      this.current = this.domain.next(this.current);
    }
  }
  
  @Override
  public boolean hasNext() {
    return (this.current != null);
  }
  
  @Override
  public Iterator<IPAddress> iterator() {
    return this;
  }
  
  @Override
  public IPAddress next() {
    final IPAddress result = this.current;
    if (result == null) {
      throw new NoSuchElementException();
    }
    IPAddress newCurrent = this.domain.next(this.current);
    if ((newCurrent == null) || (!this.range.contains(newCurrent))) {
      this.current = null;
    } else {
      this.current = newCurrent;
    }
    return result;
  }
  
  @Override
  public void remove() {
    throw new UnsupportedOperationException(
        "remove not supported on continous ranges"); //$NON-NLS-1$
  }
  
  /**
   * @return size of the address range
   */
  public BigInteger size() {
    BigInteger result = this.domain.distance(this.range.lowerEndpoint(),
        this.range.upperEndpoint());
    result = result.add(BigInteger.ONE);
    if (this.range.upperBoundType() == BoundType.OPEN) {
      result = result.subtract(BigInteger.ONE);
    }
    if (this.range.lowerBoundType() == BoundType.OPEN) {
      result = result.subtract(BigInteger.ONE);
    }
    return result;
  }
}
