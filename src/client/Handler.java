package client;

import java.io.*;

public class Handler implements Runnable{
    private Client client;
    BufferedReader inReader;
    private ObjectInputStream obin;
    private ObjectOutputStream objout;

    public Handler(Client client) {
        this.inReader = new BufferedReader(new InputStreamReader(System.in));
        this.client = client;


    }


    @Override
    public void run() {
        try {
            objout = new ObjectOutputStream(client.client.getOutputStream());
            obin = new ObjectInputStream(client.client.getInputStream());

            while (!this.client.client.isClosed()) {

                Data data = new Data(1, "test");
                objout.writeObject(data);
//                Object tst = obin.readObject();
//
//                System.out.println(tst);

//                String message = inReader.readLine();
//                this.client.sendMessage(message);
            }
        } catch (IOException e) {
            this.client.shutdown();
        }
    }
}
