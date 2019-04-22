package com.sisterhore.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.sisterhore.crdt.CRDT;
import com.sisterhore.version.VersionVector;

public class Controller {
  private ArrayList<Client> clients;
  private Server server;
  private CRDT crdt;
  private VersionVector versionVector;
  private int serverPort;
  private ArrayList<String> peers;

  public Controller(int serverPort) throws UnknownHostException {
    this.setServerPort(serverPort);
    this.serverPort = serverPort;
    this.peers = new ArrayList<String>();
    this.clients = new ArrayList<Client>();
    this.versionVector = new VersionVector(1);
  }

  public void startServer() throws UnknownHostException {
    this.server = new Server(this, this.serverPort);
    this.server.start();
    System.out.println("Server started");
  }
  /**
   * @return the serverPort
   */
  public int getServerPort() {
    return serverPort;
  }

  /**
   * @param serverPort the serverPort to set
   */
  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public void connectToPeer(String uri) throws URISyntaxException {
    if (!this.peers.contains(uri)) {
      Map<String, String> httpHeaders = new HashMap<String, String>();
      httpHeaders.put("server_port", String.valueOf(this.serverPort));
      Client client = new Client(this, uri, httpHeaders);
      // client.connect();
      this.clients.add(client);
      this.insertPeer(uri);
      client.connect();
    }
  }

  public void closeClient(String uri) {
    for (Client client : clients) {
      if (client.getURI().toString() == uri){
        client.close();
        clients.remove(client);
      }
    }
  }

  public void closeServer() throws IOException, InterruptedException {
    this.server.stop();
  }
  
  public void sendMessage(String message){
    for (Client client : clients) {
      client.send(message);
    }
  }

  public void insertPeer(String uri){
    this.peers.add(uri);
  }

  public void deletePeer(String uri){
    this.peers.remove(uri);
  }

  public boolean isContainPeer(String uri){
    return this.peers.contains(uri);
  }

  public void handleRemoteOperation(Operation operation) {
//    if (this.versionVector.isApplied(operation))
  }

  public void handleRemoteInsert(){

  }

  public void handleRemoteDelete(){
    
  }

  public void handleLocalInsert(){

  }
}