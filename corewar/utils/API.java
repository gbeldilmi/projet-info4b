package corewar.utils;

import java.util.ArrayList;

public class API {
    private static String sep = "%";
    private static String error = "0";
    private static String ok = "1";
    private static String username = "2";
    private static String warrior = "3";

    public static String[] apiCallArray(String apiCall) {
        return apiCall.split(API.sep);
    }

    public static String errorCode() {
        return API.error;
    }

    public static String validCode() {
        return API.ok;
    }

    public static boolean valid(String apiCall) {
        return apiCall.split(API.sep)[0].equals(API.ok);
    }

    public static String callType(String apiCall) {
        String callType = null;
        switch (apiCall.split(API.sep)[0]) {
            case "0":
                callType = "error";
                break;
            case "1":
                callType = "ok";
                break;
            case "2":
                callType = "username";
                break;
            case "3":
                callType = "warrior";
                break;
            default:
                break;
        }
        return callType;
    }

    public static String newUsernameRequest(String username) {
        return API.username+ API.sep + username;
    }
    
    public static String newWarriorRequest(ArrayList<String> lines) {
        String request = API.warrior + API.sep + lines.get(0);

        for (int i = 1; i < lines.size(); i++)
            request = request + API.sep + lines.get(i);
        return request;
    }

}
