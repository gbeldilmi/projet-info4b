package corewar.mars;

import corewar.mars.Compiler;
import corewar.mars.Core;
import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.OpCode;

public class Mars extends Thread {
  private final boolean DEBUG = true;
  private Warrior[] warriors;
  private Core[] memory;
  
  public Mars(Warrior[] warriors) throws RuntimeException {
    if (warriors.length < 2) {
      throw new RuntimeException("It is not possible to play a game with less than 2 warriors.");
    } else {
      this.warriors = warriors;
      initMemory();
    }
  }

  public void execute(Warrior warrior) {
    Core core;
    int position;
    if (warrior.isAlive()) {
      position = warrior.getPosition();
      core = memory[getIndex(position)];
      if (DEBUG) {
        System.out.println(">> Warrior " + warrior.getId() + " @" + position + ": " + core);
        System.out.println(this);
      }
      switch (core.getOpCode()) {
        case MOV:
          memory[getIndex(getTargetAddress(position, core.getAddressModeArg2(), core.getArg2()))] = new Core(getTargetValue(position, core.getAddressModeArg1(), core.getArg1()));
          warrior.next(memory.length);
          break;
        case ADD:
          memory[getIndex(getTargetAddress(position, core.getAddressModeArg2(), core.getArg2()))].add(memory[getIndex(getTargetAddress(position, core.getAddressModeArg1(), core.getArg1()))]);
          warrior.next(memory.length);
          break;
        case SUB:
          memory[getIndex(getTargetAddress(position, core.getAddressModeArg2(), core.getArg2()))].sub(memory[getIndex(getTargetAddress(position, core.getAddressModeArg1(), core.getArg1()))]);
          warrior.next(memory.length);
          break;
        case JMP:
          warrior.setPosition(getTargetAddress(position, core.getAddressModeArg1(), core.getArg1()));
          break;
        case JMZ:
          if (getTargetValue(position, core.getAddressModeArg2(), core.getArg2()) == 0) {
            warrior.setPosition(getTargetAddress(position, core.getAddressModeArg1(), core.getArg1()));
          } else {
            warrior.next(memory.length);
          }
          break;
        case JMG:
          if (getTargetValue(position, core.getAddressModeArg2(), core.getArg2()) > 0) {
            warrior.setPosition(getTargetAddress(position, core.getAddressModeArg1(), core.getArg1()));
          } else {
            warrior.next(memory.length);
          }
          break;
        case DJZ:
          if (memory[getIndex(getTargetAddress(position, core.getAddressModeArg2(), core.getArg2()))].decrement()) {
            warrior.setPosition(getTargetAddress(position, core.getAddressModeArg1(), core.getArg1()));
          } else {
            warrior.next(memory.length);
          }
          break;
        case CMP:
          if (getTargetValue(position, core.getAddressModeArg1(), core.getArg1()) == getTargetValue(position, core.getAddressModeArg1(), core.getArg1())) {
            warrior.setPosition(getTargetAddress(position, core.getAddressModeArg1(), core.getArg1()));
          } else {
            warrior.next(memory.length);
          }
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

  private int getIndex(int address) {
    while (address < 0) {
      address += memory.length;
    }
    return address % memory.length;
  }

  private int getTargetAddress(int position, AddressMode mode, int arg) {
    if (mode != AddressMode.INDIRECT) {
      return (mode == AddressMode.IMMEDIATE) ? arg : position + arg;
    }
    return memory[(position + arg) % memory.length].getValue();
  }

  private int getTargetValue(int position, AddressMode mode, int arg) {
    int targetAddress = getTargetAddress(position, mode, arg);
    return (mode == AddressMode.IMMEDIATE) ? targetAddress : memory[targetAddress % memory.length].getValue();
  }

  public Warrior[] getWarriors() {
    return warriors;
  }

  private void initMemory() {
    Core p[];
    int i, j, k;
    for (i = j = 0; i < warriors.length; i++) {
      k = warriors[i].getProgram().length;
      if (k > j) {
        j = k;
      }
    }
    j *= 2;
    memory = new Core[j * warriors.length];
    for (i = 0; i < warriors.length; i++) {
      warriors[i].setPosition(i * j);
    }
    for (i = 0; i < memory.length; i++) {
      memory[i] = new Core();
    }
    for (i = 0; i < warriors.length; i++) {
      p = warriors[i].getProgram();
      k = warriors[i].getPosition();
      for (j = 0; j < p.length; j++) {
        memory[k + j] = p[j];
      }
      /*warriors[i].programFlush();//*/
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
    for (int i = 0; i < memory.length; i++) {
      sb.append(i + ". " + memory[i].toString() + "\n");
    }
    return sb.toString();
  }
}
