package org.academiadecodigo.asynctomatics.concurrentchatserver.server;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ClientHandler implements Runnable {

    // fields
    private Socket clientSocket;
    private LinkedList<ClientHandler> list;
    private PrintWriter output;

    // constructor
    public ClientHandler(Socket clientSocket, LinkedList<ClientHandler> list) {
        this.clientSocket = clientSocket;
        this.list = list;
    }

    // method from Runnable interface
    @Override
    public void run() {

        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                // reads the line from the client and prints it to the server terminal
                String message = input.readLine();
                System.out.println(message);

                // broadcasts the message from the client to other clients terminal
                for (ClientHandler client : list) {
                    client.output.println(message);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
