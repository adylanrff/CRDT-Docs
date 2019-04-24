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
  private String host;
  private int port;
  private PrintWriter out;
  private BufferedReader in;

  public AbstractSocketClient(String host, int port) throws UnknownHostException, IOException {
    this.host = host;
    this.port = port;
  }

  public String getAddress(){
    return this.host;
  }

  public void startConnection(String ip, int port) throws UnknownHostException, IOException {
    clientSocket = new Socket(ip, port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  public String sendMessage(String msg) throws IOException {
      out.println(msg);
      String resp = in.readLine();
      this.onMessage(resp);
      return resp;
  }

  public void stopConnection() throws IOException {
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