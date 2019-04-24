package com.sisterhore.util;

import java.io.Serializable;

/**
 * Handshake
 */
public class Handshake implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public String host;
  public int port;
  public Handshake(String host, int port){
    this.host = host;
    this.port = port;
  }

  public static Handshake HandshakeNull(){
    return new Handshake("", -1);
  }
}