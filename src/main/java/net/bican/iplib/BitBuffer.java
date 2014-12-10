package net.bican.iplib;

class BitBuffer {

  private static Boolean isBitSet(final byte b, final int bit) {
    return (b & (1 << bit)) != 0;
  }

  public static BitBuffer wrap(final byte[] bytes) {
    final BitBuffer result = new BitBuffer();
    result.bits = new boolean[bytes.length * 8];
    int c = 0;
    for (final byte b : bytes) {
      for (int i = 7; i >= 0; i--) {
        result.bits[c] = BitBuffer.isBitSet(b, i);
        c++;
      }
    }
    return result;
  }

  private boolean[] bits;

  public byte[] array() {
    final byte[] result = new byte[this.bits.length / 8];
    int resultPos = 0;
    int bit = 7;
    byte b = (byte) 0;
    for (final boolean value : this.bits) {
      b = (byte) (value ? b | (1 << bit) : b & ~(1 << bit));
      bit--;
      if (bit < 0) {
        result[resultPos] = b;
        bit = 7;
        resultPos++;
        b = (byte) 0;
      }
    }
    return result;
  }

  public boolean get(final int pos) {
    return this.bits[pos];
  }

  public void put(final byte[] bytes, final int prefix) {
    int c = 0;
    for (final byte b : bytes) {
      for (int i = 7; i >= 0; i--) {
        if (c >= prefix) {
          return;
        }
        this.bits[c] = BitBuffer.isBitSet(b, i);
        c++;
      }
    }
  }

  public int size() {
    return this.bits.length;
  }

  @SuppressWarnings("nls")
  @Override
  public String toString() {
    final StringBuffer result = new StringBuffer();
    for (final boolean b : this.bits) {
      result.append(b ? "1" : "0");
    }
    return result.toString();
  }
}
