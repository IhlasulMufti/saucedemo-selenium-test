package com.juaracoding.utils;

public class Utils {

  public static void delay(long detik) {
      try {
          Thread.sleep(detik*1000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
  }

}
