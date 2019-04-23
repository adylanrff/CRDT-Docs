package com.sisterhore.crdt;

import java.util.ArrayList;

public class Char {
  private String siteId;
  private char value;
  private ArrayList<Integer> position;

  public Char(String siteId, char value, ArrayList<Integer> position){
    this.siteId = siteId;
    this.value = value;
    this.position = position;
  }
  /**
   * @return the siteId
   */
  public String getSiteId() {
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

  public String printPosition() {
    String output = "";
    for (int i = 0; i < this.position.size(); i++) {
      output += this.position.get(i).toString();
      output += " ";
    }
    return output;
  }

  /**
   * @param value the value to set
   */
  public void setValue(char value) {
    this.value = value;
  }

  /**
   * @param siteId the siteId to set
   */
  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  /**
   * @param position the position to set
   */
  public void setPosition(ArrayList<Integer> position) { this.position = position; }

}