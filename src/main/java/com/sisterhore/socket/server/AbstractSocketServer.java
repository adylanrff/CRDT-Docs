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
  public AbstractSocketServer(int port) {
    this.port = port;
  };

  public void start() {
    try {
      serverSocket = new ServerSocket(this.port);
    } catch (Exception e) {
      System.out.println("Error server socket handler " + e);      
    }
    while (true) {
      try {
        Socket connection = serverSocket.accept();
        this.onOpen(connection);
        new ClientHandler(this, connection).start();
      } catch (Exception e) {
        System.out.println("Error client handler " + e);
      }
    }
  }

  public void stop() throws IOException {
      serverSocket.close();
  }
  
  public abstract void onOpen(Socket conn);
  public abstract void onClose(Socket conn);
  public abstract void onMessage(Socket conn, String message);
  public abstract void onError(Socket conn, Exception ex);
}