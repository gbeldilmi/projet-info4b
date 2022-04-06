package corewar.mars;

import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.OpCode;

public class Core {
  public static final int MAX_ARG_VALUE = 0x0FFF;
  private int value;

  public Core() {
    this(0);
  }

  public Core(OpCode opCode, AddressMode addressModeArg1, AddressMode addressModeArg2, int arg1, int arg2) {
    this();
    value |= (opCode.getCode() & 0x0F) << 28;
    value |= (addressModeArg1.getCode() & 0x03) << 26;
    value |= (addressModeArg2.getCode() & 0x03) << 24;
    value |= (arg1 & 0x0FFF) << 12;
    if (arg1 < 0) {
      value |= 0x0800 << 12;
    }
    value |= (arg2 & 0x0FFF);
    if (arg2 < 0) {
      value |= 0x0800;
    }
    setValue(value);
  }

  public Core(int value) {
    setValue(value);
  }

  public void add(int value) {
    setValue(this.value + value);
  }

  public boolean decrement() {
    sub(1);
    return getValue() == 0;
  }

  public int getArg1() {
    return getArgComplement((value >> 12) & 0x0FFF);
  }

  public int getArg2() {
    return getArgComplement(value & 0x0FFF);
  }

  private int getArgComplement(int arg) {
    return ((arg & 0x0800) != 0) ? arg | 0xFFFFF000 : arg;
  }

  public AddressMode getAddressModeArg1() {
    return AddressMode.getAddressMode((value >> 26) & 0x03);
  }

  public AddressMode getAddressModeArg2() {
    return AddressMode.getAddressMode((value >> 24) & 0x03);
  }

  public OpCode getOpCode() {
    return OpCode.getOpCode(value >> 28 & 0x0F);
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void sub(int value) {
    setValue(this.value - value);
  }

  public String toString() {
    return getOpCode().toString() + " " + getAddressModeArg1().toString() + getArg1() + " " + getAddressModeArg2().toString() + getArg2() + " [" + Integer.toBinaryString(value);
  }
}
