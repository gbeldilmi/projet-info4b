package corewar.utils;

import java.util.ArrayList;

public class API {
    public static final String SEPARATOR = "%";
    public static final String ERR = "0";
    public static final String VALID = "1";
    public static final String SETUSERNAME = "2";
    public static final String UPLOADWARRIOR = "3";
    public static final String CREATEGAME = "4";
    public static final String JOINGAME = "5";
    public static final String GETGAMELIST = "6";
    public static final String WAITMSG = "7";
    public static final String DELETEGAME = "8";
    public static final String ENDCONNECTION = "9";

    public static String[] apiCallToArray(String apiCall) {
        return apiCall.split(API.SEPARATOR);
    }

    public static String getCallType(String apiCall) {
        return API.apiCallToArray(apiCall)[0];
    }

    public static boolean isValidResponse(String response) {
        return API.getCallType(response).equals(API.VALID);
    }

    public static String setUsernameRequest(String username) {
        return API.SETUSERNAME + API.SEPARATOR + username;
    }

    public static String uploadWarriorRequest(ArrayList<String> program) {
        int length = program.size();
        String request = API.UPLOADWARRIOR + API.SEPARATOR + program.get(0);

        for (int i = 1; i < length; i++)
            request = request + API.SEPARATOR + program.get(i);
        return request;
    }

    public static String createGameRequest(int maxPlayers) {
        return API.CREATEGAME + API.SEPARATOR + maxPlayers;
    }

    public static String joinGameRequest(int gameId) {
        return API.JOINGAME + API.SEPARATOR + gameId;
    }

    public static String getGameListRequest() {
        return API.GETGAMELIST;
    }

    public static String waitMsgRequest() {
        return API.WAITMSG;
    }

    public static String deleteGameRequest(int gameId) {
        return API.DELETEGAME + API.SEPARATOR + gameId;
    }

    public static String endConnectionRequest() {
        return API.ENDCONNECTION;
    }
}
