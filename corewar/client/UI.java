package corewar.client;

import corewar.utils.Read;
import java.io.IOException;

public class UI {
    public static void printLogo() {
        System.out.println("#####  #####  #####  #####  #   #  #####  #####");
        System.out.println("#      #   #  #   #  #      #   #  #   #  #   #");
        System.out.println("#      #   #  #####  ####   # # #  #####  #####");
        System.out.println("#      #   #  #  #   #      # # #  #   #  #  #");
        System.out.println("#####  #####  #   #  #####  #####  #   #  #   #\n\n");
    }

    public static void reset() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException e) {}
        UI.printLogo();
    }

    public static String serverConnexion() {
        String choice;

        reset();
        System.out.println("1 : mode hors ligne");
        System.out.println("2 : mode en ligne");
        System.out.println("3 : quitter");
        do {
            System.out.print(">> ");
            choice = Read.S();
        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3"));
        if (choice.equals("3"))
            System.exit(0);
        if (choice.equals("2")) {
            System.out.print("Addresse ip du serveur >> ");
            return Read.S();
        }
        return "localhost";
    }

    public static String usernameChoice(String msg) {
        reset();
        if (!msg.equals(""))
            System.out.println(msg);
        System.out.print("Pseudo >> ");
        return Read.S();
    }
}
