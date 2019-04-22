package com.sisterhore.version;

public class Version {
  private int siteId;
  private int counter;

  public Version(int siteId) {
    this.siteId = siteId;
    this.counter = 0;
  }

  public int getSiteId() { return this.siteId; }

  public int getCounter() { return this.counter; }
}
