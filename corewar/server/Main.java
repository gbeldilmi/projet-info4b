package corewar.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        //  Initialise un serveur et le lance
        Server server = new Server(new ServerSocket(1234));
        server.start();
    }
}
