package server;

import client.Data;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable{
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;
    String username;
    ObjectOutputStream objout;
    ObjectInputStream obin;

    public Handler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }
    @Override
    public void run() {
        try {
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream(), true);
//            username = in.readLine();
//            server.broadcast(username + " connected...");

            objout = new ObjectOutputStream(socket.getOutputStream());
            obin = new ObjectInputStream(socket.getInputStream());

            String str;
            while ((str = (String) obin.readObject()) != null) {
                System.out.println(str);
                objout.writeObject(str);
            }

//            String message;
//            while ((message = in.readLine()) != null) {
//                handleMessage(message);
//                System.out.println(message);
//            }

        } catch (IOException e) {
         shutdown();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


//    public void sendMessage(String message) {
//        out.println(message);
//    }

    public void sendObject(Data data)  {
        try {
            objout.writeObject(data);

        } catch (IOException e) {
            System.out.println("Object error");
        }
    }

//    public void handleMessage(String message) {
//        if (message.startsWith("/quit")) {
//            server.broadcast(username + " left the chat...");
//            shutdown();
//        } else {
//            server.broadcast(username + " " + message);
//        }
//    }


    public void shutdown() {
        try {
//            in.close();
//            out.close();
            if (this.socket.isClosed()) {
                this.socket.close();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong...");
        }
    }
}
