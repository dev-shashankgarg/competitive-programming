package com.kickstart.y2021.b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class D {

  private static HashMap<Integer, List<Edge>> map;
  private static HashMap<Integer, List<Query>> qmap;
  private static SegmentTree st;
  private static HashSet<Integer> visited;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.rangeClosed(1, tests).forEach(test -> {

      st = new SegmentTree();
      visited = new HashSet<>();

      String[] s = scan.scanString().split(" ");

      int n = Integer.parseInt(s[0]);
      int q = Integer.parseInt(s[1]);
      map = new HashMap<>();
      qmap = new HashMap<>();

      for (int i = 0; i < n - 1; i++) {
        s = scan.scanString().split(" ");
        Edge e1 = new Edge(s[0], s[1], s[2], s[3]);
        Edge e2 = new Edge(s[1], s[0], s[2], s[3]);

        List<Edge> li = map.getOrDefault(e1.from, new ArrayList<>());
        li.add(e1);
        map.put(e1.from, li);

        List<Edge> li1 = map.getOrDefault(e2.from, new ArrayList<>());
        li1.add(e2);
        map.put(e2.from, li1);
      }

      for (int i = 0; i < q; i++) {
        s = scan.scanString().split(" ");
        Query qu = new Query(i, s[0], s[1]);
        List<Query> li = qmap.getOrDefault(qu.city, new ArrayList<>());
        li.add(qu);
        qmap.put(qu.city, li);
      }

      solve(1);
      long[] ans = new long[q];

      for (List<Query> qans : qmap.values()) {
        for (Query qqq : qans) {
          ans[qqq.index] = qqq.ans;
        }
      }

      StringJoiner sj = new StringJoiner(" ");
      for (long x : ans) {
        sj.add(Long.toString(x));
      }

      print.printLine(String.format("Case #%d: %s", test, sj.toString()));
    });

    print.close();
  }

  private static void solve(int city) {
    for (Query q : qmap.getOrDefault(city, new ArrayList<>())) {
      q.ans = st.query(q.weight);
    }

    visited.add(city);
    for (Edge e : map.getOrDefault(city, new ArrayList<>())) {
      if (!visited.contains(e.to)) {
        st.update(e.limit, e.toll);
        solve(e.to);
        st.update(e.limit, 0);
      }
    }

    visited.remove(city);
  }

  static class Edge {

    private Integer from;
    private Integer to;
    private Integer limit;
    private Long toll;

    public Edge(String from, String to, String limit, String toll) {
      this.from = Integer.parseInt(from);
      this.to = Integer.parseInt(to);
      this.limit = Integer.parseInt(limit);
      this.toll = Long.parseLong(toll);
    }

    @Override
    public String toString() {
      return "Edge{" +
          "from=" + from +
          ", to=" + to +
          ", limit=" + limit +
          ", toll=" + toll +
          '}';
    }
  }

  static class Query {

    private Integer index;
    private Integer city;
    private Integer weight;
    private Long ans;

    public Query(Integer index, String city, String weight) {
      this.index = index;
      this.city = Integer.parseInt(city);
      this.weight = Integer.parseInt(weight);
    }

    @Override
    public String toString() {
      return "Query{" +
          "index=" + index +
          ", city=" + city +
          ", weight=" + weight +
          ", ans=" + ans +
          '}';
    }
  }

  static class SegmentTree {

    int size = 2_000_07;
    Node[] data;

    SegmentTree() {
      data = new Node[size * 4];
      build(0, 0, size - 1);
    }

    private Node build(int current, int l, int r) {
      if (l == r) {
        return data[current] = new Node(0);
      } else {
        int mid = l + (r - l) / 2;
        return data[current] = combine(build(2 * current + 1, l, mid),
            build(2 * current + 2, mid + 1, r));
      }
    }

    public long query(int x) {
      return query(0, 0, x, 0, size - 1).val;
    }

    private Node query(int index, int l, int r, int ll, int rr) {
      if (l == ll && r == rr) {
        return data[index];
      }

      int mid = ll + (rr - ll) / 2;
      Node a = new Node(0);
      if (l <= mid) {
        a = query(2 * index + 1, l, Math.min(mid, r), ll, mid);
      }

      Node b = new Node(0);
      if (r > mid) {
        b = query(2 * index + 2, Math.max(l, mid + 1), r, mid + 1, rr);
      }

      return combine(a, b);
    }

    public void update(int index, long val) {
      update(0, index, val, 0, size - 1);
    }

    private Node update(int current, int index, long val, int ll, int rr) {
      if (ll == rr) {
        return data[current] = new Node(val);
      }

      int mid = ll + (rr - ll) / 2;
      if (index <= mid) {
        return data[current] = combine(update(2 * current + 1, index, val, ll, mid),
            data[2 * current + 2]);
      }
      return data[current] = combine(update(2 * current + 2, index, val, mid + 1, rr),
          data[2 * current + 1]);
    }

    private Node combine(Node a, Node b) {
      return new Node(gcd(a.val, b.val));
    }

    private long gcd(long a, long b) {
      if (b == 0) {
        return a;
      }
      return gcd(b, a % b);
    }

    static class Node {

      long val;

      public Node(long val) {
        this.val = val;
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
