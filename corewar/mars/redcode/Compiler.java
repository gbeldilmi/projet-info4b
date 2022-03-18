package corewar.mars.redcode;

import corewar.mars.redcode.Core;
import corewar.utils.Char;

public class Compiler {
  public static Core[] compile(String[] program, int ownerId) {
    int i, valueArg1, valueArg2;
    String[] instruction;
    OpCode opCode;
    AddressMode addressModeArg1, addressModeArg2;
    Core[] compiledProgram = new Core[program.length * 3];
    for (i = 0; i < program.length; i++) {
      instruction = program[i].split(" ");
      try {
        opCode = OpCode.getOpCode(instruction[0]);
      } catch (Exception e) {
        opCode = OpCode.DAT;
      }
      try {
        addressModeArg1 = AddressMode.getAddressMode(instruction[1]);
      } catch (Exception e) {
        addressModeArg1 = AddressMode.INDIRECT;
      }
      try {
        addressModeArg2 = AddressMode.getAddressMode(instruction[2]);
      } catch (Exception e) {
        addressModeArg2 = AddressMode.INDIRECT;
      }
      try {
        if (Char.isDigit(instruction[1].charAt(0)) || instruction[1].charAt(0) == '-') {
          valueArg1 = Integer.parseInt(instruction[1]);
        } else {
          valueArg1 = Integer.parseInt(instruction[1].substring(1));
        }
      } catch (Exception e) {
        valueArg1 = 0;
      }
      try {
        if (Char.isDigit(instruction[2].charAt(0)) || instruction[2].charAt(0) == '-') {
          valueArg2 = Integer.parseInt(instruction[2]);
        } else {
          valueArg2 = Integer.parseInt(instruction[2].substring(1));
        }
      } catch (Exception e) {
        valueArg2 = 0;
      }
      compiledProgram[i * 3] = new Core((opCode.getCode() << 4) + (addressModeArg1.getCode() << 2) + addressModeArg2.getCode(), ownerId);
      compiledProgram[i * 3 + 1] = new Core(valueArg1, ownerId);
      compiledProgram[i * 3 + 2] = new Core(valueArg2, ownerId);
    }
    return compiledProgram;
  }
}
