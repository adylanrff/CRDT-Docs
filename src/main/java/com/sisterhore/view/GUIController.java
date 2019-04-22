package com.sisterhore.view;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sisterhore.controller.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * GUIView
 */
public class GUIController implements Initializable {

  @FXML
  private Button btn_connect;
  @FXML
  private TextField host_textfield;
  @FXML
  public TextField docs_textfield;

  private Controller controller;

  public void handleButtonClick() {
    btn_connect.setOnAction((event) -> {
      try {
        controller.connectToPeer(host_textfield.getText());
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    });
  }

  private int getChangedIndex(String oldValue, String newValue) {
    if (oldValue.length()==0){
      return 0;
    }
    int shortestLength = oldValue.length() > newValue.length()? newValue.length(): oldValue.length();
    for (int i = 0; i<shortestLength; i++){
      if (oldValue.charAt(i) != newValue.charAt(i)){
        return i;
      }
    }

    return shortestLength;
  }
  
  public void handleDocsTextChanged() {
    docs_textfield.textProperty().addListener((observable, oldValue, newValue) -> {
      System.out.println(this.getChangedIndex(oldValue, newValue));
    });
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    handleButtonClick();
    handleDocsTextChanged();
  }

}