package com.sisterhore.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * SocketClient
 */
public abstract class AbstractSocketClient {
  private Socket clientSocket;
  protected String host;
  protected int port;
  private PrintWriter out;
  private BufferedReader in;

  public AbstractSocketClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String getURI(){
    return String.format("%s:%d", this.host, this.port);
  }

  public String getAddress(){
    return this.host;
  }

  public void connect() throws UnknownHostException, IOException {
    clientSocket = new Socket(this.host, this.port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    this.onOpen();
  }

  public void send(String msg) throws IOException {
      out.println(msg);
  }

  public void close() throws IOException {
      in.close();
      out.close();
      clientSocket.close();
      this.onClose();
  }

  public abstract void onOpen();
  
  public abstract void onMessage(String message);

  public abstract void onClose();
  
  public abstract void onError(Exception ex);
}