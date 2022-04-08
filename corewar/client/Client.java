package corewar.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import corewar.utils.API;

public class Client {
    private Socket socket = null;
    private BufferedReader in = null;
    private BufferedWriter out = null;
    public String username = null;
    public int gameId = -1;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            this.close();
        }
    }

    //  Envoi une requête et retourne une réponse serveur
    public String request(String request) {
        String response = "";

        try {
            if (!API.getCallType(request).equals(API.WAITMSG)) {
                this.out.write(request);
                this.out.newLine();
                this.out.flush();
            }
            response = this.in.readLine();
        } catch (IOException e) {
            this.close();
        }
        return response;
    }

    //  Ferme le client proprement
    public void close() {
        try {
            if (this.in != null)
                this.in.close();
            if (this.out != null)
                this.out.close();
            if (this.socket != null)
                this.socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
