package com.contest.abc184;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

public class F {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int n = scan.scanInt();
    boolean odd = n % 2 == 1;
    long capacity = Long.parseLong(scan.scanString());

    int[] arr1 = new int[n / 2];
    int[] arr2 = new int[(n / 2) + ((odd) ? 1 : 0)];

    for (int i = 0; i < arr1.length; i++) {
      arr1[i] = scan.scanInt();
    }

    for (int i = 0; i < arr2.length; i++) {
      arr2[i] = scan.scanInt();
    }

    List<Long> li1 = new ArrayList<>();
    solve(arr1, 0, li1, 0);
    List<Long> li2 = new ArrayList<>();
    solve(arr2, 0, li2, 0);

    Collections.sort(li2);

    long max = 0;

    for (long i : li1) {
      if (i <= capacity) {
        long j = bs(li2, 0, li2.size() - 1, capacity - i);
        max = Math.max(max, i + j);
      }
    }

    print.printLine(Long.toString(max));
    print.close();
  }

  private static long bs(List<Long> li2, int l, int r, long capacity) {

    if (l > r) {
      return 0;
    }

    int mid = l + (r - l) / 2;
    if (mid == li2.size() - 1) {
      return li2.get(mid) <= capacity ? li2.get(mid) : 0;
    }

    long cu = li2.get(mid);
    long next = li2.get(mid + 1);

    if (cu <= capacity && next > capacity) {
      return cu;
    } else if (cu > capacity) {
      return bs(li2, l, mid - 1, capacity);
    }

    return bs(li2, mid + 1, r, capacity);
  }

  private static void solve(int[] arr, int index, List<Long> li, long hold) {
    if (index == arr.length) {
      li.add(hold);
    } else {
      solve(arr, index + 1, li, hold + arr[index]);
      solve(arr, index + 1, li, hold);
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