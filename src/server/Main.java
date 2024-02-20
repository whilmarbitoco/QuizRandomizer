package server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(9901);
        server.run();
    }
}
