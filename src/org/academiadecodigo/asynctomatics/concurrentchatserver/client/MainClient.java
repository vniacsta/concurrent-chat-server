package org.academiadecodigo.asynctomatics.concurrentchatserver.client;

public class MainClient {

    public static void main(String[] args) {

        Client client = new Client("localhost", 8081, 40);
        client.execute();
    }
}
