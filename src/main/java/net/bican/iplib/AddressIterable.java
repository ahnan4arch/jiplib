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
 * @param <T>
 */
public class AddressIterable<T extends IPAddress> implements Iterable<T>,
Iterator<T> {

  private T current;
  private LongDiscreteDomain<T> domain;
  private Range<T> range;

  private AddressIterable() {
    // nothing here
  }

  /**
   * @param range
   *          the address range
   * @param domain
   *          addressing domain
   */
  public AddressIterable(final Range<T> range,
      final LongDiscreteDomain<T> domain) {
    this();
    this.range = range;
    this.domain = domain;
    this.current = range.lowerEndpoint();
  }

  @Override
  public boolean hasNext() {
    return (this.domain.next(this.current) != null);
  }

  @Override
  public Iterator<T> iterator() {
    return this;
  }

  @Override
  public T next() {
    final T result = this.domain.next(this.current);
    if (result == null) {
      throw new NoSuchElementException();
    }
    this.current = result;
    return this.current;
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
