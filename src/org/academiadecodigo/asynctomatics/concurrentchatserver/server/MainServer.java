package org.academiadecodigo.asynctomatics.concurrentchatserver.server;

public class MainServer {

    public static void main(String[] args) {

        Server server = new Server(8081, 20);
        server.execute();
    }
}
