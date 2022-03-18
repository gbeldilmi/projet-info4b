package corewar.tests;

import corewar.mars.redcode.OpCode;

public class Test2 {
  public static void main(String[] args) {
    for (OpCode opCode : OpCode.values()) {
      System.out.println(opCode.toString() + " (" + opCode.ordinal() + ") : " + opCode.getCode());
    }
    for (int i = 0; i < OpCode.values().length * 2; i++) {
      System.out.println(OpCode.getOpCode(i) + " (" + i + ")");
    }
  }
}
