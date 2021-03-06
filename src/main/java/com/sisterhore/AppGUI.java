package com.sisterhore;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import com.sisterhore.controller.Controller;
import com.sisterhore.view.GUIController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppGUI extends Application {

  private static final String MY_FIRST_JAVA_FX_APP = "CRDT Docs";
  private static Controller controller = null;
  private static GUIController guiController = null;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle(MY_FIRST_JAVA_FX_APP);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/crdt-docs.fxml"));
    AnchorPane anchorPane = loader.load();
    Scene scene = new Scene(anchorPane);
    guiController = loader.getController();
    guiController.setController(controller);
    controller.startServer();
    controller.setGuiController(guiController);
    primaryStage.setScene(scene);
    primaryStage.setOnCloseRequest((event)->{
      System.exit(0);
    });
    primaryStage.show();
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    int port = args.length != 0 ? Integer.parseInt(args[0]) : 8887;
    try {
      System.out.println("TEST");
      controller = new Controller(port);
      launch(args);
    } catch (UnknownHostException e) {
      System.out.println("Exception occured: " + e);
      System.exit(1);
    }
  }

}
