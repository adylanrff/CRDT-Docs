package com.sisterhore.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * SocketWriter
 */
public class SocketComm {
  public static void write(Socket conn, String message) throws IOException {
    PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
    out.println(message);
  }

  public static String read(Socket conn) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String message = in.readLine();
    in.close();
    return message;
  }
}