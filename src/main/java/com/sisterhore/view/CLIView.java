package com.sisterhore.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import com.sisterhore.controller.Controller;

public class CLIView extends View implements Runnable {

  private String getInput() throws IOException {
    BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
    String input = sysin.readLine();
    String str = input != null ? input : "";
    return str;
  }

  private boolean isUriValid(String message) {
    return message.startsWith("ws://");
  }

  public CLIView(Controller controller) {
    super(controller);
  }

  private void runChatLoop() {
    if (this.controller != null) {
      while (true) {
        String message = "";
        try {
          message = getInput();
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (message != "") {
          controller.sendMessage(message);
        }
      }
    } else {
      System.err.println("Failed to create node");
    }
  }

  private void promptPeerUri() {
    System.out.print("Enter peer URI: ");
    try {
      String uriString = getInput();
      if (isUriValid(uriString)) {
        System.out.println(uriString);
        this.controller.connectToPeer(uriString);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    this.start(null);
  }

  @Override
  public void stop() {
    System.exit(0);
  }

  @Override
  public void start(String[] args) {
    try {
      this.controller.startServer();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    promptPeerUri();
    runChatLoop();
  }
}