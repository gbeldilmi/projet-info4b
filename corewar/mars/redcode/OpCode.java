package corewar.mars.redcode;

public enum OpCode {
  UNKNOWN, DAT, MOV, ADD, SUB, JMP, JMZ, JMG, DJZ, CMP;

  public static OpCode getOpCode(String mnemonic) {
    for (OpCode op : OpCode.values()) {
      if (op.name().equals(mnemonic)) {
        return op;
      }
    }
    return UNKNOWN;
  }

  public static OpCode getOpCode(int code) {
    switch (code % (OpCode.values().length - 1)) {
      case 0:
        return DAT;
      case 1:
        return MOV;
      case 2:
        return ADD;
      case 3:
        return SUB;
      case 4:
        return JMP;
      case 5:
        return JMZ;
      case 6:
        return JMG;
      case 7:
        return DJZ;
      case 8:
        return CMP;
      default:
        return UNKNOWN;
    }
  }

  public int getCode() {
    switch (this) {
      case DAT:
        return 0;
      case MOV:
        return 1;
      case ADD:
        return 2;
      case SUB:
        return 3;
      case JMP:
        return 4;
      case JMZ:
        return 5;
      case JMG:
        return 6;
      case DJZ:
        return 7;
      case CMP:
        return 8;
      default:
        return -1;
    }
  }

  public String toString() {
    return name();
  }
}
