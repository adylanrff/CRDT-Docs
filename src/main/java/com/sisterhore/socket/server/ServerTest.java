package com.sisterhore.socket.server;

import com.sisterhore.socket.server.TestServer;

public class ServerTest {
    public static void main(String[] args) {
        TestServer server = new TestServer();

        server.start(5000);
    }
}