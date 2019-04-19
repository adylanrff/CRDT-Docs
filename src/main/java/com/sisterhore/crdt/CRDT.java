package com.sisterhore.crdt;

import java.util.ArrayList;
import java.util.Arrays;

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
    ArrayList<Integer> posBefore = this.findPosBefore(index);
    ArrayList<Integer> posAfter = this.findPosAfter(index);
    ArrayList<Integer> newPos;

    posBefore = this.struct.get(index-1).getPosition();
    posAfter = this.struct.get(index).getPosition();
  }

  /**
   * @param index index where new char will be inserted
   * @return the position before
   *
   * For example, string ['l','o','r','e','m',' ','i','p','s','u','m']
   * We want to insert character 'x' between 'o' and 'r',
   * which means between position 1 and 2 (starting from 0)
   * and we will insert 'x' on position 2.
   * So, we need to find position before position 2, that is 1.
   */
  private ArrayList<Integer> findPosBefore(int index) {
    Char charBefore = this.struct.get(index-1);
    return charBefore.getPosition();
  }

  private ArrayList<Integer> findPosAfter(int index) {
    Char charAfter = this.struct.get(index);
    if (charAfter == null) {
      return new ArrayList<>();
    }
    return charAfter.getPosition();
  }

  private ArrayList<Integer> generatePosBetween(ArrayList<Integer> posBefore, ArrayList<Integer> posAfter, ArrayList<Integer> posResult) {
    int level1 = posBefore.get(0);
    int level2 = posAfter.get(0);

    if (level1 - level2 > 1) {
      int newPos = level1 + 1; // Will be replaced later
      posResult.add(newPos);
      return posResult;
    } else if (level1 - level2 == 1) {
      posResult.add(level1);
      ArrayList<Integer> newPosBefore = (ArrayList) posBefore.clone();
      newPosBefore.remove(0);
      return this.generatePosBetween(newPosBefore, posAfter, posResult);
    }
    return posResult;
  }

}