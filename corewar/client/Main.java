package corewar.client;

import corewar.utils.Read;
import java.net.Socket;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 1234);
    Client client = new Client(socket);
    client.listenForMessage();
    client.sendMessage();
}
}
