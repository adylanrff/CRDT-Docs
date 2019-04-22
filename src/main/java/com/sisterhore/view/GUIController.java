package com.sisterhore.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * GUIView
 */
public class GUIController  {

  private static final String MY_FIRST_JAVA_FX_APP = "CRDT Collaborative Docs";
  
  @FXML
  private Button btn_connect;
  @FXML
  private TextField host_textfield;
  @FXML
  public TextField docs_textfield;

  public void handleButtonClick(){
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
      @Override 
      public void handle(MouseEvent e) { 
         System.out.println("Hello World"); 
         btn_connect.setText(host_textfield.getText());  
      } 
   };

    btn_connect.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }


}