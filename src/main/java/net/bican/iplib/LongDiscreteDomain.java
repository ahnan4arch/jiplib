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

/**
 * This represents a discrete domain of sizes longer than {@code Long}
 *
 * @author Can Bican
 * @param <C>
 */
interface LongDiscreteDomain<C extends Comparable<?>> {

  /**
   * @param start
   *          first member
   * @param end
   *          last member
   * @return number of members in betwen the first and the last member
   */
  public BigInteger distance(C start, C end);

  /**
   * @return the maximum CIDR prefix of the address range
   */
  public int maxPrefix();

  /**
   * @return the maximum value
   */
  public C maxValue();

  /**
   * @return the minimum value
   */
  public C minValue();

  /**
   * @param value
   *          a member of the domain
   * @return the next member
   */
  public C next(C value);

  /**
   * @param value
   *          a member of the domain
   * @return the previous member
   */
  public C previous(C value);

}
