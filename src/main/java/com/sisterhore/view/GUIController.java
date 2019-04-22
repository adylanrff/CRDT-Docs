package com.sisterhore.view;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sisterhore.controller.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * GUIView
 */
public class GUIController {

  private static final String MY_FIRST_JAVA_FX_APP = "CRDT Collaborative Docs";

  @FXML
  private Button btn_connect;
  @FXML
  private TextField host_textfield;
  @FXML
  public TextField docs_textfield;
  
  private Controller controller;

  public void handleButtonClick() {
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        System.out.println("Hello World");
        try {
          controller.connectToPeer(host_textfield.getText());
        } catch (URISyntaxException e1) {
          e1.printStackTrace();
        }
      }
    };

    btn_connect.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

}