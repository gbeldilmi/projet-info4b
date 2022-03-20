package corewar.client;

import corewar.utils.Read;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.initUsername();
        } catch (IOException e) {
            closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
        }
    }

    private void initUsername() {
        System.out.print("Username : ");
        String username = Read.S();
        // send
        // besoin de récupérer la reponse de la reqête, attribut ou juste readline ????
    }

    public void sendMessage() {
        try {
            this.bufferedWriter.write(this.username);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();

            while (this.socket.isConnected()) {
                String messageToSend = Read.S();
                this.bufferedWriter.write(messageToSend);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}