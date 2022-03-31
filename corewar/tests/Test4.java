package corewar.tests;

import corewar.mars.Mars;
import corewar.mars.Warrior;

public class Test4 {
  public static void main(String[] args) {
    Warrior[] warriors = new Warrior[] {
      new Warrior(3, "dwarf1", new String[] {
        "JMP 2",
        "DAT -1",
        "ADD #5 -1",
        "MOV #0 @-2",
        "JMP -2"
      }),//*/
      new Warrior(4, "dwarf2", new String[] {
        "JMP 2",
        "DAT -1",
        "ADD #5 -1",
        "MOV #0 @-2",
        "JMP -2"
      }),//*/
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
