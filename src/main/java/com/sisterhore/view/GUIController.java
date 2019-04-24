package com.sisterhore.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import com.sisterhore.controller.Controller;
import com.sisterhore.controller.Operation;
import com.sisterhore.controller.OperationType;
import com.sisterhore.util.Serializer;
import com.sisterhore.version.Version;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
      } catch (UnknownHostException e){
        e.printStackTrace();
      } catch (IOException e){
        e.printStackTrace();
      }
    });
  }

  private Operation getOperation(String oldValue, String newValue, Version version) {    
    OperationType operationType = OperationType.DELETE;
    if (oldValue.length() < newValue.length()){
      operationType = OperationType.INSERT;  
    } 

    int index = 0;
    char value = ' ';
    int shortestLength = oldValue.length() > newValue.length()? newValue.length(): oldValue.length();

    int i = 0;
    for (; i < shortestLength; i++){
      if (oldValue.charAt(i) != newValue.charAt(i)){
        index = i;
        if (operationType == OperationType.INSERT){
          value = newValue.charAt(i);
        } else {
          value = oldValue.charAt(i);
        }
        break;
      }
    }

    if (i == shortestLength){
      if (operationType == OperationType.INSERT){
        value = newValue.charAt(shortestLength);
      } else {
        value = oldValue.charAt(shortestLength);
      }
      index = shortestLength;
    }

    Operation operation = new Operation(operationType, value, index, version);
    
    return operation;
  }
  
  public void handleDocsTextChanged() {
    docs_textfield.textProperty().addListener((observable, oldValue, newValue) -> {
      this.controller.versionVector.incrementLocalVersion();
      Version version = this.controller.versionVector.getLocalVersion();
      Operation operation = this.getOperation(oldValue, newValue, version);
      try {
        byte[] operationData = Serializer.serialize(operation);
        String operationDataString = Base64.getEncoder().encodeToString(operationData);
        this.controller.broadcast(operationDataString);
      } catch(IOException e){
        e.printStackTrace();
      }
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