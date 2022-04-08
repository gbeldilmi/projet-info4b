package corewar.utils;

import java.util.ArrayList;

public class API {
    //  Codes API
    public static final String SEPARATOR = "%";
    public static final String ERR = "0";
    public static final String VALID = "1";
    public static final String SETUSERNAME = "2";
    public static final String UPLOADWARRIOR = "3";
    public static final String CREATEGAME = "4";
    public static final String JOINGAME = "5";
    public static final String GETGAMELIST = "6";
    public static final String WAITMSG = "7";
    public static final String ENDCONNECTION = "8";
    public static final String CLASSEMENT = "9";

    //  Retourne une requête ou une réponse sous forme d'un tableau
    public static String[] apiCallToArray(String apiCall) {
        return apiCall.split(API.SEPARATOR);
    }

    //  Retourne le type de la requête ou de la réponse
    public static String getCallType(String apiCall) {
        return API.apiCallToArray(apiCall)[0];
    }

    //  Retourne true si une réponse est valide
    public static boolean isValidResponse(String response) {
        return API.getCallType(response).equals(API.VALID);
    }

    //  Construit une requête setUsername
    public static String setUsernameRequest(String username) {
        return API.SETUSERNAME + API.SEPARATOR + username;
    }

    //  Construit une requête d'upload de warrior
    public static String uploadWarriorRequest(ArrayList<String> program) {
        int length = program.size();
        String request = API.UPLOADWARRIOR + API.SEPARATOR + program.get(0);

        for (int i = 1; i < length; i++)
            request = request + API.SEPARATOR + program.get(i);
        return request;
    }

    //  Construit une requête de création de partie
    public static String createGameRequest(int maxPlayers) {
        return API.CREATEGAME + API.SEPARATOR + maxPlayers;
    }

    //  Construit une requête joinGame
    public static String joinGameRequest(int gameId) {
        return API.JOINGAME + API.SEPARATOR + gameId;
    }

    //  Construit une requête de liste de parties disponibles
    public static String getGameListRequest() {
        return API.GETGAMELIST;
    }

    //  Construit une requête wait
    public static String waitMsgRequest() {
        return API.WAITMSG;
    }

    //  Construit une requête de fin de connexion
    public static String endConnectionRequest() {
        return API.ENDCONNECTION;
    }

    //  Construit une requête de classement
    public static String getClassementRequest() {
        return API.CLASSEMENT;
    }    
}
