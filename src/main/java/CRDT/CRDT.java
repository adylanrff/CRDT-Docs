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

  /**
   * @param value value of character to be inserted
   * @param index index where the value will be inserted
   */
  public void localInsert(char value, int index) {
//    static Char char =
  }

  public void generateChar(char value, int index) {
    ArrayList<Integer> posBefore;
    ArrayList<Integer> posAfter;
    ArrayList<Integer> newPos;

    posBefore = this.struct.get(index-1).getPosition();
    posAfter = this.struct.get(index).getPosition();
  }

  public ArrayList<Integer> generatePos(ArrayList<Integer> posBefore, ArrayList<Integer> posAfter) {
    /**
     * Misalkan [1] dan [1,5] = [1,2,5]
     */
    if (posBefore.get(0) - posAfter.get(1) > 1) {

    }
    ArrayList<Integer> result = new ArrayList<>();
    return result;
  }

}