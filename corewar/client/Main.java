package corewar.client;

import java.io.IOException;

import corewar.utils.API;
import corewar.utils.FileOperation;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        Client client = null;
        String response = "";
        String input = "";

        //  Connexion du client via le socket
        while (!socket.isConnected()) {
            try {
                socket = new Socket(UI.serverConnexion(), 1234);
            } catch (UnknownHostException e) {}
        }
        client = new Client(socket);

        //  Choix du pseudo du joueur
        input = UI.usernameChoice("");
        while (input.contains(API.SEPARATOR) || !API.isValidResponse(client.request(API.setUsernameRequest(input))))
            input = UI.usernameChoice("Pseudo invalide ou deja pris");
        client.username = input; 

        while (true) {
            if (client.gameId == -1) {
                input = UI.gameMenu();
                switch (input) {
                    case "1": //    Création d'une nouvelle partie
                        response = client.request(API.createGameRequest(UI.numberOfPlayers()));
                        client.gameId = Integer.parseInt(API.apiCallToArray(response)[1]);
                        UI.reset();
                        System.out.println("Identifiant de partie : " + client.gameId);
                        System.out.println("");
                        UI.waiting();
                        break;
                    case "2": //    Le joueur rejoint une partie
                        UI.reset();
                        response = client.request(API.getGameListRequest());
                        if (API.isValidResponse(response)) {
                            String[] games = API.apiCallToArray(response);
                            ArrayList<Integer> ids = new ArrayList<>();
                            for (int i = 1; i < games.length; i += 2) {
                                System.out.println("Identifiant de partie : " + games[i] + "\nNombre de joueurs : " + games[i + 1] + "\n");
                                ids.add(Integer.parseInt(games[i]));
                            }
                            int id = UI.gameId(ids);
                            response = client.request(API.joinGameRequest(id));
                            if (API.isValidResponse(response)) {
                                System.out.println("Vous avez rejoint la partie !");
                                client.gameId = id;
                            } else
                                System.out.println("Impossible de rejoindre cette partie !");
                        } else
                            System.out.println("Aucune partie trouvee !");
                        System.out.println("");
                        UI.waiting();
                        break;
                    case "3": //    Demande d'affichage du classement
                        UI.reset();
                        response = client.request(API.getClassementRequest());
                        if (API.isValidResponse(response)) {
                            String[] classement = API.apiCallToArray(response);
                            for (int i = 1; i < classement.length; i += 2)
                                System.out.println((i / 2 + 1) + " - " + classement[i] + " | score : " + classement[i + 1]);
                        } else
                            System.out.println("Classement indisponible !");
                        System.out.println("");
                        UI.waiting();
                        break;
                    case "4": //    Le joueur souhaite quitter le jeu
                        client.request(API.endConnectionRequest());
                        client.close();
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            } else { //  Le joueur est dans une partie
                //  Upload du warrior du
                do {
                    response = client.request(API.uploadWarriorRequest(FileOperation.read(UI.selectWarrior())));
                } while (!API.isValidResponse(response));
                UI.reset();
                System.out.println("En attente des autres joueurs...");
                response = client.request(API.waitMsgRequest());
                UI.reset();

                //  Affichage du classement de la partie
                String[] classement = API.apiCallToArray(response);
                System.out.println("Fin de la partie !\n");
                for (int i = 0; i < classement.length; i++)
                    System.out.println(classement[i]);
                client.gameId = -1;
                System.out.println();
                UI.waiting();
            }
        }
    }
}
