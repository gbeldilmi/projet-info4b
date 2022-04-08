package corewar.mars;

import corewar.mars.Compiler;
import corewar.mars.Core;

public class Warrior {
  private Core[] program;
  private int id, position, rank;
  private boolean alive;
  private String name;

  /*
   * Constructeur
   */
  public Warrior(int id, String name, String[] program) throws RuntimeException {
    this.id = id;
    this.name = name;
    this.program = Compiler.compile(program);
    position = 0;
    rank = 0;
    alive = true;
  }

  /*
   * Fait mourir le warrior en spécifiant sa position dans le classement
   */
  public void die(int rank) {
    if (alive) {
      this.rank = rank;
      alive = false;
    }
  }

  /*
   * Accesseurs en lecture pour chaque attribut de l'objet Warrior
   */
  public String getName() {
    return name;
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

  /*
   * Déplace le warrior à la case suivante (modulo le nombre maximum de cases de la mémoire)
   */
  public void next(int max) {
    position = (position + 1) % max;
  }
  
  /*
   * Libère la mémoire allouée pour le programme du warrior
   */
  public void programFlush() {
    program = null;
  }

  /*
   * Accesseur en écriture pour la position du warrior
   */
  public void setPosition(int position) {
    this.position = position;
  }
}
