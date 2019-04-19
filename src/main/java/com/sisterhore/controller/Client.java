package com.sisterhore.controller;

/*
 * Copyright (c) 2010-2019 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class Client extends WebSocketClient {
  private Controller controller;

  public Client(Controller controller, String uri, Map<String,String> httpHeaders) throws URISyntaxException {
    super(new URI(uri), new Draft_6455(), httpHeaders);
    this.setReuseAddr(true);
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

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    System.out.println("CLIENT: You are connected to Server: " + getURI() + "\n");
  }

  @Override
  public void onMessage(String message) {
    System.out.println("CLIENT "+getURI()+":"+ message);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    System.out.println("CLIENT: You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason + "\n");
    this.controller.deletePeer(getURI().toString());
  }
  
  @Override
  public void onError(Exception ex) {    
    System.out.println("CLIENT: Exception occured ...\n" + ex + "\n");
  }

}