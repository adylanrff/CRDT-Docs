package com.sisterhore.crdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CRDT {
  private int siteId;
  private ArrayList<Char> struct;
  private HashMap<Integer, BoundaryType> strategyChoice;
  private int base;
  enum BoundaryType {
    PLUS, MINUS
  }
  static final int boundary = 10;

  public CRDT(int siteId){
    this.siteId = siteId;
    this.struct = new ArrayList<>();
    this.strategyChoice = new HashMap<>();
    this.base = 32;
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
   * Insert by index on local
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
   * Delete by index on local
   * @param index
   * @return
   */
  public Char localDelete(int index) {
    Char deletedChar = this.struct.get(index);
    this.struct.remove(index);
    // LOG
    this.printStruct();
    return deletedChar;
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

    ArrayList<Integer> newPos = this.allocPosBetween(posBefore, posAfter);
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
    Char charBefore = this.struct.get(index - 1);
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
   * Generate boundary strategy for certain depth
   * @param depth asked depth
   * @return Boundary type
   */
  private BoundaryType allocBoundaryStrategy(int depth) {
    if (!this.strategyChoice.containsKey(depth)) {
      boolean choice = new Random().nextBoolean();
      if (choice) this.strategyChoice.put(depth, BoundaryType.PLUS);
      else this.strategyChoice.put(depth, BoundaryType.MINUS);
    }
    return this.strategyChoice.get(depth);
  }

  /**
   * Calculate base for certain depth
   * @param depth asked depth
   * @return base
   */
  private int calculateBase(int depth) {
    int multiplier = (int) Math.pow(2, (double) depth);
    return multiplier * this.base;
  }

  /**
   * Generate new position between posBefore and posAfter
   * @param posBefore position before
   * @param posAfter position after
   * @return position result
   */
  private ArrayList<Integer> allocPosBetween(ArrayList<Integer> posBefore, ArrayList<Integer> posAfter) {
    int interval = 0;
    int depth = 0;
    int prefixBefore = 0;
    int prefixAfter = this.calculateBase(depth);
    BoundaryType strategy;
    ArrayList<Integer> posResult = new ArrayList<>();

    /* For 1st char in struct */
    if (posBefore.size() == 0 && posAfter.size() == 0) {
      posResult.add(0);
      return posResult;
    }

    /* While there's no space to be inserted */
    while (interval < 1) {
      depth++; /* Down 1 level */
      if (posBefore.size() < depth) prefixBefore = 0;
      else prefixBefore = posBefore.get(depth - 1);
      if (posAfter.size() < depth) prefixAfter = this.calculateBase(depth - 1);
      else prefixAfter = posAfter.get(depth - 1);

      interval = prefixAfter - prefixBefore - 1;

      if (interval == 0) {
        posResult.add(prefixBefore);
      } else if (interval == -1) { /* Will add extra handling later */
        posResult.add(prefixBefore);
      }
    }
    int step = Math.min(interval, boundary);

    strategy = this.allocBoundaryStrategy(depth);
    if (strategy == BoundaryType.PLUS) {
      System.out.println("Plus"); /* LOG */
      int val = new Random().nextInt(step) + 1;
      int id = prefixBefore + val;
      posResult.add(id);
    } else {
      System.out.println("Minus"); /* LOG */
      int val = new Random().nextInt(step) + 1;
      int id = prefixAfter - val;
      posResult.add(id);
    }

    return posResult;
  }

  /**
   * Returns new arrayList without head
   * @param input arrayList
   * @return new modified arrayList
   */
  private ArrayList<Integer> slice(ArrayList<Integer> input) {
    ArrayList<Integer> result = (ArrayList) input.clone();
    if (result.size() > 0) result.remove(0);
    return result;
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