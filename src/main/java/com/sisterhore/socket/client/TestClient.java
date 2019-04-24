package com.sisterhore.socket.client;

import java.io.IOException;
import java.net.UnknownHostException;

import com.sisterhore.socket.client.AbstractSocketClient;

public class TestClient extends AbstractSocketClient {

    public TestClient(String host, int port) throws UnknownHostException, IOException {
        super(host, port);
    }

    @Override
    public void onOpen() {
        System.out.println("Client connected");
    }
    
    @Override
    public void onMessage(String msg) {
        System.out.println("Response: " + msg);
    }
    
    @Override 
    public void onClose() {
        System.out.println("Client disconnected");
    }

    @Override 
    public void onError(Exception ex) {
        System.out.println(ex);
    }
}