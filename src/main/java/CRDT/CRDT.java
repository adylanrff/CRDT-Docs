package CRDT;

import java.util.ArrayList;

public class CRDT {
  private int siteId;
  private ArrayList<Char> struct;

  public CRDT(int siteId){
    this.siteId = siteId;
    this.struct = new ArrayList<Char>();
  }
  /**
   * @return the siteId
   */
  public int getSiteId() {
    return siteId;
  }

  /**
   * @param siteId the siteId to set
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }
}