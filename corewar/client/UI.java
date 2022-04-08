package corewar.client;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import corewar.utils.Read;

public class UI {
    public static void reset() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {}
    }

    public static String serverConnexion() {
        String choice;

        reset();
        System.out.println("1 : connexion\n2 : quitter");
        do {
            System.out.print(">> ");
            choice = Read.S();
        } while (!choice.equals("1") && !choice.equals("2"));
        if (choice.equals("2"))
            System.exit(0);
        else {
            reset();
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

    public static String gameMenu() {
        String input;

        reset();
        System.out.println("1 : creer une nouvelle partie\n2 : rejoindre une partie\n3 : afficher le classement\n4 : quitter");
        do {
            System.out.print(">> ");
            input = Read.S();
        } while (Integer.parseInt(input) < 1 || Integer.parseInt(input) > 4);
        return input;
    }

    public static int numberOfPlayers() {
        int input;

        reset();
        System.out.println("Nombre de joueurs (2 joueurs minimum) ");
        do {
            System.out.print(">> ");
            input = Read.i();
        } while (input < 2);
        return input;
    }

    public static int gameId(ArrayList<Integer> ids) {
        int input;

        System.out.println("Identifiant de partie ");
        do {
            System.out.print(">> ");
            input = Read.i();
        } while (!ids.contains(input));
        reset();
        return input;
    }

    public static void waiting() {
        System.out.println("Appuyez sur [Entree] pour retourner au menu");
        Read.S();
    }

    public static String selectWarrior() {
        String fileName;

        reset();
        System.out.println("/!\\ Votre warrior doit etre dans le dossier du meme nom a la racine du jeu /!\\\nExemple : dwarf.red\n");
        do {
            System.out.print("Nom du warrior >> ");
            fileName = Read.S();
        } while(!new File("warriors/" + fileName).exists());
        return "warriors/" + fileName;
    }
}
