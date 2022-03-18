package corewar.mars;

import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.Compiler;
import corewar.mars.redcode.Core;
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

  public void execute(Warrior warrior) throws RuntimeException {
    ////
  }

  private boolean checkGameOver() {
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

  public Warrior[] getWarriors() {
    return warriors;
  }

  private void initMemory() throws RuntimeException {
    Core p[];
    int i, j;
    for (i = 0; i < warriors.length; i++) {
      p = Compiler.compile(warriors[i].getProgram(), warriors[i].getId());
      for (j = 0; j < warriors[i].getProgram().length; j++) {
        memory[warriors[i].getPosition() + j] = p[j];
      }
    }
  }

  public void run() throws RuntimeException {
    while (!checkGameOver()) {
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
