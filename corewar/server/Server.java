package corewar.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import corewar.utils.API;
import corewar.mars.Warrior;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private ArrayList<Warrior> warriors = new ArrayList<>();
    private ArrayList<String> warriorName = new ArrayList<>();
    private ArrayList<String> warriorOwner = new ArrayList<>();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    //  Acceptation des nouvelles connexions entrantes et création de clientHandlers
    public void start() {
        try {
            while (!this.serverSocket.isClosed()) {
                ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), this);
                System.out.println("new client connected !\n");
                clientHandlers.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {

        }
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        this.clientHandlers.remove(clientHandler);
    }

    private void echo(String username, String apiCall, boolean response) {
        if (!response) {
            if (username != null)
                System.out.println(username + " (request) -> " + apiCall);
            else
                System.out.println("unknown client (request) -> " + apiCall);
        }
        else
            System.out.println("server (response) -> " + apiCall + "\n");
    }

    public String response(String username, String request) {
        String response = "";

        this.echo(username, request, false);
        switch (API.callType(request)) {
            case "username":
                response = checkUsername(request);
                break;
            case "warrior":
                response = addWarrior(request, username);
                break;
            default:
                response = API.errorCode();
                break;
        }
        this.echo(username, response, true);
        return response;
    }

    private String checkUsername(String request) {
        String username = API.apiCallArray(request)[1];
        for (ClientHandler clientHandler : this.clientHandlers) {
            if (clientHandler.getClientUsername() != null && clientHandler.getClientUsername().equals(username))
                return API.errorCode();
        }
        return API.validCode();
    }

    private String addWarrior(String request, String username) {
        String[] requestArray = API.apiCallArray(request);
        String[] program = new String[requestArray.length - 2];
        
        this.warriorName.add(requestArray[1]);
        this.warriorOwner.add(username);
        if (requestArray.length <= 2)
            return API.errorCode();
        for (int i = 2; i < requestArray.length; i++)
            program[i - 2] = requestArray[i];
        this.warriors.add(new Warrior(this.warriors.size(), program));
        return API.validCode();
    }
}