package com.sisterhore.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Map;

import com.sisterhore.socket.client.AbstractSocketClient;
import com.sisterhore.util.Handshake;
import com.sisterhore.util.Serializer;

public class Client extends AbstractSocketClient {
  private Controller controller;

  public Client(Controller controller, String uri) throws URISyntaxException {
    super(uri.split(":")[0], Integer.parseInt(uri.split(":")[1]));
    this.setController(controller);
  }

  /**
   * @return the controller
   */
  public Controller getController() {
    return controller;
  }

  /**
   * @param controller the controller to set
   */
  public void setController(Controller controller) {
    this.controller = controller;
  }

  // @Override
  // public void onOpen(ServerHandshake handshakedata) {
  // System.out.println("CLIENT: You are connected to Server: " + getURI() +
  // "\n");
  // }

  @Override
  public void onMessage(String message) {
    Operation operation = Operation.getOperation(message);
    System.out.println("CLIENT :" + this.host + ":" + this.port + message);
    System.out.println(operation);
  }

  // @Override
  // public void onClose(int code, String reason, boolean remote) {
  // System.out.println("CLIENT: You have been disconnected from: " + getURI() +
  // "; Code: " + code + " " + reason + "\n");
  // this.controller.deletePeer(getURI().toString());
  // }

  @Override
  public void onError(Exception ex) {
    System.out.println("CLIENT: Exception occured ...\n" + ex + "\n");
  }

  @Override
  public void onOpen() {
    System.out.println("CLIENT: You are connected to Server: " + getURI() + "\n");
    Handshake handshake = new Handshake(this.host, this.controller.getServerPort());
    byte[] handshakeData;
    try {
      handshakeData = Serializer.serialize(handshake);
      String operationDataString = Base64.getEncoder().encodeToString(handshakeData);
      this.send(operationDataString);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }

  @Override
  public void onClose() {
    System.out.println("CLIENT: You have been disconnected from: " + getURI());
    this.controller.deletePeer(getURI().toString());
  }

}