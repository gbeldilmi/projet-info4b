package corewar.client;

import corewar.utils.Read;
import java.io.IOException;
import java.io.File;

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

    public static String gameLoopMenu() {
        String choice;

        reset();
        System.out.println("1 : creer une nouvelle partie");
        System.out.println("2 : rejoindre une partie");
        System.out.println("3 : afficher les parties");
        System.out.println("4 : afficher le classement");
        System.out.println("5 : quitter");
        /*
        System.out.println("1 : " + (!warriorUploaded ? "uploader un warrior" : "lancer partie"));
        System.out.println("2 : afficher le classement");
        System.out.println("3 : quitter");
        */
        do {
            System.out.print(">> ");
            choice = Read.S();
        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5"));
        return choice;
    }

    public static void waiting() {
        System.out.println("[Entree] pour retourner au menu");
        Read.S();
    }

    public static String selectWarrior() {
        String fileName;

        reset();
        System.out.println("/!\\ Votre warrior doit etre dans le dossier du meme nom a la racine du jeu/!\\\nExemple : dwarf.cor\n");
        do {
            System.out.print("Nom du warrior >> ");
            fileName = Read.S();
        } while(!new File("warriors/" + fileName).exists());
        return "warriors/" + fileName;
    }
}
