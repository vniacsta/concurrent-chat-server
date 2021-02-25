package org.academiadecodigo.asynctomatics.concurrentchatserver.client;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ReadHandler implements Runnable {

    // fields
    private Socket clientSocket;
    private Client client;

    // constructor
    public ReadHandler(Socket clientSocket, Client client) {
        this.clientSocket = clientSocket;
        this.client = client;
    }

    @Override
    public void run() {

        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (client.isLoggedIn()) {

                // reads the line
                String message = input.readLine();

                if (message == null) {
                    System.out.println("Connection closed ");
                    clientSocket.close();
                    System.exit(0);
                }

                // don't broadcast message to sender
                StringTokenizer checkUsername = new StringTokenizer(message);
                String sender = checkUsername.nextToken();
                System.out.println(sender);

                if (client.getUsername().equals(sender)) {
                    break;
                }

                // if not sender, print the message to the client terminal
                System.out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            cleanUp();
        }
    }

    private void cleanUp() {

        try {
            if (clientSocket != null) {
                System.out.println("Closing the connection...");
                clientSocket.close();
            }

        } catch (IOException ex) {
            System.err.println("Error: Network Failure: " + ex.getMessage());
        }
    }
}
