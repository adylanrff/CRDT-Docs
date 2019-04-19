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
    Char newChar = this.generateChar(value, index);
    this.struct.add(index, newChar);
  }

  private Char generateChar(char value, int index) {
    ArrayList<Integer> posBefore = this.findPosBefore(index);
    this.printPosition(posBefore);

    ArrayList<Integer> posAfter = this.findPosAfter(index);
    this.printPosition(posAfter);

    ArrayList<Integer> posResult = new ArrayList<>();
    ArrayList<Integer> newPos = this.generatePosBetween(posBefore, posAfter, posResult);
    this.printPosition(newPos);

    return new Char(this.siteId, value, newPos);
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
    if ((this.struct.size() == 0) || (index == 0)) {
      return new ArrayList<>();
    }
    Char charBefore = this.struct.get(index-1);
    return charBefore.getPosition();
  }

  private ArrayList<Integer> findPosAfter(int index) {
    if (this.struct.size() == 0) {
      return new ArrayList<>();
    } else if (index == this.struct.size()) {
      ArrayList<Integer> end = new ArrayList<>();
      end.add(index);
      return end;
    }
    Char charAfter = this.struct.get(index);
    if (charAfter == null) {
      return new ArrayList<>();
    }
    return charAfter.getPosition();
  }

  private ArrayList<Integer> generatePosBetween(ArrayList<Integer> posBefore, ArrayList<Integer> posAfter, ArrayList<Integer> posResult) {
    int levelBefore, levelAfter;

    if (posBefore.size() == 0 && posAfter.size() == 0) {
      ArrayList<Integer> initial = new ArrayList<>();
      initial.add(0);
      return initial;
    }

    if (posBefore.size() == 0) levelBefore = 0;
    else levelBefore = posBefore.get(0);
    if (posAfter.size() == 0) levelAfter = 0;
    else levelAfter = posAfter.get(0);

    if (levelBefore - levelAfter > 1) {
      int newPos = levelBefore + 1; // Will be replaced later
      posResult.add(newPos);
      return posResult;
    } else if (levelBefore - levelAfter == 1) {
      posResult.add(levelBefore);
      ArrayList<Integer> newPosBefore = (ArrayList) posBefore.clone();
      newPosBefore.remove(0);
      return this.generatePosBetween(newPosBefore, posAfter, posResult);
    }
    return posResult;
  }

  private void printPosition(ArrayList<Integer> position) {
    for (int i=0; i<position.size(); i++) {
      System.out.print(String.format("%d ", position.get(i)));
    }
    System.out.println();
  }

}