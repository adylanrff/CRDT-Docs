package com.sisterhore.crdt;

import java.util.Scanner;

public class CRDTTest {

  public void run() {
    CRDT crdt = new CRDT("ws://localhost:8887");
    Scanner reader = new Scanner(System.in);
    for (int i = 0; i < 5; i++) {
      System.out.print("Insert a character: ");
      char c = reader.next().charAt(0);
      System.out.print("Insert index: ");
      int idx = reader.nextInt();
      crdt.localInsert(c, idx);
    }

    for (int i=0; i<5; i++) {
      System.out.print("Insert index: ");
      int idx = reader.nextInt();
      crdt.localDelete(idx);
    }
  }

}
