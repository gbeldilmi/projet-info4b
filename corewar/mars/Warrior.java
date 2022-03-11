package corewar.mars;

import corewar.mars.redcode.Instruction;

public class Warrior {
  private Instruction[] program;
  private int id, position, rank;
  private boolean alive;

  Warrior(int id, String[] program) {
    this.id = id;
    this.program = new Instruction[program.length];
    for (int i = 0; i < program.length; i++) {
      this.program[i] = new Instruction(program[i], id);
    }
    position = 0;
    rank = 0;
    alive = true;
  }

  public void die(int rank) {
    if (alive) {
      this.rank = rank;
      alive = false;
    }
  }

  public int getRank() {
    return rank;
  }

  public int getId() {
    return id;
  }

  public int getPosition() {
    return position;
  }

  public Instruction[] getProgram() {
    return program;
  }

  public boolean isAlive() {
    return alive;
  }

  public void setPosition(int position) {
    this.position = position;
  }
}
