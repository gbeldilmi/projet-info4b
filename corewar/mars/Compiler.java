package corewar.mars;

import corewar.mars.Core;
import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.OpCode;

public class Compiler {
  public static Core[] compile(String[] program) throws RuntimeException {
    int i, valueArg1, valueArg2;
    String[] instruction;
    OpCode opCode;
    AddressMode addressModeArg1, addressModeArg2;
    Core[] compiledProgram;
    if (program.length == 0) {
      throw new RuntimeException("Empty program.");
    }
    compiledProgram = new Core[program.length];
    for (i = 0; i < program.length; i++) {
      addressModeArg1 = addressModeArg2 = AddressMode.getAddressMode(0);
      valueArg1 = valueArg2 = 0;
      instruction = program[i].split(" ");
      try {
        opCode = OpCode.getOpCode(instruction[0]);
      } catch (Exception e) {
        throw new RuntimeException(e.toString() + " @" + (i + 1));
      }
      if (opCode == OpCode.DAT || opCode == OpCode.JMP) {
        if (instruction.length != 2) {
          throw new RuntimeException("Invalid number of arguments for " + opCode.toString() + " @" + (i + 1));
        }
        try {
          if (opCode == OpCode.JMP) {
            addressModeArg1 = AddressMode.getAddressMode(instruction[1].charAt(0));
            valueArg1 = Integer.parseInt((addressModeArg1 == AddressMode.DIRECT) ? instruction[1] : instruction[1].substring(1));
          } else {
            valueArg2 = Integer.parseInt(instruction[1]);
          }
        } catch (Exception e) {
          throw new RuntimeException("Invalid argument for " + opCode.toString() + " @" + (i + 1));
        }
      } else {
        if (instruction.length != 3) {
          throw new RuntimeException("Invalid number of arguments for " + opCode.toString() + " @" + (i + 1));
        }
        try {
          addressModeArg1 = AddressMode.getAddressMode(instruction[1].charAt(0));
          valueArg1 = Integer.parseInt((addressModeArg1 == AddressMode.DIRECT) ? instruction[1] : instruction[1].substring(1));
          addressModeArg2 = AddressMode.getAddressMode(instruction[2].charAt(0));
          valueArg2 = Integer.parseInt((addressModeArg2 == AddressMode.DIRECT) ? instruction[2] : instruction[2].substring(1));
        } catch (Exception e) {
          throw new RuntimeException("Invalid argument for " + opCode.toString() + " @" + (i + 1));
        }
      }
      compiledProgram[i] = new Core(opCode, addressModeArg1, addressModeArg2, valueArg1, valueArg2);
    }
    return compiledProgram;
  }
}
