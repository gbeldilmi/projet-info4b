package corewar.mars;

import corewar.mars.Compiler;
import corewar.mars.Core;
import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.OpCode;

public class Mars extends Thread {
  private Warrior[] warriors;
  private Core[] memory;
  
  Mars(Warrior[] warriors) throws RuntimeException {
    if (warriors.length < 2) {
      throw new RuntimeException("It is not possible to play a game with less than 2 warriors.");
    } else {
      this.warriors = warriors;
      initMemory();
    }
  }

  public void execute(Warrior warrior) {
    if (warrior.isAlive()) {
      switch (memory[warrior.getPosition()].getOpCode()) {
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
          warrior.die(countAliveWarriors());
      }
    }
  }

  private boolean isGameOver() {
    if (countAliveWarriors() > 1) {
      return false;
    } else {
      for (Warrior warrior : warriors) {
        if (warrior.isAlive()) {
          warrior.die(1);
        }
      }
      return true;
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

  private long getTargetValue(int position, AddressMode mode, int arg) {
    if (mode != AddressMode.INDIRECT) {
      return (mode == AddressMode.IMMEDIATE) ? arg : position + arg;
    }
    return memory[(position + arg) % memory.length].getValue();
  }

  public Warrior[] getWarriors() {
    return warriors;
  }

  private void initMemory() {
    Core p[];
    int i, j, k;
    for (i = 0; i < warriors.length; i++) {
      p = warriors[i].getProgram();
      k = warriors[i].getPosition();
      for (j = 0; j < p.length; j++) {
        memory[k + j] = p[j];
      }
    }
  }

  public void run() {
    while (!isGameOver()) {
      for (Warrior warrior : warriors) {
        execute(warrior);
      }
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Core c : memory) {
      sb.append(c.toString());
      sb.append(" ");
    }
    return sb.toString();
  }
}
