package corewar.utils;

import java.util.ArrayList;

public class API {
    public static final String SEP = "%";
    public static final String ERROR = "error";
    public static final String OK = "ok";
    public static final String USERNAME = "username";
    public static final String WARRIOR = "warrior";

    public static String[] apiCallArray(String apiCall) {
        return apiCall.split(API.SEP);
    }

    public static boolean valid(String apiCall) {
        return apiCall.split(API.SEP)[0].equals(API.OK);
    }

    public static String callType(String apiCall) {
        return apiCall.split(API.SEP)[0];
    }

    public static String newUsernameRequest(String username) {
        return API.USERNAME + API.SEP + username;
    }
    
    public static String newWarriorRequest(ArrayList<String> lines) {
        String request = API.WARRIOR + API.SEP + lines.get(0);

        for (int i = 1; i < lines.size(); i++)
            request = request + API.SEP + lines.get(i);
        return request;
    }

}
