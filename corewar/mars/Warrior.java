package corewar.mars;

import corewar.mars.Compiler;
import corewar.mars.Core;

public class Warrior {
  private Core[] program;
  private int id, position, rank;
  private boolean alive;
  private String name;

  public Warrior(int id, String name, String[] program) throws RuntimeException {
    this.id = id;
    this.name = name;
    this.program = Compiler.compile(program);
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

  public String getName() {
    return this.name;
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

  public void next(int max) {
    position = (position + 1) % max;
  }
  
  public void programFlush() {
    program = null;
  }

  public void setPosition(int position) {
    this.position = position;
  }
}
