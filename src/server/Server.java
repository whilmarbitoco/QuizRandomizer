package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private int port;
    private ArrayList<Handler> connections;
    private ServerSocket server;

    public Server(int port) {
        this.connections = new ArrayList<>();
        this.port = port;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(this.port);
            System.out.println("Server is running on port " + this.port);
            while (!server.isClosed()) {
                Socket client = server.accept();
                Handler CHandler = new Handler(client, this);
                connections.add(CHandler);
                Thread thread = new Thread(CHandler);
                thread.start();
            }
        } catch (IOException e) {
            shutdown();
        }
    }

//    public void broadcast(String message) {
//        for (Handler ch : connections) {
//           if (ch != null) {
//               ch.sendMessage(message);
//           }
//        }
//    }

    public void shutdown() {
      try {
          if (!server.isClosed()) {
              server.close();
          }
          for(Handler ch : this.connections) {
              ch.shutdown();
          }
      } catch (IOException e) {
          System.out.println("Server already shutdown...");
      }

    }
}
