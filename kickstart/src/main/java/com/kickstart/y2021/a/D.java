package com.kickstart.y2021.a;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

class D {

  static int[][] data;
  static int[][] costs;
  static int n;

  public static void main(String[] args) {
    Print print = new Print();
    Scanner sc = new Scanner(System.in);

    int tests = Integer.parseInt(sc.nextLine());
    IntStream.rangeClosed(1, tests).forEach(test -> {

      n = Integer.parseInt(sc.nextLine());
      data = new int[n][n];
      costs = new int[n][n];

      int[] r = new int[n];
      int[] c = new int[n];

      for (int i = 0; i < n; i++) {
        String[] arr = sc.nextLine().split(" ");
        for (int j = 0; j < arr.length; j++) {
          data[i][j] = Integer.parseInt(arr[j]);
        }
      }

      for (int i = 0; i < n; i++) {
        String[] arr = sc.nextLine().split(" ");
        for (int j = 0; j < arr.length; j++) {
          costs[i][j] = Integer.parseInt(arr[j]);
        }
      }

      String[] arr = sc.nextLine().split(" ");
      for (int j = 0; j < arr.length; j++) {
        r[j] = Integer.parseInt(arr[j]);
      }

      arr = sc.nextLine().split(" ");
      for (int j = 0; j < arr.length; j++) {
        c[j] = Integer.parseInt(arr[j]);
      }

      long ans = solve();

      print.printLine(String.format("Case #%d: %d", test, ans));

    });

    print.close();
  }

  private static long solve() {
    List<Edge> li = new ArrayList<>();

    long total = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (data[i][j] == -1) {
          total += costs[i][j];
          li.add(new Edge(1000 + i, 2000 + j, costs[i][j]));
        }
      }
    }

    li.sort((e1, e2) -> e2.cost - e1.cost);
    DSU dsu = new DSU();
    for (Edge e : li) {
      int x = dsu.add(e);
      total -= x;
    }

    return total;
  }


  static class DSU {

    Map<Integer, Node> map;

    DSU() {
      map = new HashMap<>();
    }

    private Node findSet(Node a) {
      if (a.parent == a) {
        return a;
      }
      a.parent = findSet(a.parent);
      return a.parent;
    }

    public int add(Edge e) {
      Node l = map.getOrDefault(e.i, new Node(e.i));
      Node r = map.getOrDefault(e.j, new Node(e.j));

      map.put(e.i, l);
      map.put(e.j, r);

      l = findSet(l);
      r = findSet(r);

      if (l == r) {
        return 0;
      } else {
        if (l.rank >= r.rank) {
          l.rank += (l.rank == r.rank ? 1 : 0);
          r.parent = l;
        } else {
          l.parent = r;
        }
        return e.cost;
      }
    }

  }

  static class Edge {

    int i;
    int j;
    int cost;

    public Edge(int i, int j, int cost) {
      this.i = i;
      this.j = j;
      this.cost = cost;
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

  static class Node {

    int i;
    int rank;
    Node parent;

    Node(int val) {
      this.i = val;
      this.rank = 0;
      this.parent = this;
    }

  }

}
