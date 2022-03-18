package corewar.mars;

public class Warrior {
  private String[] program;
  private int id, position, rank;
  private boolean alive;

  Warrior(int id, String[] program) {
    this.id = id;
    this.program = program.length > 0 ? program : new String[] { "DAT 0 0" };
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

  public String[] getProgram() {
    return program;
  }

  public boolean isAlive() {
    return alive;
  }

  public void programFlush() {
    program = null;
  }

  public void setPosition(int position) {
    this.position = position;
  }
}
