package com.sisterhore.socket.client;

import java.io.IOException;

import com.sisterhore.socket.client.TestClient;

public class Test {
    public static void main(String[] args) {
        try {
            TestClient client = new TestClient("127.0.0.1", 8888);
            client.start();
            client.send("Huyu");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("IO Exc Error : "+ e);
        }
    }
}