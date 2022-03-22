package corewar.tests;

import corewar.mars.Mars;
import corewar.mars.Warrior;

public class Test4 {
  public static void main(String[] args) {
    Warrior[] warriors = new Warrior[] {
      new Warrior(1, new String[] {
        "MOV 0 1"
      }),//*/
      new Warrior(2, new String[] {
        "JMP 3",
        "DAT 0",
        "DAT 99",
        "MOV @-2 @-1",
        "CMP -3 #9",
        "JMP 4",
        "ADD #1 -5",
        "ADD #1 -5",
        "JMP -5",
        "MOV #99 93",
        "JMP 93"
      }),//*/
      /*new Warrior(3, new String[] {
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
