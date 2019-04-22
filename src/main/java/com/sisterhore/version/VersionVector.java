package com.sisterhore.version;

import java.util.ArrayList;

public class VersionVector {
  private int siteId;
  private ArrayList<Version> struct;
  private Version localVersion;

  public VersionVector(int siteId) {
    this.siteId = siteId;
    this.struct = new ArrayList<>();
    this.localVersion = new Version();
    this.struct.add(this.localVersion);
  }



}
