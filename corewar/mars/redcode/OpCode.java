package corewar.mars.redcode;

public enum OpCode {
  UNKNOWN, DAT, MOV, ADD, SUB, JMP, JMZ, JMG, DJZ, CMP;

  /*
  public static OpCode getOpCode(String mnemonic) {
    for (OpCode op : OpCode.values()) {
      if (op.name().equals(mnemonic)) {
        return op;
      }
    }
    return UNKNOWN;
  }//*/

  public String toString() {
    return name();
  }
}
