package CRDT;

public class Char {
  private int siteId;
  private char value;
  
  
  public Char(){

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

}