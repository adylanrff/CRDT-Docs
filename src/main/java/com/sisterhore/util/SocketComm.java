package com.sisterhore.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * SocketWriter
 */
public class SocketComm {
  public static void write(Socket conn, String message) throws IOException {
    DataOutputStream out = new DataOutputStream(conn.getOutputStream());     
    out.writeUTF(message);
  }

  public static String read(Socket conn) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String message = in.readLine();
    return message;
  }
}