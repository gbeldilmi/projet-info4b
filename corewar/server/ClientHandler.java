package corewar.server;

import java.net.Socket;
import corewar.utils.API;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername = null;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            this.close();
        }
    }

    public String getClientUsername() {
        return this.clientUsername;
    }

    @Override
    public void run() {
        String request;
        String response;

        while (this.socket.isConnected()) {
            try {
                request = this.bufferedReader.readLine();
                response = server.response(this.clientUsername, request);
                if (API.callType(request).equals("username") && API.valid(response))
                    this.clientUsername = API.apiCallArray(request)[1];
                this.send(response);
            } catch (Exception e) {
                this.close();
                break;
            }
        }
    }

    public void send(String response) {
        try {
            this.bufferedWriter.write(response);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            this.close();
        }
    }

    public void close() {
        this.server.removeClientHandler(this);
        try {
            if (this.bufferedReader != null)
                bufferedReader.close();
            if (this.bufferedWriter != null)
                bufferedWriter.close();
            if (this.socket != null)
                this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
