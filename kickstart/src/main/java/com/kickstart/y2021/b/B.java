package com.kickstart.y2021.b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.IntStream;

public class B {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.rangeClosed(1, tests).forEach(test -> {

      int n = scan.scanInt();
      String[] s = scan.scanString().split(" ");
      int[] data = new int[n];
      for (int i = 0; i < n; i++) {
        data[i] = Integer.parseInt(s[i]);
      }

      long ans = n == 2 ? 2 : solve(n, data);
      print.printLine(String.format("Case #%d: %d", test, ans));

    });

    print.close();
  }

  private static long solve(int n, int[] data) {

    int l = 0;
    long lval = data[1] - data[0];
    List<Interval> li = new ArrayList<>();

    for (int i = 1; i < n; i++) {
      long val = data[i] - data[i - 1];
      if (val != lval) {
        li.add(new Interval(lval, l == 0 ? l : (data[l] - data[l - 1] == lval ? l - 1 : l), i - 1));
        l = i;
        lval = val;
      }
    }

    li.add(new Interval(lval, l == 0 ? l : (data[l] - data[l - 1] == lval ? l - 1 : l), n - 1));
    //System.out.println(li);

    if (li.size() == 1) {
      return n;
    }

    long ans = 0;

    for (int i = 0; i < li.size(); i++) {
      ans = Long.max(ans, 1 + li.get(i).range());

      int rr = li.get(i).r;
      long val = li.get(i).val;
      if (rr + 2 < n && data[rr + 2] == data[rr] + val * 2) {
        ans = Long.max(ans, li.get(i).range() + 2);
      }

      int ll = li.get(i).l;
      val = li.get(i).val;
      if (ll - 2 >= 0 && data[ll - 2] + val * 2 == data[ll]) {
        ans = Long.max(ans, li.get(i).range() + 2);
      }

      if (i - 3 >= 0 && li.get(i).val == li.get(i - 3).val && li.get(i - 3).r + 2 == li.get(i).l) {
        ans = Long.max(ans, li.get(i - 3).range() + 1 + li.get(i).range());
      }

    }

    return ans;
  }

  static class Interval {

    private long val;
    private int l;
    private int r;

    public Interval(long val, int l, int r) {
      this.val = val;
      this.l = l;
      this.r = r;
    }

    public long range() {
      return r - l + 1;
    }

    @Override
    public String toString() {
      return "Interval{" +
          "val=" + val +
          ", l=" + l +
          ", r=" + r +
          '}';
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
      if (n == '\n' || n == '\r' || n == '\t' || n == -1) {
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
