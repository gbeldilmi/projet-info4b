package corewar.mars;

import corewar.mars.redcode.Argument;
import corewar.mars.redcode.Instruction;
import corewar.mars.redcode.OpCode;

public class Mars extends Thread {
  private Warrior[] warriors;
  private Instruction[] memory;

  Mars(Warrior[] warriors) throws RuntimeException {
    this.warriors = warriors;
    if (warriors.length < 2) {
      throw new RuntimeException("It is not possible to play a game with less than 2 warriors.");
    }
    int maxWarriorLength = 0;
    for (Warrior warrior : warriors) {
      if (warrior.getProgram().length > maxWarriorLength) {
        maxWarriorLength = warrior.getProgram().length;
      }
    }
    memory = new Instruction[maxWarriorLength * 2 * warriors.length];
    for (int i = 0; i < warriors.length; i++) {
      warriors[i].setPosition(i * maxWarriorLength * 2);
    }
    initMemory();
  }

  public void execute(Warrior warrior) throws RuntimeException {
    if (warrior.isAlive()) {
      switch (memory[warrior.getPosition()].getOpCode()) {
        case DAT:
          warrior.die(countAliveWarriors());
          checkGameOver();
          break;
        case MOV:
          //
          break;
        case ADD:
          //
          break;
        case SUB:
          //
          break;
        case JMP:
          //
          break;
        case JMZ:
          //
          break;
        case JMG:
          //
          break;  
        case DJZ:
          //
          break;
        case CMP:
          //
          break;
        default:
          throw new RuntimeException("Unknown instruction");
      }
    }
  }

  private void checkGameOver() {
    if (countAliveWarriors() == 1) {
      for (Warrior warrior : warriors) {
        if (warrior.isAlive()) {
          warrior.die(1);
        }
      }
    }
  }

  private int countAliveWarriors() {
    int count = 0;
    for (Warrior warrior : warriors) {
      if (warrior.isAlive()) {
        count++;
      }
    }
    return count;
  }

  /*
  private int getAddress(Argument arg,int position) {
    switch (arg.getAddressMode()) {
      case IMMEDIATE:
        return arg.getValue();
      case DIRECT:
        return memory[position + arg.getValue()].getArg1().getValue();
      case INDIRECT:
        return (position + arg.getValue()) % memory.length;
      default:
        throw new RuntimeException("Unknown address mode");
    }
  }//*/

  public Warrior[] getWarriors() {
    return warriors;
  }

  private void initMemory() throws RuntimeException {
    Instruction inst;
    int i, j;
    for (i = 0; i < warriors.length; i++) {
      for (j = 0; j < warriors[i].getProgram().length; j++) {
        inst = warriors[i].getProgram()[j].copy(warriors[i].getId());
        if (inst.getOpCode() == OpCode.UNKNOWN) {
          throw new RuntimeException("Unknown instruction in warrior " + i + " at position " + j);
        }
        memory[warriors[i].getPosition() + j] = inst;
      }
    }
  }

  public void run() throws RuntimeException {
    while (countAliveWarriors() > 1) {
      for (Warrior warrior : warriors) {
        execute(warrior);
      }
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Instruction inst : memory) {
      sb.append(inst.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
}
