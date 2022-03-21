package corewar.mars.redcode;

public enum OpCode {
  DAT, MOV, ADD, SUB, JMP, JMZ, JMG, DJZ, CMP;

  public static OpCode getOpCode(String mnemonic) throws RuntimeException {
    for (OpCode op : OpCode.values()) {
      if (op.name().equals(mnemonic)) {
        return op;
      }
    }
    throw new RuntimeException("Unknown opcode: " + mnemonic);
  }

  public static OpCode getOpCode(int code) {
    return OpCode.values()[((int) Math.abs(code)) % (OpCode.values().length)];
  }

  public int getCode() {
    return ordinal();
  }

  public String toString() {
    return name();
  }
}
