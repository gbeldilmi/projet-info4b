package corewar.client;

import corewar.utils.API;
import corewar.utils.Read;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import corewar.utils.FileOperation;
import java.io.File;

public class Main {
    public static void main(String[] args) throws IOException {
        // #############################################
        // MAIN TEMPORAIRE TEST CLIENT SERVEUR !!!!!!!!!
        // #############################################
        Socket socket = new Socket();
        Client client;
        boolean running = true;
        String response;
        String username;
        boolean warriorUploaded = false;
        boolean startGame = false;
        String choice;

        //  Connexion du client au serveur
        while (!socket.isConnected()) {
            try {
                socket = new Socket(UI.serverConnexion(), 1234);
            } catch (UnknownHostException e) {}
        }
        client = new Client(socket);

        //  Choix du pseudo du client
        username = UI.usernameChoice("");
        response = client.request(API.newUsernameRequest(username));
        while (username.contains(API.SEP) || !API.valid(response))
            username = UI.usernameChoice("Pseudo déjà prix");
        client.setUsername(username);

        while (running) {
            choice = UI.gameLoopMenu(warriorUploaded);
            switch (choice) {
                case "1":
                    if (!warriorUploaded) {
                        System.out.println("upload warrior");
                        //  upload warrior
                        warriorUploaded = true;
                    } else
                        startGame = true;
                    break;
                case "2":
                    System.out.println("classement");
                    //  requete classement
                    break;
                case "3":
                    client.close();
                    System.exit(0);
                default:
                    break;
            }
            while (startGame) {
                UI.reset();
                System.out.println("lancement jeu...");
            }
        }

        /*
        System.out.println("Warrior : ");
        String path;
        do {
            System.out.print(">> ");
            path = Read.S();
        } while(!new File(path).exists());
        response = client.request(API.newWarriorRequest(FileOperation.read(path)));
        //client.close();
        */

    }    
}