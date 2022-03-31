package corewar.tests;

import corewar.mars.Mars;
import corewar.mars.Warrior;

public class Test3 {
  public static void main(String[] args) {
    Warrior[] warriors = new Warrior[] {
      new Warrior(1, "aa", new String[] {
        "MOV 0 1"
      }),
      new Warrior(2, "aa", new String[] {
        "ADD 0 1"
      }),
      new Warrior(3, "aa", new String[] {
        "SUB #77 1"
      }),
      new Warrior(4, "aa", new String[] {
        "MOV #1 -1"
      }),
      new Warrior(5, "aa", new String[] {
        "ADD @8 1"
      }),
    };
    Mars mars = new Mars(warriors);
    mars.run();
    try {
      mars.join();      
    } catch (Exception e) {
      e.printStackTrace();
    }
    for (Warrior warrior : mars.getWarriors()) {
      System.out.println(warrior.getId() + ": " + warrior.getRank());
    }
  }
}
