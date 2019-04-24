// import com.sisterhore.socket.server.AbstractSocketServer;

package com.sisterhore.socket.server;

import com.sisterhore.socket.server.AbstractSocketServer;
import java.net.Socket;

public class TestServer extends AbstractSocketServer {

    public TestServer() {
        super();
    }

    @Override 
    public void onOpen(Socket conn) {
        System.out.println("Server connected");
    }

    @Override 
    public void onClose(Socket conn) {
        System.out.println("Server disconnected");
    }

    @Override 
    public void onMessage(Socket conn, String msg) {
        System.out.println("MSG: " + msg);
    }

    @Override 
    public void onError(Socket conn) {
        System.out.println("Error");
    }
}