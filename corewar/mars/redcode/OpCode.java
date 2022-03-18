package corewar.mars.redcode;

public enum OpCode {
  DAT, MOV, ADD, SUB, JMP, JMZ, JMG, DJZ, CMP;

  public static OpCode getOpCode(String mnemonic) {
    for (OpCode op : OpCode.values()) {
      if (op.name().equals(mnemonic)) {
        return op;
      }
    }
    return DAT;
  }

  public static OpCode getOpCode(int code) {
    return OpCode.values()[code % (OpCode.values().length)];
  }

  public int getCode() {
    return ordinal();
  }

  public String toString() {
    return name();
  }
}
