package com.contest.arc109;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;

public class A {

  private static Long[][] cache;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int a = scan.scanInt();
    int b = scan.scanInt();
    int x = scan.scanInt();
    int y = scan.scanInt();

    cache = new Long[101][2];

    print.printLine(Long.toString(solve(a, b, x, y, 0)));
    print.close();

  }

  private static long solve(int a, int b, int x, int y, int build) {

    if (a < 1 || a > 100) {
      return Integer.MAX_VALUE;
    }

    if (a == b) {
      return build == 1 ? 0 : x;
    }

    if (cache[a][build] != null) {
      return cache[a][build];
    }

    if (build == 0) {
      if (a < b) {
        return cache[a][build] = Math.min(y + solve(a + 1, b, x, y, build),
            x + solve(a, b, x, y, (build + 1) % 2));
      } else {
        return cache[a][build] = Math.min(y + solve(a - 1, b, x, y, build),
            x + solve(a - 1, b, x, y, (build + 1) % 2));
      }
    } else {
      if (a < b) {
        return cache[a][build] = Math.min(y + solve(a + 1, b, x, y, build),
            x + solve(a + 1, b, x, y, (build + 1) % 2));
      } else {
        return cache[a][build] = Math.min(y + solve(a - 1, b, x, y, build),
            x + solve(a, b, x, y, (build + 1) % 2));
      }
    }

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