package com.kickstart.y2013.pr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Problem As the leader of the Evil League of Evil, Bad Horse has a lot of problems to deal with.
 * Most recently, there have been far too many arguments and far too much backstabbing in the
 * League, so much so that Bad Horse has decided to split the league into two departments in order
 * to separate troublesome members. Being the Thoroughbred of Sin, Bad Horse isn't about to spend
 * his valuable time figuring out how to split the League members by himself. That what he's got you
 * -- his loyal henchman -- for.
 * <p>
 * Input The first line of the input gives the number of test cases, T. T test cases follow. Each
 * test case starts with a positive integer M on a line by itself -- the number of troublesome pairs
 * of League members. The next M lines each contain a pair of names, separated by a single space.
 * <p>
 * Output For each test case, output one line containing "Case #x: y", where x is the case number
 * (starting from 1) and y is either "Yes" or "No", depending on whether the League members
 * mentioned in the input can be split into two groups with neither of the groups containing a
 * troublesome pair.
 * <p>
 * Limits Time limit: 60 seconds per test set. Memory limit: 1 GB. 1 ≤ T ≤ 100. Each member name
 * will consist of only letters and the underscore character. Names are case-sensitive. No pair will
 * appear more than once in the same test case. Each pair will contain two distinct League members.
 * <p>
 * Small dataset 1 ≤ M ≤ 10.
 * <p>
 * Large dataset 1 ≤ M ≤ 100.
 * <p>
 * Sample
 * <p>
 * Input
 * <p>
 * Output
 * <p>
 * 2 1 Dead_Bowie Fake_Thomas_Jefferson 3 Dead_Bowie Fake_Thomas_Jefferson Fake_Thomas_Jefferson
 * Fury_Leika Fury_Leika Dead_Bowie Case #1: Yes Case #2: No
 */
public class BadHorse {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.rangeClosed(1, tests).forEach(test -> {

      Map<String, List<String>> map = new HashMap<>();
      int pairs = scan.scanInt();

      for (int i = 0; i < pairs; i++) {
        String a = scan.scanString();
        String b = scan.scanString();

        List<String> li = map.getOrDefault(a, new ArrayList<>());
        li.add(b);
        map.put(a, li);

        List<String> li1 = map.getOrDefault(b, new ArrayList<>());
        li1.add(a);
        map.put(b, li1);
      }

      boolean ans = solve(map);

      print.printLine(String.format("Case #%d: %s", test, ans ? "Yes" : "No"));

    });

    print.close();
  }

  private static boolean solve(Map<String, List<String>> map) {

    Set<String> checked = new HashSet<>();
    Queue<String> next = new LinkedList<>();

    for (String person : map.keySet()) {
      if (!checked.contains(person)) {

        DisjointSet ds = new DisjointSet();

        next.add(person);
        while (!next.isEmpty()) {
          String tc = next.poll();

          for (String conn : map.get(tc)) {
            boolean poss = ds.union(tc, conn);
            if (!poss) {
              return false;
            }
          }

          checked.add(tc);
          for (String s : map.getOrDefault(tc, new ArrayList<>())) {
            if (!checked.contains(s)) {
              next.add(s);
            }
          }
        }
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

  static class DisjointSet {

    Set<String> ga;
    Set<String> gb;

    DisjointSet() {
      ga = new HashSet<>();
      gb = new HashSet<>();
    }

    public boolean union(String a, String b) {

      //System.out.println("##" + a + " " + b);

      if (ga.isEmpty() && gb.isEmpty()) {
        ga.add(a);
      }

      if (ga.contains(a)) {
        if (ga.contains(b)) {
          return false;
        }
        gb.add(b);
      } else if (gb.contains(a)) {
        if (gb.contains(b)) {
          return false;
        }
        ga.add(b);
      }

      //System.out.println("GA" + ga);
      //System.out.println("GB" + gb);

      return true;
    }
  }

}
