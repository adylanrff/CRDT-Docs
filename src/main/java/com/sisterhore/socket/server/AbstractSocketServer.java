package com.sisterhore.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

/**
 * SocketServer
 */
public abstract class AbstractSocketServer implements Runnable {
  private ServerSocket serverSocket;
  private int port;
  private ArrayList<Socket> connections;

  public AbstractSocketServer(int port) {
    this.port = port;
    this.connections = new ArrayList<Socket>();
  };

  public void run() {
    try {
      serverSocket = new ServerSocket(this.port);
    } catch (Exception e) {
      System.out.println("Error server socket handler " + e);      
    }
    while (true) {
      try {
        Socket connection = serverSocket.accept();
        this.connections.add(connection);
        this.onOpen(connection);
        new ClientHandler(this, connection).start();
      } catch (Exception e) {
        System.out.println("Error client handler " + e);
      }
    }
  }

  public ArrayList<Socket> getConnections(){
    return this.connections;
  }

  public void stop() throws IOException {
      serverSocket.close();
  }
  
  public abstract void onOpen(Socket conn);
  public abstract void onClose(Socket conn);
  public abstract void onMessage(Socket conn, String message);
  public abstract void onError(Socket conn, Exception ex);
}