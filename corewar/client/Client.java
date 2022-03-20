package corewar.client;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import corewar.utils.API;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username = null;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            this.close();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String request(String request) {
        String response = null;

        try {
            this.bufferedWriter.write(request);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            response = this.bufferedReader.readLine();
        } catch (IOException e) {
            this.close();
        }
        return response;
    }

    public void close() {
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