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
  void onMessage(Socket socket, String message) {
    socketServer.onMessage(socket, message);
  }


  @Override
  void onClose(Socket conn) {
    socketServer.onClose(conn);
  }

  @Override
  void onError(Socket conn, Exception ex) {
    socketServer.onError(conn, ex);
  }

  @Override
  void onOpen(Socket socket) {
    socketServer.onOpen(conn);
  }
}