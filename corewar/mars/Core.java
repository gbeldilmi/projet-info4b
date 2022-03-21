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
    this(((opCode.getCode() & 0x0F) << 60) + ((addressModeArg1.getCode() & 0b0011) << 58) + ((addressModeArg2.getCode() & 0b0011) << 56) + ((arg1 & 0x0FFFFFFF) << 28) + (arg2 & 0x0FFFFFFF), owner);
  }

  public Core(long value, int owner) {
    set(value, owner);
  }

  public Core add(Core core) {
    return new Core(value + core.getValue(), owner);
  }

  public int getArg1() {
    return (int) (value >> 28) & 0x0FFFFFFF;
  }

  public int getArg2() {
    return (int) value & 0x0FFFFFFF;
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

  public Core sub(Core core) {
    return new Core(value - core.getValue(), owner);
  }

  public String toString() {
    return Long.toHexString(value);
  }
}
