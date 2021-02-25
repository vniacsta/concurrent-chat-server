package org.academiadecodigo.asynctomatics.concurrentchatserver.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    // fields
    private String address;
    private int port;
    private ExecutorService fixedPool;
    private Socket clientSocket;
    private boolean loggedIn;
    private String username;

    // constructor
    public Client(String address, int port, int numThreads) {
        this.address = address;
        this.port = port;
        fixedPool = Executors.newFixedThreadPool(numThreads);
        loggedIn = false;
    }

    public void execute() {

        try {
            // creates connection with the server
            clientSocket = new Socket(InetAddress.getByName(address), port);
            System.out.println("Connected to server!");

            // creating a thread to read messages
            ReadHandler readHandler = new ReadHandler(clientSocket, this);
            fixedPool.submit(readHandler);

            // creating a thread to send messages
            SendHandler sendHandler = new SendHandler(clientSocket, this);
            fixedPool.submit(sendHandler);


        } catch (UnknownHostException e) {
            System.err.println("Error: Invalid host: " + address);

        } catch (IOException e) {
            System.err.println("Error: Network Failure: " + e.getMessage());
        }
    }

    // getter
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUsername() {
        return username;
    }

    // setter
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
