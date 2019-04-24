package com.sisterhore.socket.server;

import java.net.Socket;

/**
 * ClientHandler
 */
public class ClientHandler extends AbstractClientHandler {
  private AbstractSocketServer socketServer;
  public ClientHandler(AbstractSocketServer socketServer, Socket socket){
    super(socket);
    this.socketServer = socketServer;
  }

  @Override
  public void onMessage(Socket conn, String message) {
    socketServer.onMessage(conn, message);
  }


  @Override
  public void onClose(Socket conn) {
    socketServer.onClose(conn);
  }

  @Override
  public void onError(Socket conn, Exception ex) {
    socketServer.onError(conn, ex);
  }

  @Override
  public void onOpen(Socket conn) {
    socketServer.onOpen(conn);
  }
}