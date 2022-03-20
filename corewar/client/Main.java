package corewar.client;

import corewar.utils.API;
import corewar.utils.Read;
import java.io.IOException;
import java.net.Socket;
import corewar.utils.FileOperation;
import java.io.File;

public class Main {
    public static void main(String[] args) throws IOException {
        // #############################################
        // MAIN TEMPORAIRE TEST CLIENT SERVEUR !!!!!!!!!
        // #############################################
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket);
        String response;
        String username;

        System.out.println("Entrez votre pseudo :");
        do {
            System.out.print(">> ");
            username = Read.S();
            response = client.request(API.newUsernameRequest(username));
        } while (!API.valid(response));
        client.setUsername(username);
        System.out.println("Warrior : ");
        String path;
        do {
            System.out.print(">> ");
            path = Read.S();
        } while(!new File(path).exists());
        response = client.request(API.newWarriorRequest(FileOperation.read(path)));
        //client.close();
    }    
}