package corewar.mars.redcode;

public class Core {
  private static final int MAX_VALUE = 2147483647, MIN_VALUE = -(MAX_VALUE + 1);
  private int owner, value;

  public Core() {
    this(0, -1);
  }

  public Core(int value, int owner) {
    set(value, owner);
  }

  public AddressMode getAddressModeArg1() {
    return AddressMode.getAddressMode(value & 0b1100);
  }

  public AddressMode getAddressModeArg2() {
    return AddressMode.getAddressMode(value & 0b0011);
  }

  public OpCode getOpCode() {
    return OpCode.getOpCode(value >> 4);
  }

  public int getOwner() {
    return owner;
  }

  public int getValue() {
    return value;
  }

  public void set(int value, int owner) {
    setOwner(owner);
    setValue(value);
  }

  private void setOwner(int owner) {
    this.owner = owner;
  }

  private void setValue(int value) {
    this.value = value;
  }

  public String toString() {
    return Integer.toHexString(value);
  }
}
