package sample;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Client extends Service<Void> {

    String serverAddress = "";
    String clientip;
    String name= "";
    int PORT = 8888;
    BufferedReader in;
    PrintWriter out;
    SqlLiteManager sqlm;

    public Client(String serverAddress,String clientIp,String client) {
        this.serverAddress = serverAddress;
        this.name = client;
        sqlm = new SqlLiteManager();
        sqlm.connect();
        this.clientip = clientIp;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                serverAddress = getserveraddr();
                Socket socket = new Socket(serverAddress, PORT);
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);



                while (true) {
                    String line = in.readLine();
                    if (line == null) {
                        continue;
                    }
                    if (line.startsWith("CONNECT")) {
                        out.println(getName());
                    } else if (line.startsWith("HANDSHAKE")) {

                    } else if (line.startsWith("STREAM")) {


                            sqlm.insertMessage(name,line.substring(7));


                        return null;
                    }
                }
            }


        };

    }
    private String getserveraddr() {
        return this.serverAddress;
    }
    private String getName(){
        return this.name+":"+this.clientip;
    }
    public void sendMessage(String message){
        out.println(message);
    }
}