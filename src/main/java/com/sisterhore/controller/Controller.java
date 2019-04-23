package com.sisterhore.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import com.sisterhore.crdt.CRDT;
import com.sisterhore.crdt.Char;
import com.sisterhore.util.Serializer;
import com.sisterhore.version.Version;
import com.sisterhore.version.VersionVector;
import com.sisterhore.view.GUIController;

public class Controller {
  private ArrayList<Client> clients;
  private Server server;
  private int serverPort;
  private ArrayList<String> peers;
  private CRDT crdt;
  public VersionVector versionVector;
  private ArrayList<Operation> deletionBuffer;
  private GUIController guiController;

  public Controller(int serverPort) throws UnknownHostException {
    this.serverPort = serverPort;
    this.peers = new ArrayList<>();
    this.clients = new ArrayList<>();
    String uri = String.format("ws://localhost:%d", this.serverPort);
    this.versionVector = new VersionVector(uri);
    this.deletionBuffer = new ArrayList<>();
    this.crdt = new CRDT(uri);
  }

  /**
   * @return the guiController
   */
  public GUIController getGuiController() {
    return guiController;
  }

  public CRDT getCrdt() { return this.crdt; }

  /**
   * @param guiController the guiController to set
   */
  public void setGuiController(GUIController guiController) {
    this.guiController = guiController;
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
  
  public boolean sendMessage(Operation operation){
    try {
      byte[] operationData = Serializer.serialize(operation);
      String operationDataString = Base64.getEncoder().encodeToString(operationData);
      for (Client client : clients) {
        System.out.println(String.format("Client: %s", client.getURI()));
        client.send(operationDataString);
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
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
    System.out.println("Handle Remote");
    if (this.versionVector.isApplied(operation.getVersion())) return;
    if (operation.getOperationType() == OperationType.INSERT)
      this.applyOperation(operation);
    else if (operation.getOperationType() == OperationType.DELETE)
      this.deletionBuffer.add(operation);
    this.processDeletionBuffer();
  }

  public void applyOperation(Operation operation) {
    if (operation.getOperationType() == OperationType.DELETE)
      this.crdt.localDelete(operation.getIndex());
    else if (operation.getOperationType() == OperationType.INSERT)
      this.crdt.localInsert(operation.getCharacterUsed(), operation.getIndex());

    this.versionVector.update(operation.getVersion());
  }

  public void processDeletionBuffer() {
    for (int i = 0; i < this.deletionBuffer.size(); i++) {
      Operation delete = this.deletionBuffer.get(i);
      Version deleteVersion = delete.getVersion();
      Char deleteChar = this.crdt.getChar(delete.getIndex());
      boolean isInserted = this.versionVector.isApplied(delete.getVersion());

      // LOG
      System.out.println(String.format("PROCESS BUFFER, %b", isInserted));
      System.out.println(String.format("VERSION: %s %d", deleteVersion.getSiteId(), deleteVersion.getCounter()));
      System.out.println(String.format("CHARACTER: %s %c %s", deleteChar.getSiteId(), deleteChar.getValue(), deleteChar.printPosition()));

      if (isInserted) {
        this.applyOperation(delete);
        this.deletionBuffer.remove(i);
      } else {
        this.applyOperation(delete);
        this.deletionBuffer.remove(i);
      }
    }
  }

  public String getCRDTContent() {
    return this.crdt.getStructContent();
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