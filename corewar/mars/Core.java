package corewar.mars;

import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.OpCode;

public class Core {
  private int owner;
  private long value;

  public Core() {
    this(0, -1);
  }

  public Core(OpCode opCode, AddressMode addressModeArg1, AddressMode addressModeArg2, int arg1, int arg2, int owner) {
    long value = 0;
    value |= (opCode.getCode() & 0x0F) << 60;
    value |= (addressModeArg1.getCode() & 0b0011) << 58;
    value |= (addressModeArg2.getCode() & 0b0011) << 56;
    value |= (arg1 & 0x0FFFFFFF) << 28;
    if (arg1 < 0) {
      value |= 0x080000000 << 28;
    }
    value |= (arg2 & 0x0FFFFFFF);
    if (arg2 < 0) {
      value |= 0x080000000;
    }
    set(value, owner);
  }

  public Core(long value, int owner) {
    set(value, owner);
  }

  public void add(Core core, int owner) {
    set(value + core.getValue(), owner);
  }

  public boolean decrement(int owner) {
    set(value - 1, owner);
    return getValue() == 0;
  }

  public int getArg1() {
    int r = (int) (value >> 28) & 0x0FFFFFFF;
    return (r & 0x080000000) == 0 ? r : -r;
  }

  public int getArg2() {
    int r = (int) value & 0x0FFFFFFF;
    return (r & 0x080000000) == 0 ? r : -r;
  }

  public AddressMode getAddressModeArg1() {
    return AddressMode.getAddressMode((int) (value >> 58) & 0b11);
  }

  public AddressMode getAddressModeArg2() {
    return AddressMode.getAddressMode((int) (value >> 56) & 0b11);
  }

  public OpCode getOpCode() {
    return OpCode.getOpCode((int) value >> 60);
  }

  public int getOwner() {
    return owner;
  }

  public long getValue() {
    return value;
  }

  public void set(long value, int owner) {
    setOwner(owner);
    setValue(value);
  }

  private void setOwner(int owner) {
    this.owner = owner;
  }

  private void setValue(long value) {
    this.value = value;
  }

  public void sub(Core core, int owner) {
    set(value - core.getValue(), owner);
  }

  public String toString() {
    return Long.toHexString(value);
  }
}
