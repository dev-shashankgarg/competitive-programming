package com.contest.abc184;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;

public class C {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    long x1 = scan.scanInt();
    long y1 = scan.scanInt();

    long x2 = scan.scanInt();
    long y2 = scan.scanInt();

    if (x1 == x2 && y1 == y2) {
      print.printLine(Integer.toString(0));
    } else if (canReach(x1, y1, x2, y2)) {
      print.printLine(Integer.toString(1));
    } else if (Math.abs(x1 - y1) % 2 == Math.abs(x2 - y2) % 2) {
      print.printLine(Integer.toString(2));
    } else if (canReach(x2, y1 + x2 - x1, x2, y2)) {
      print.printLine(Integer.toString(2));
    } else if (canReach(x1 + y2 - y1, y2, x2, y2)) {
      print.printLine(Integer.toString(2));
    } else if (canReach(x2, y1 + x1 - x2, x2, y2)) {
      print.printLine(Integer.toString(2));
    } else if (canReach(x1 + y1 - y2, y2, x2, y2)) {
      print.printLine(Integer.toString(2));
    } else {
      print.printLine(Integer.toString(3));
    }

    print.close();

  }

  private static boolean canReach(long x1, long y1, long x2, long y2) {
    return (x1 + y1 == x2 + y2) || (x1 - y1 == x2 - y2) || (Math.abs(x1 - x2) + Math.abs(y1 - y2)
        <= 3);
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