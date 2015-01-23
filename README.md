# jiplib

This is a java library for manipulating IP address ranges. It makes use of [Guava](https://github.com/google/guava) [Ranges](https://code.google.com/p/guava-libraries/wiki/RangesExplained).

Library can handle both IPv4 and IPv6 addresses.

### Using

As a maven dependency:

```xml
<dependency>
  <groupId>net.bican</groupId>
  <artifactId>jiplib</artifactId>
  <version>0.1</version>
</dependency>
```

Here is a brief tutorial:

### Creating a network range CIDR address:

```java
CIDR cidr1 = new CIDR("10.10.10.0/30");
Range<IPAddress> range1 = IPAddresses.fromCIDR(cidr1);
```

### Creating a network range by netmask:

```java
Netmask netmask1 = new Netmask("10.10.20.0/255.255.255.0");
Range<IPAddress> range2 = IPAddresses.fromNetmask(netmask1);
```

### Iterating on a network range:
```java
Iterables.all(new AddressIterable(range1), new Predicate<IPAddress>() {
  @Override
  public boolean apply(IPAddress input) {
    System.out.println(input);
    return true;
  }
});
```

### Checking the contents of a range:
```java
System.out.println("range contains ip: "
  + range2.contains(IPAddress.getInstance(InetAddresses
    .forString("10.10.20.20")))); // returns true
System.out.println("range contains ip: "
  + range2.contains(IPAddress.getInstance(InetAddresses
    .forString("10.10.21.20")))); // returns false
```

### Creating a range from an arbitrary interval:

This code creates a set of ranges that can be represented by CIDR prefixes:

```java
Range<IPAddress> interval = Range.closed(
  IPAddress.getInstance(InetAddresses.forString("1.0.0.1")),
  IPAddress.getInstance(InetAddresses.forString("1.0.2.22")));
Set<Range<IPAddress>> ips = IPAddresses.fromInterval(interval);
for (Range<IPAddress> i : ips) {
  System.out.println(i);
}
```

[![Build Status](https://travis-ci.org/canbican/jiplib.svg?branch=master)](https://travis-ci.org/canbican/jiplib)
[![codecov.io](https://codecov.io/github/canbican/jiplib/coverage.svg?branch=master)](https://codecov.io/github/canbican/jiplib?branch=master)
