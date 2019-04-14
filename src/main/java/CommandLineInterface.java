import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import Controller.Controller;

public class CommandLineInterface extends Thread {
  Controller controller;

  private String getInput() throws IOException {
    BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
    String input = sysin.readLine();
    String str = input!=null?input:"";
    return str;
  }

  private boolean isUriValid(String message){
    return message.startsWith("ws://");
  }

  public CommandLineInterface(Controller controller) {
    this.controller = controller;
  }

  public void run() {
    System.out.print("Enter peer URI: ");
    try {
      String uriString = getInput();
      if(isUriValid(uriString)){
        this.controller.connectToPeer(uriString);
      }
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

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
}