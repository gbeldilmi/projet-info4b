package corewar.client;

import corewar.utils.API;
import corewar.utils.Read;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import corewar.utils.FileOperation;

public class Main {
    public static void main(String[] args) throws IOException {
        // #############################################
        // MAIN TEMPORAIRE TEST CLIENT SERVEUR !!!!!!!!!
        // #############################################
        Socket socket = new Socket();
        Client client;
        String response;
        String username;
        boolean warriorUploaded = false;
        boolean startGame = false;
        int gameId = -1;
        String choice;
        int value;

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

        while (true) {
            choice = UI.gameLoopMenu();
            switch (choice) {
                case "1":
                    System.out.print("Nombre de joueurs >> ");
                    response = client.request(API.newGameRequest(Read.i()));
                    gameId = Integer.parseInt(API.apiCallArray(response)[1]);
                    System.out.println("Numero de partie : " + gameId);
                    UI.waiting();
                    break;
                case "2":
                    System.out.print("Numero de partie >> ");
                    gameId = Read.i();
                    response = client.request(API.joinGameRequest(gameId));
                    if (API.valid(response))
                        System.out.println("Vous avez rejoint la partie " + gameId + " !");
                    else {
                        System.out.println("Numero de partie invalide ou partie complete !");
                        gameId = -1;
                    }
                    UI.waiting();
                    break;
                case "3":
                    response = client.request(API.getGamesRequest());
                    if (API.valid(response)) {
                        for (int i = 1; i < API.apiCallArray(response).length; i++)
                            System.out.println(API.apiCallArray(response)[i]);
                    } else
                        System.out.println("Aucune partie disponnible");
                    UI.waiting();
                case "4":
                    System.out.println("classement");
                    break;
                case "5": 
                    client.close();
                    System.exit(0);
                default:
                    break;
            }
            if (gameId != -1 && !warriorUploaded) {
                do {
                    choice = UI.selectWarrior();
                    response = client.request(API.newWarriorRequest(FileOperation.read(choice), gameId));
                    System.out.println(response);
                } while (!API.valid(response));
                warriorUploaded = true;
            }
            while (gameId != -1 && warriorUploaded) {
                UI.reset();
                System.out.println("lancement jeu...");
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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