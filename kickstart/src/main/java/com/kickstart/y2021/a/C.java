package com.kickstart.y2021.a;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.IntStream;

class C {

  private static HashSet<Long> fixed;
  private static PriorityQueue<P> q;
  private static HashMap<Long, P> map;
  private static int r;
  private static int c;
  private static int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
  private static long k_f = 3500;

  private static long key(long i, long j) {
    return k_f * i + j;
  }

  public static void main(String[] args) {
    Print print = new Print();
    //Scan scan = new Scan();

    Scanner sc = new Scanner(System.in);

    int tests = Integer.parseInt(sc.nextLine());
    IntStream.rangeClosed(1, tests).forEach(test -> {

      fixed = new HashSet<>();
      q = new PriorityQueue<>((p1, p2) -> p2.val - p1.val);
      map = new HashMap<>();

      String[] s = sc.nextLine().split(" ");
      r = Integer.parseInt(s[0]);
      c = Integer.parseInt(s[1]);

      for (int i = 0; i < r; i++) {
        String[] arr = sc.nextLine().split(" ");
        for (int j = 0; j < c; j++) {
          long key = key(i, j);
          P p = new P(key, Integer.parseInt(arr[j]));
          map.put(key, p);
          q.add(p);
        }
      }

      long ans = solve();

      print.printLine(String.format("Case #%d: %d", test, ans));

    });

    print.close();
  }

  private static long solve() {
    long co = 0;
    while (fixed.size() != r * c && !q.isEmpty()) {
      P p = q.poll();

      if (!fixed.contains(p.key)) {
        long i = p.key / k_f;
        long j = p.key % k_f;

        for (int[] d : directions) {
          long ni = i + d[0];
          long nj = j + d[1];

          if (valid(ni, nj)) {
            long nkey = key(ni, nj);
            P np = map.get(nkey);
            if (Math.abs(np.val - p.val) > 1) {
              int nval = p.val - 1;
              co += (long) (nval - np.val);
              P npp = new P(nkey, nval);
              map.put(nkey, npp);
              q.add(npp);
            }
          }
        }
        fixed.add(p.key);
      }
    }

    return co;
  }

  private static boolean valid(long ni, long nj) {
    return ni >= 0 && ni < r && nj >= 0 && nj < c;
  }

  static class P {

    long key;
    int val;

    public P(long key, int val) {
      this.key = key;
      this.val = val;
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
