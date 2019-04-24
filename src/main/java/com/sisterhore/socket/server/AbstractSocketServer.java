package com.sisterhore.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SocketServer
 */
public abstract class AbstractSocketServer {
  private ServerSocket serverSocket;
  private int port;
  public AbstractSocketServer() {
  
  };

  public void start(int port) {
    try {
      serverSocket = new ServerSocket(port);
    } catch (Exception ex) {
      
    }
    while (true)
      new ClientHandler(this, serverSocket.accept()).start();
  }

  public void stop() throws IOException {
      serverSocket.close();
  }
  
  public abstract void onOpen(Socket conn);
  public abstract void onClose(Socket conn);
  public abstract void onMessage(Socket conn, String message);
  public abstract void onError(Socket conn, Exception ex);
}