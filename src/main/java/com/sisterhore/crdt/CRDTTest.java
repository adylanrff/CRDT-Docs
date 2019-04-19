package com.sisterhore.crdt;

import java.util.Scanner;

public class CRDTTest {

  public void run() {
    CRDT crdt = new CRDT(0);
    Scanner reader = new Scanner(System.in);
    while(true) {
      System.out.print("Insert a character: ");
      char c = reader.next().charAt(0);
      System.out.print("Insert index: ");
      int i = reader.nextInt();
      crdt.localInsert(c, i);
    }
  }

}
