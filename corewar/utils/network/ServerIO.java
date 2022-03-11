package corewar.utils.network;

import java.io.*;
import java.net.*;

public class ServerIO {
    private int port;
    private boolean working;
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader sysReader;
    private PrintWriter sysWriter;


    public ServerIO(int port) {
        this.port = port;
        this.working = true;
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.socket = this.serverSocket.accept();
            this.sysReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.sysWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public 
}
