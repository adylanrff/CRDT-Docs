package Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import CRDT.CRDT;

public class Controller {
  private Client client;
  private Server server;
  private CRDT crdt;
  private int serverPort;
  private ArrayList<String> peers;

  public Controller(int serverPort) throws UnknownHostException {
    this.setServerPort(serverPort);
    this.server = new Server(this, serverPort);
    this.server.start();
    this.peers = new ArrayList<>();
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
      this.client = new Client(this, uri, httpHeaders);
      this.client.connect();
      this.insertPeer(uri);
    }
  }

  public void closeClient() {
    this.client.close();
  }

  public void closeServer() throws IOException, InterruptedException {
    this.server.stop();
  }
  
  public void sendMessage(String message){
    if (this.peers.size()>1){
      this.server.broadcast(message);
    } else {
      this.client.send(message);
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

  public void handleRemoteInsert(){

  }

  public void handleRemoteDelete(){
    
  }

  public void handleLocalInsert(){

  }
}