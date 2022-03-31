package corewar.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import corewar.utils.API;
import corewar.mars.Warrior;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private ArrayList<Game> games = new ArrayList<>();
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

    public String response(ClientHandler clientHandler, String request) {
        String response = "";

        this.echo(clientHandler.getClientUsername(), request, false);
        switch (API.callType(request)) {
            case API.USERNAME:
                response = checkUsername(request); break;
            case API.WARRIOR:
                response = addWarrior(request, clientHandler); break;
            case API.NEWGAME:
                response = newGame(request, clientHandler); break;
            case API.JOINGAME:
                response = joinGame(request, clientHandler); break;
            case API.GETGAMES:
                response = getGames(); break;
            default:
                response = API.ERROR; break;
        }
        this.echo(clientHandler.getClientUsername(), response, true);
        return response;
    }

    private String checkUsername(String request) {
        String username = API.apiCallArray(request)[1];
        for (ClientHandler clientHandler : this.clientHandlers) {
            if (clientHandler.getClientUsername() != null && clientHandler.getClientUsername().equals(username))
                return API.ERROR;
        }
        return API.OK;
    }

    // revoir fonction ! ajout warrior possible si joueur dans game
    // ajout warrior dans game
    private String addWarrior(String request, ClientHandler clientHandler) {
        String[] requestArray = API.apiCallArray(request);
        if (requestArray.length <= 3)
            return API.ERROR;
        String[] program = new String[requestArray.length - 3];
        String warriorName = requestArray[2];
        int gameId = Integer.parseInt(requestArray[1]);
        int clientId = this.games.get(gameId).getClientId(clientHandler.getClientUsername());
        for (int i = 3; i < requestArray.length; i++)
            program[i - 3] = requestArray[i];
        this.games.get(gameId).addWarrior(new Warrior(clientId, warriorName, program));
        return API.OK;
    }

    private String newGame(String request, ClientHandler clientHandler) {
        int maxPlayers;
        String[] requestArray = API.apiCallArray(request);
        if (requestArray.length != 2)
            return API.ERROR;
        maxPlayers = Integer.parseInt(requestArray[1]);
        this.games.add(new Game(clientHandler, maxPlayers));
        Thread thread = new Thread(this.games.get(this.games.size() - 1));
        thread.start();
        return API.OK + API.SEP + (this.games.size() - 1);
    }
    
    private String joinGame(String request, ClientHandler clientHandler) {
        int gameId = Integer.parseInt(API.apiCallArray(request)[1]);

        if (gameId < 0 || gameId > this.games.size() - 1)
            return API.ERROR;
        if (this.games.get(gameId).addClient(clientHandler))
            return API.OK;
        return API.ERROR;
    }

    private String getGames() {
        String gameList = API.OK;

        if (this.games.size() == 0)
            return API.ERROR;
        for (int i = 0; i < this.games.size(); i++) {
            if (!this.games.get(i).isFull())
                gameList = gameList + API.SEP + "Partie " + i + " (" + this.games.get(i).gameCapacity() + ")";
        }
        return gameList;
    }

    //private String getClassement();

    // getGames
    // newGame
    // addClientToGame
    
}