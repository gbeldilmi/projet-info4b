package corewar.mars;

import corewar.mars.redcode.Compiler;

public class Warrior {
  private Core[] program;
  private int id, position, rank;
  private boolean alive;

  public Warrior(int id, String[] program) throws RuntimeException {
    this.id = id;
    this.program = Compiler.compile(program, id);
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

  public Core[] getProgram() {
    return program;
  }

  public boolean isAlive() {
    return alive;
  }

  /*public void programFlush() {
    program = null;
  }//*/

  public void setPosition(int position) {
    this.position = position;
  }
}
