package com.sisterhore.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class AbstractClientHandler extends Thread {
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public AbstractClientHandler(Socket socket) {
    this.clientSocket = socket;
    // this.onOpen(socket);
  }

  public void run() {
    try {
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        System.out.println("Print this!");
        // if (".".equals(inputLine)) {
        //   out.println("bye");
        //   break;
        // }
        // out.println(inputLine);
        this.onMessage(this.clientSocket, inputLine);
      }
      in.close();
      out.close();
      System.out.println(clientSocket);
      this.onClose(this.clientSocket);
      clientSocket.close();
    } catch (IOException e) {
      // e.printStackTrace();
      this.onError(this.clientSocket, e);
    }
  }

  public void send(String message){
    out.println(message);
  }

  public abstract void onOpen(Socket socket);

  public abstract void onClose(Socket conn);

  public abstract void onMessage(Socket socket, String message);

  public abstract void onError(Socket conn, Exception ex);
}