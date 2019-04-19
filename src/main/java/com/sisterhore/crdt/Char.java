package com.sisterhore.crdt;

import java.util.ArrayList;

public class Char {
  private int siteId;
  private char value;
  private ArrayList<Integer> position;

  public Char(int siteId, char value, ArrayList<Integer> position){
    this.siteId = siteId;
    this.value = value;
    this.position = position;
  }
  /**
   * @return the siteId
   */
  public int getSiteId() {
    return siteId;
  }

  /**
   * @return the value
   */
  public char getValue() {
    return value;
  }

  /**
   * @return the position
   */
  public ArrayList<Integer> getPosition() { return position; }

  /**
   * @param value the value to set
   */
  public void setValue(char value) {
    this.value = value;
  }

  /**
   * @param siteId the siteId to set
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }

  /**
   * @param position the position to set
   */
  public void setPosition(ArrayList<Integer> position) { this.position = position; }

}