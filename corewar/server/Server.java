package corewar.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import corewar.utils.network.ClientHandler;

import java.io.IOException;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    //  private API api;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() {
        try {
            //  Tant que le socketServeur est ouvert
            while(!this.serverSocket.isClosed()) {
                //  On accepte les connexions et on créé un ClientHandler pour chaque connexion
                Socket socket = serverSocket.accept();
                System.out.println("New client connected !");
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clientHandlers.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {

        }
    }

    private void echo(String username, String request) {
        System.out.println("echo : " + username + " -> " + request);
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        this.clientHandlers.remove(clientHandler);
    }

    public String request(String username, String request) {
        // -> check request, utiliser api pour trouver bonne fonction, appeler clientHandler.send() pour retour requête si necéssaire
        this.echo(username, request);
        // check request if invalide return "ERR"
        // si valide -> lancer bonne fonction api et récup retour
        return "reponse";
    }
}