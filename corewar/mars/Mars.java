package corewar.mars;

import corewar.mars.Core;
import corewar.mars.Warrior;
import corewar.mars.redcode.AddressMode;
import corewar.mars.redcode.OpCode;

public class Mars extends Thread {
  private final long MAX_CYCLE = 1000;
  private final int MIN_WARRIOR_SIZE = 32;
  private Warrior[] warriors;
  private Core[] memory;
  private long cycle;
  private boolean debug;
  
  /*
   * Constructeur minimal, sans affichage de la méoire à chaque cycle
   */
  public Mars(Warrior[] warriors) throws RuntimeException {
    this(warriors, false);
  }

  /*
   * Constructeur complet, spécifiant si on doit afficher la méoire à chaque cycle ou non
   */
  public Mars(Warrior[] warriors, boolean debug) throws RuntimeException {
    if (warriors.length < 2) {
      throw new RuntimeException("It is not possible to play a game with less than 2 warriors.");
    } else {
      cycle = 0;
      this.debug = debug;
      this.warriors = warriors;
      initMemory();
    }
  }

  /*
   * Execute l'instructions courante du warrior spécifié
   * et agis sur la mémoire et le warrior en conséquence 
   */
  public void execute(Warrior warrior) {
    Core core, targetCore1, targetCore2;
    int position, target1, target2;
    boolean skipNext;
    if (warrior.isAlive()) {
      // pleins de variables pour simplifier l'écriture des instructions
      skipNext = true;
      position = warrior.getPosition();
      core = memory[getIndex(position)];
      target1 = getAddress(position, core.getAddressModeArg1(), core.getArg1());
      target2 = getAddress(position, core.getAddressModeArg2(), core.getArg2());
      targetCore1 = getCore(target1);
      targetCore2 = getCore(target2);
      if (debug) { // affichage de la méoire si debug activé
        System.out.println(">> Warrior " + warrior.getId() + " @" + position + " @t" + cycle + ": " + core);
        System.out.println(this);
      }
      switch (core.getOpCode()) { // OPCODE A B
        case MOV: // Transférer contenu adresse A à adresse B
          targetCore2.setValue(core.getAddressModeArg1() == AddressMode.IMMEDIATE ? target1 : targetCore1.getValue());
          skipNext = false;
          break;
        case ADD: // Ajouter contenu adresse A à adresse B
          targetCore2.add(core.getAddressModeArg1() == AddressMode.IMMEDIATE ? target1 : targetCore1.getValue());
          skipNext = false;
          break;
        case SUB: // Soustraire contenu adresse A de contenu adresse B
          targetCore2.sub(core.getAddressModeArg1() == AddressMode.IMMEDIATE ? target1 : targetCore1.getValue());
          skipNext = false;
          break;
        case JMP: // Transférer exécution à adresse A
          warrior.setPosition(getIndex(target1));
          break;
        case JMZ: // Transférer exécution à adresse A seulement si contenu adresse B = 0
          if (targetCore2.getValue() == 0) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        case JMG: // Transférer exécution à adresse A seulement si contenu adresse B > 0
          if (targetCore2.getValue() > 0) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        case DJZ: // Retrancher 1 du contenu adresse B et sauter à adresse A seulement si résultat = 0
          if (targetCore2.decrement()) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        case CMP: // Comparer contenus des adresses A et B, s'ils sont différents sauter l'instruction suivante
          if ((core.getAddressModeArg1() == AddressMode.IMMEDIATE ? target1 : targetCore1.getValue()) == 
              (core.getAddressModeArg2() == AddressMode.IMMEDIATE ? target2 : targetCore2.getValue())) {
            warrior.setPosition(getIndex(target1));
          } else {
            skipNext = false;
          }
          break;
        default: // DAT (Instruction non exécutable)
          warrior.die(countAliveWarriors());
      }
      if (!skipNext) {
        warrior.next(memory.length);
      }
    }
  }

  /*
   * Vérifie si le jeu est terminé
   * c'est à dire si tous les warriors sont mort
   * ou si le nombre de cycle max est atteint
   */
  private boolean isGameOver() {
    if (countAliveWarriors() > 1 && cycle < MAX_CYCLE) {
      return false;
    } else {
      for (Warrior warrior : warriors) {
        if (warrior.isAlive()) {
          warrior.die((cycle < MAX_CYCLE) ? 1 : 0);
        }
      }
      return true;
    }
  }

  /*
   * Compte le nombre de warrior toujours en lice
   */
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
   * Traduit l'adresse spécifiée en index dans la mémoire
   * ou en quantité si le mode d'adressage est immédiat
   */
  private int getAddress(int position, AddressMode mode, int arg) {
    if (mode != AddressMode.INDIRECT) {
      return (mode == AddressMode.IMMEDIATE) ? arg : position + arg;
    }
    return memory[getIndex(position + arg)].getValue();
  }

  /*
   * Accesseur en lecture sur un élément de la mémoire à partir de son adresse
   */
  private Core getCore(int address) {
    return memory[getIndex(address)];
  }

  /*
   * Corrige éventuellement l'adresse en fonction de la taille de la mémoire
   * afin d'éviter les débordements et faire boucler les adresses
   */
  private int getIndex(int address) {
    while (address < 0) {
      address += memory.length;
    }
    return address % memory.length;
  }

  /*
   * Accesseur en lecture des warriors
   */
  public Warrior[] getWarriors() {
    return warriors;
  }

  /*
   * Initialise l'état de la mémoire à partir des programmes compilés des warriors
   */
  private void initMemory() {
    Core p[];
    int i, j, k;
    // Cherche la taille la plus grande
    for (i = j = 0; i < warriors.length; i++) {
      k = warriors[i].getProgram().length;
      if (k > j) {
        j = k;
      }
    }
    // On adapte si c'est trop petit
    if ((j *= 2) < MIN_WARRIOR_SIZE) {
      j = MIN_WARRIOR_SIZE;
    }
    memory = new Core[j * warriors.length];
    // On place les warriors aux bonnes adresses
    for (i = 0; i < warriors.length; i++) {
      warriors[i].setPosition(i * j);
    }
    // On remplit la mémoire
    for (i = 0; i < memory.length; i++) {
      memory[i] = new Core();
    }
    // On copie les programmes des warriors
    for (i = 0; i < warriors.length; i++) {
      p = warriors[i].getProgram();
      k = warriors[i].getPosition();
      for (j = 0; j < p.length; j++) {
        memory[k + j] = p[j];
      }
      warriors[i].programFlush();
    }
  }

  /* 
   * Boucle principale du jeu
   */
  public void run() {
    while (!isGameOver()) {
      for (Warrior warrior : warriors) {
        execute(warrior);
      }
      cycle++;
    }
  }

  /*
   * Représentation textuelle de la mémoire
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < memory.length; i++) {
      sb.append(i + ". " + memory[i].toString() + "\n");
    }
    return sb.toString();
  }
}
