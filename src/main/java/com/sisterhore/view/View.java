package com.sisterhore.view;
import com.sisterhore.controller.Controller;

/**
 * View
 */
public abstract class View {
  protected Controller controller;
  public View(Controller controller){
    this.controller = controller;
  }
  
  public abstract void start(String[] args);
  public abstract void stop();
}