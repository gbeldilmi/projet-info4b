package corewar.mars.redcode;

public enum OpCode {
  DAT, MOV, ADD, SUB, JMP, JMZ, JMG, DJZ, CMP;

  /*
   * Traduit un opcode sous forme de chaine de caracteres en élément de l'énumération
   */
  public static OpCode getOpCode(String mnemonic) throws RuntimeException {
    for (OpCode op : OpCode.values()) {
      if (op.name().equals(mnemonic)) {
        return op;
      }
    }
    throw new RuntimeException("Opcode inconnu : " + mnemonic);
  }

  /*
   * Donne un élément de l'énumération à partir de son entier correspondant
   */
  public static OpCode getOpCode(int code) {
    return OpCode.values()[code % OpCode.values().length];
  }

  /*
   * Donne le nombre correspondant à l'opcode
   */
  public int getCode() {
    return ordinal();
  }

  /*
   * Donne le nom de l'opcode sous forme de chaine de caractères
   */
  public String toString() {
    return name();
  }
}
