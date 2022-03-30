package corewar.mars;

import corewar.mars.Compiler;
import corewar.mars.Core;
import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.OpCode;

public class Mars extends Thread {
  private final boolean DEBUG = false;
  private final long MAX_CYCLE = 5000;
  private final int MIN_WARRIOR_SIZE = 32;
  private Warrior[] warriors;
  private Core[] memory;
  private long cycle;
  
  public Mars(Warrior[] warriors) throws RuntimeException {
    if (warriors.length < 2) {
      throw new RuntimeException("It is not possible to play a game with less than 2 warriors.");
    } else {
      cycle = 0;
      this.warriors = warriors;
      initMemory();
    }
  }

  public void execute(Warrior warrior) {
    Core core, targetCore1, targetCore2;
    int position, target1, target2;
    boolean skipNext;
    if (warrior.isAlive()) {
      skipNext = true;
      position = warrior.getPosition();
      core = memory[getIndex(position)];
      target1 = getAddress(position, core.getAddressModeArg1(), core.getArg1());
      target2 = getAddress(position, core.getAddressModeArg2(), core.getArg2());
      targetCore1 = getCore(target1);
      targetCore2 = getCore(target2);
      if (DEBUG) {
        System.out.println(">> Warrior " + warrior.getId() + " @" + position + " @t" + cycle + ": " + core);
        System.out.println(this);
      }
      switch (core.getOpCode()) {
        case MOV:          
          targetCore2.setValue(targetCore1.getValue());
          skipNext = false;
          break;
        case ADD:
          targetCore2.add(targetCore1);
          skipNext = false;
          break;
        case SUB:
          targetCore2.sub(targetCore1);
          skipNext = false;
          break;
        case JMP:
          warrior.setPosition(getIndex(target1));
          break;
        case JMZ:
          if (targetCore2.getValue() == 0) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        case JMG:
          if (targetCore2.getValue() > 0) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        case DJZ:
          if (targetCore2.decrement()) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        case CMP:
          if (targetCore1.getValue() == targetCore2.getValue()) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        default:
          warrior.die(countAliveWarriors());
      }
      if (!skipNext) {
        warrior.next(memory.length);
      }
    }
  }

  private boolean isGameOver() {
    if (countAliveWarriors() > 1 && cycle < MAX_CYCLE) {
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

  private int getAddress(int position, AddressMode mode, int arg) {
    if (mode != AddressMode.INDIRECT) {
      return (mode == AddressMode.IMMEDIATE) ? arg : position + arg;
    }
    return memory[getIndex(position + arg)].getValue();
  }

  private Core getCore(int address) {
    return memory[getIndex(address)];
  }

  private int getIndex(int address) {
    while (address < 0) {
      address += memory.length;
    }
    return address % memory.length;
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
    if ((j *= 2) < MIN_WARRIOR_SIZE) {
      j = MIN_WARRIOR_SIZE;
    }
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
      //warriors[i].programFlush();
    }
  }

  public void run() {
    while (!isGameOver()) {
      for (Warrior warrior : warriors) {
        execute(warrior);
      }
      cycle++;
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
