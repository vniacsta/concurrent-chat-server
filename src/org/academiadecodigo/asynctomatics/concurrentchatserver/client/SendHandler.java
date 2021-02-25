package org.academiadecodigo.asynctomatics.concurrentchatserver.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SendHandler implements Runnable {

    // fields
    private Socket clientSocket;
    private Client client;

    // constructor
    public SendHandler(Socket clientSocket, Client client) {
        this.clientSocket = clientSocket;
        this.client = client;
    }

    @Override
    public void run() {

        try {

            Scanner scanner = new Scanner(System.in);
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // saves user input and username
            String userInput;

            // save username
            System.out.print("Enter your username: ");
            userInput = scanner.nextLine();
            client.setUsername(userInput);
            client.setLoggedIn(true);

            while (client.isLoggedIn()) {

                String userDeclaration = "@" + client.getUsername() + ": ";
                System.out.print(userDeclaration);

                // reads the message the user wrote
                userInput = scanner.nextLine();

                // sends message
                output.println(userDeclaration + userInput);

                // if client enters quit, the connection is closed
                if (userInput.equals("/quit")) {
                    clientSocket.close();
                    System.exit(0);
                    break;
                }
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
