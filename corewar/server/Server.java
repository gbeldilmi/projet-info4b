package corewar.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import corewar.mars.Warrior;
import corewar.utils.API;

public class Server {
    private ServerSocket socket = null;
    private ArrayList<ClientHandler> clientHandlers = null;
    private ArrayList<Game> games = null;
    private ClassementHandler classementHandler = null;

    public Server(ServerSocket socket) {
        this.clientHandlers = new ArrayList<>();
        this.socket = socket;
        this.games = new ArrayList<>();
        this.classementHandler = new ClassementHandler("classement.txt");
        Thread thread = new Thread(this.classementHandler);
        thread.start();
    }

    public void start() {
        ClientHandler clientHandler;
        try {
            while (!this.socket.isClosed()) {
                clientHandler = new ClientHandler(socket.accept(), this);
                System.out.println("Nouvelle connexion sur le port : " + socket.getLocalPort());
                clientHandlers.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  Si client en jeu ????
    public void removeClientHandler(ClientHandler clientHandler) {
        if (clientHandler.gameId != -1)
            this.games.get(clientHandler.gameId).removeClient(clientHandler);
        this.clientHandlers.remove(clientHandler);
    }

    public void removeGame(int gameId) {
        for (ClientHandler clientHandler : this.clientHandlers) {
            if (clientHandler.gameId == gameId) {
                clientHandler.gameId = -1;
                clientHandler.warriorId = -1;
            }
        }
        this.games.set(gameId, null);
    }

    private void echoRequest(String username, String request) {
        System.out.println((username == null ? "unknown" : username) + "(request) -> " + request);
    }

    private void echoResponse(String response) {
        System.out.println("server (response) -> " + response);
    }

    
    public String response(ClientHandler clientHandler, String request) {
        String response = API.ERR;

        this.echoRequest(clientHandler.clientUsername, request);
        switch (API.getCallType(request)) {
            case API.SETUSERNAME:
                response = setUsername(clientHandler, request); break;
            case API.CREATEGAME:
                response = createGame(clientHandler, request); break;
            case API.GETGAMELIST:
                response = getGamesList(); break;
            case API.JOINGAME:
                response = joinGame(clientHandler, request); break;
            case API.UPLOADWARRIOR:
                response = uploadWarrior(clientHandler, request); break;
            case API.CLASSEMENT:
                response = getClassement(); break;
            default:
                response = API.ERR; break;
        }
        this.echoResponse(response);
        return response;
    }

    private String setUsername(ClientHandler clientHandler, String request) {
        String username = API.apiCallToArray(request)[1];

        if (clientHandler.clientUsername != null)
            return API.ERR;
        for (ClientHandler otherClientHandler : this.clientHandlers) {
            if (username.equals(otherClientHandler.clientUsername))
                return API.ERR;
        }
        clientHandler.clientUsername = username;
        return API.VALID;
    }

    public String createGame(ClientHandler clientHandler, String request) {
        int gameLength;
        int i;
        String[] requestArray = API.apiCallToArray(request);
        int maxPlayers = Integer.parseInt(requestArray[1]);

        gameLength = this.games.size();
        i = 0;
        while (i < gameLength) {
            if (this.games.get(i) == null) {
                this.addGame(i, new Game(this, clientHandler, maxPlayers));
                clientHandler.gameId = i;
                return API.VALID + API.SEPARATOR + i;
            }
            i++;
        }
        this.addGame(-1, new Game(this, clientHandler, maxPlayers));
        clientHandler.gameId = gameLength;
        return API.VALID + API.SEPARATOR + (gameLength);
    }

    private void addGame(int i, Game game) {
        if (i == -1)
            this.games.add(game);
        else
            this.games.set(i, game);
        Thread thread = new Thread(game);
        this.classementHandler.addGame(game, thread);
        thread.start();
    }

    public String getGamesList() {
        String gameList = "";
        int length = this.games.size();

        if (length == 0)
            return API.ERR;
        for (int i = 0; i < length; i++) {
            if (this.games.get(i) != null && !this.games.get(i).isFull())
                gameList = gameList + API.SEPARATOR + i + API.SEPARATOR + this.games.get(i);
        }
        if (gameList.length() == 0)
            return API.ERR;
        return API.VALID + gameList;
    }

    public String joinGame(ClientHandler clientHandler, String request) {
        int gameId = Integer.parseInt(API.apiCallToArray(request)[1]);

        if (this.games.get(gameId) == null || this.games.get(gameId).isFull())
            return API.ERR;
        this.games.get(gameId).addClient(clientHandler);
        clientHandler.gameId = gameId;
        return API.VALID;
    }

    public String uploadWarrior(ClientHandler clientHandler, String request) {
        String[] requestArray = API.apiCallToArray(request);
        int length = requestArray.length;
        
        if (length < 3)
            return API.ERR;
        String[] program = new String[length - 2];
        String name = requestArray[1];
        int clientId = this.games.get(clientHandler.gameId).getClientId(clientHandler.clientUsername);
        for (int i = 2; i < length; i++)
            program[i - 2] = requestArray[i];
        this.games.get(clientHandler.gameId).addWarrior(clientHandler, new Warrior(clientId, name, program));
        return API.VALID;
    }

    public String getClassement() {
        String str = this.classementHandler.getClassement();
        
        if (str == null)
            return API.ERR;
        return API.VALID + API.SEPARATOR + str;
    }
}
