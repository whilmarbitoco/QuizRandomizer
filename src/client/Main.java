package client;

import server.Server;

public class Main {
    public static void main(String[] args) {
        Client client = new Client(9901);
        client.run();
    }
}
