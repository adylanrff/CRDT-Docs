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
  public VersionVector versionVector;
  private int serverPort;
  private ArrayList<String> peers;

  public Controller(int serverPort) throws UnknownHostException {
    this.serverPort = serverPort;
    this.peers = new ArrayList<String>();
    this.clients = new ArrayList<Client>();
    String uri = String.format("ws://localhost:%d", this.serverPort);
    this.versionVector = new VersionVector(uri);
  }

  public void startServer() throws UnknownHostException {
    this.server = new Server(this, this.serverPort);
    new Thread(this.server).start();
    System.out.println("Server started");
  }
  /**
   * @return the serverPort
   */
  public int getServerPort() {
    return serverPort;
  }

  public void connectToPeer(String uri) throws URISyntaxException, UnknownHostException, IOException {
    if (!this.peers.contains(uri)) {
      Client client = new Client(this, uri);
      this.insertPeer(uri);
      this.clients.add(client);
      client.connect();
    }
  }

  public void closeClient(String uri) throws IOException {
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
  
  public void sendMessage(String message) throws IOException {
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

  public void handleLocalDelete(){

  }
}