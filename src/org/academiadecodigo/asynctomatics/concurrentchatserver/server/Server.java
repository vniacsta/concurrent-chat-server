package org.academiadecodigo.asynctomatics.concurrentchatserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // fields
    private int port;
    private ExecutorService fixedPool;
    private List<ClientHandler> list;

    // constructor
    public Server(int port, int numThreads) {
        this.port = port;
        fixedPool = Executors.newFixedThreadPool(numThreads);
        list = new LinkedList<>();
    }

    public void execute() {

        try {
            // creating connection
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Prepared for connection...");

            while (true) {
                // connecting
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getInetAddress() + " connected!");

                // creating object to handle client requests
                ClientHandler clientHandler = new ClientHandler(clientSocket, (LinkedList<ClientHandler>) list);
                // add new client handler to the list
                list.add(clientHandler);
                // starting my thread pool
                fixedPool.submit(clientHandler);
            }

        } catch (IOException e) {
            e.printStackTrace();
            fixedPool.shutdown();
        }
    }
}
