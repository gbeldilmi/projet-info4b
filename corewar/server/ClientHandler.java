package corewar.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import corewar.utils.API;

public class ClientHandler implements Runnable {
    private Socket socket = null;
    private Server server = null;
    private BufferedReader in = null;
    private BufferedWriter out = null;
    public String clientUsername = null;
    public int gameId = -1;
    public int warriorId = -1;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            this.close();
        }
    }

    //  Attend une requête (envoyé par le client) et demande au serveur la réponse à envoyer en retour
    @Override
    public void run() {
        String request = "";
        String response = "";

        while (this.socket.isConnected()) {
            try {
                request = this.in.readLine();
                if (API.getCallType(request).equals(API.ENDCONNECTION)) {
                    this.close();
                    break;
                }
                response = this.server.response(this, request);
                this.send(response);
            } catch (Exception e) {
                this.close();
                break;
            }
        }
    }

    //  Envoi une réponse au client
    public void send(String response) {
        try {
            this.out.write(response);
            this.out.newLine();
            this.out.flush();
        } catch (IOException e) {
            this.close();
        }
    }

    //  Ferme le ClientHandler
    public void close() {
        System.out.println(this.clientUsername + " s'est deconnecte !");
        this.server.removeClientHandler(this);
        try {
            if (this.in != null)
                this.in.close();
            if (this.out != null)
                this.out.close();
            if (this.socket != null)
                this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
