package com.sisterhore.version;

import java.io.Serializable;

public class Version implements Serializable {
  private String siteId;
  private int counter;

  public Version(String siteId) {
    this.siteId = siteId;
    this.counter = 0;
  }

  public String getSiteId() { return this.siteId; }

  public int getCounter() { return this.counter; }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

  public void incrementCounter(){
    this.counter++;
  };
}
