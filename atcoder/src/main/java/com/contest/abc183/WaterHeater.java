package com.contest.abc183;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

public class WaterHeater {

  private static int people;
  private static int rate;
  private static List<Point> starts;
  private static List<Point> ends;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    people = scan.scanInt();
    rate = scan.scanInt();

    starts = new ArrayList<>();
    ends = new ArrayList<>();

    for (int i = 0; i < people; i++) {
      int si = scan.scanInt();
      int ei = scan.scanInt();
      int cost = scan.scanInt();

      starts.add(new Point(si, cost));
      ends.add(new Point(ei, cost));
    }

    starts.sort(Comparator.comparingInt(p -> p.index));
    ends.sort(Comparator.comparingInt(p -> p.index));

    boolean possible = solve();

    print.printLine(possible ? "Yes" : "No");
    print.close();

  }

  private static boolean solve() {
    int bucket = 0;
    int i = 0, j = 0;

    while (i < starts.size() && j < ends.size()) {
      if (starts.get(i).index < ends.get(j).index) {
        bucket += starts.get(i).cost;

        if (bucket > rate) {
          return false;
        }

        i++;
      } else {
        bucket -= ends.get(j).cost;
        j++;
      }
    }

    return true;
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

  static class Point {

    private int index;
    private int cost;

    Point(int _index, int _cost) {
      this.index = _index;
      this.cost = _cost;
    }
  }

}
