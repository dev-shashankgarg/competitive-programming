package com.contest.arc114;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeSet;

public class A {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int n = scan.scanInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = scan.scanInt();
    }

    print.printLine(Long.toString(solve(arr)));
    print.close();
  }

  private static long solve(int[] arr) {

    Map<Integer, TreeSet<Integer>> map = new HashMap<>();

    for (int x : arr) {
      map.put(x, new TreeSet<>());

      int factor = 2;
      TreeSet<Integer> s = map.get(x);
      while (x > 1) {
        if (x % factor == 0) {
          s.add(factor);
          x /= factor;
        } else {
          factor += 1;
        }
      }
    }

    return permute(arr, 0, map, new HashSet<>());
  }

  private static long permute(int[] arr, int i, Map<Integer, TreeSet<Integer>> map,
      HashSet<Integer> taken) {

    if (i == arr.length) {
      return 1;
    }

    TreeSet<Integer> se = map.get(arr[i]);

    for (int x : taken) {
      if (se.contains(x)) {
        return permute(arr, i + 1, map, taken);
      }
    }

    long min = Long.MAX_VALUE;

    for (int x : se) {
      taken.add(x);
      min = Math.min(x * permute(arr, i + 1, map, taken), min);
      taken.remove(x);
    }

    return min;
  }

  static class Scan {

    private byte[] buf = new byte[1024];
    private int index;
    private InputStream in;
    private int total;

    public Scan() {
      in = System.in;
    }

    public int scan() {
      if (total < 0) {
        throw new InputMismatchException();
      }
      if (index >= total) {
        index = 0;
        try {
          total = in.read(buf);
        } catch (IOException ignored) {
        }
        if (total <= 0) {
          return -1;
        }
      }
      return buf[index++];
    }

    public int scanInt() {
      int integer = 0;
      int n = scan();
      while (isWhiteSpace(n)) {
        n = scan();
      }
      int neg = 1;
      if (n == '-') {
        neg = -1;
        n = scan();
      }
      while (!isWhiteSpace(n)) {
        if (n >= '0' && n <= '9') {
          integer *= 10;
          integer += n - '0';
          n = scan();
        } else {
          throw new InputMismatchException();
        }
      }
      return neg * integer;
    }

    public double scanDouble() {
      double doub = 0;
      int n = scan();
      while (isWhiteSpace(n)) {
        n = scan();
      }
      int neg = 1;
      if (n == '-') {
        neg = -1;
        n = scan();
      }
      while (!isWhiteSpace(n) && n != '.') {
        if (n >= '0' && n <= '9') {
          doub *= 10;
          doub += n - '0';
          n = scan();
        } else {
          throw new InputMismatchException();
        }
      }
      if (n == '.') {
        n = scan();
        double temp = 1;
        while (!isWhiteSpace(n)) {
          if (n >= '0' && n <= '9') {
            temp /= 10;
            doub += (n - '0') * temp;
            n = scan();
          } else {
            throw new InputMismatchException();
          }
        }
      }
      return doub * neg;
    }

    public String scanString() {
      StringBuilder sb = new StringBuilder();
      int n = scan();
      while (isWhiteSpace(n)) {
        n = scan();
      }
      while (!isWhiteSpace(n)) {
        sb.append((char) n);
        n = scan();
      }
      return sb.toString();
    }

    private boolean isWhiteSpace(int n) {
      if (n == ' ' || n == '\n' || n == '\r' || n == '\t' || n == -1) {
        return true;
      }
      return false;
    }
  }

  static class Print {

    private final BufferedWriter bw;

    public Print() {
      bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void print(String str) {
      try {
        bw.append(str);
      } catch (IOException ignored) {
      }
    }

    public void printLine(String str) {
      print(str);
      try {
        bw.append("\n");
      } catch (IOException ignored) {
      }
    }

    public void close() {
      try {
        bw.close();
      } catch (IOException ignored) {
      }
    }
  }

}