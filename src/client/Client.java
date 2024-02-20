package client;

import javax.management.ObjectName;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    public Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private int port;
    private String host;
    Scanner scanner;
    private ObjectInputStream obin;
    private ObjectOutputStream objout;

    public Client(int port) {
        this("127.0.0.1", port);
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

    }


    @Override
    public void run() {
        try {
            this.client = new Socket("127.0.0.1", this.port);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            scanner = new Scanner(System.in);

            objout = new ObjectOutputStream(this.client.getOutputStream());
            obin = new ObjectInputStream(this.client.getInputStream());

            System.out.println("Enter username: ");
            String username = scanner.next();

            Handler handler = new Handler(this);
            Thread thread = new Thread(handler);
            thread.start();

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("message: " + message);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void shutdown() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (client != null && !client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            System.out.println("Error while shutting down: " + e.getMessage());
        }
    }

}
