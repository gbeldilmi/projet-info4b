package corewar.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server(new ServerSocket(32769));
        server.start();
    }
}
