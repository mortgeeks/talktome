package sample;


import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

import static java.lang.System.out;


public class Server {

    private int PORT =8888;

    public Server() throws UnknownHostException, SocketException {

    }
    public void start() throws IOException {
        out.println("Peer Chat server is now running");
            ServerSocket listener = new ServerSocket(PORT);

        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }




 private static class Handler extends Service<String>{
     private String name,ip=null;
     private Socket socket;
     private BufferedReader in;
     private PrintWriter out;
     private SqlLiteManager sqlLiteManager = new SqlLiteManager();
     public Handler(Socket socket){
        this.socket = socket;
        sqlLiteManager.createNewDatabase("clients");
        sqlLiteManager.connect();
        sqlLiteManager.createNewTable();
        }
     public void print(boolean x)
     {
         out.println(x);
     }

     public void print(String x)
     {
         out.println(x);
     }

     @Override
     protected Task<String> createTask() {
         return new Task<String>() {
             @Override
             protected String call() throws Exception {
                 try {


                     in = new BufferedReader(new InputStreamReader(
                             socket.getInputStream()));
                     out = new PrintWriter(socket.getOutputStream(), true);

                     while (true) {
                         out.println("CONNECT");
                         name = in.readLine();
                         for (int i = 0;i<name.length();i++){
                             if (name.charAt(i)==':'){
                                 ip=name.substring(i+1);
                             }
                         }
                         synchronized (sqlLiteManager) {
                             if (sqlLiteManager.search(name,ip)){
                                 print(name + " joined");
                                 break;
                             } else {
                                 sqlLiteManager.insertClient(name,ip);
                                 //ask client
                                 print(name + " joined");
                                 break;
                             }

                         }
                     }
                     out.println("HANDSHAKE");


                     while (true) {
                         String input = in.readLine();
                         if (input == null) {
                             return null;
                         }
                         sqlLiteManager.insertMessage(name,input);
                         out.append("STREAM " + name + ": " + input);
                     }
                 } catch (IOException e) {
                     out.println(e);
                 } finally {
                     try {
                         socket.close();
                     } catch (IOException e) {
                     }
                 }
                return null;
             }
         };
     }
 }
}