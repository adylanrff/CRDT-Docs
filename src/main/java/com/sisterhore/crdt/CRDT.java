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
    // LOG
    this.printStruct();
  }

  /**
   * Generate Char object with desired position
   * @param value char value
   * @param index index to be inserted
   * @return new Char object
   */
  private Char generateChar(char value, int index) {
    ArrayList<Integer> posBefore = this.findPosBefore(index);
    // LOG
    System.out.print("Position before: ");
    this.printPosition(posBefore);

    ArrayList<Integer> posAfter = this.findPosAfter(index);
    // LOG
    System.out.print("Position after: ");
    this.printPosition(posAfter);

    ArrayList<Integer> posResult = new ArrayList<>();
    ArrayList<Integer> newPos = this.generatePosBetween(posBefore, posAfter, posResult);
    // LOG
    System.out.print("New position: ");
    this.printPosition(newPos);

    return new Char(this.siteId, value, newPos);
  }

  /**
   * @param index index where new char will be inserted
   * @return the position before
   */
  private ArrayList<Integer> findPosBefore(int index) {
    if ((this.struct.size() == 0) || (index == 0))
      return new ArrayList<>();
    Char charBefore = this.struct.get(index-1);
    return charBefore.getPosition();
  }

  /**
   * @param index index where new char will be inserted
   * @return the position after
   */
  private ArrayList<Integer> findPosAfter(int index) {
    if (this.struct.size() == 0) {
      return new ArrayList<>();
    } else if (index == this.struct.size()) {
      ArrayList<Integer> end = new ArrayList<>();
      end.add(index + 1);
      return end;
    }
    Char charAfter = this.struct.get(index);
    if (charAfter == null) return new ArrayList<>();
    return charAfter.getPosition();
  }

  /**
   * Generate new position between posBefore and posAfter
   * @param posBefore left boundary
   * @param posAfter right boundary
   * @param posResult new position
   * @return position result
   */
  private ArrayList<Integer> generatePosBetween(ArrayList<Integer> posBefore, ArrayList<Integer> posAfter, ArrayList<Integer> posResult) {
    int levelBefore = 0;
    int levelAfter = 99; // Some high range number, replaced later

    if (posBefore.size() == 0 && posAfter.size() == 0) {
      posResult.add(0);
      return posResult;
    }
    if (posBefore.size() != 0) levelBefore =  posBefore.get(0);
    if (posAfter.size() != 0) levelAfter =  posAfter.get(0);

    if (levelAfter - levelBefore > 1) {

      posResult.add(levelBefore + 1); // Will be replaced later
      return posResult;

    } else if (levelAfter - levelBefore == 1) {

      posResult.add(levelBefore);
      ArrayList<Integer> newPosBefore = (ArrayList) posBefore.clone();
      if (newPosBefore.size() > 0) newPosBefore.remove(0);
      return this.generatePosBetween(newPosBefore, new ArrayList<>(), posResult);

    } else if (levelAfter == levelBefore) {

      posResult.add(levelBefore);
      ArrayList<Integer> newPosBefore = (ArrayList) posBefore.clone();
      if (newPosBefore.size() > 0) newPosBefore.remove(0);
      ArrayList<Integer> newPosAfter = (ArrayList) posAfter.clone();
      if (newPosAfter.size() > 0) newPosAfter.remove(0);
      return this.generatePosBetween(newPosBefore, newPosAfter, posResult);

    }
    return posResult;
  }

  /**
   * Print position in format [1,2,...]
   * @param position position array
   */
  private void printPosition(ArrayList<Integer> position) {
    System.out.print('[');
    for (int i=0; i<position.size(); i++) {
      System.out.print(position.get(i));
      if (i < position.size()-1) System.out.print(',');
    }
    System.out.println(']');
  }

  /**
   * Print current struct contents
   */
  private void printStruct() {
    System.out.print("Current struct: ");
    for (int i=0; i<this.struct.size(); i++) {
      System.out.print(this.struct.get(i).getValue());
    }
    System.out.println('\n');
  }

}