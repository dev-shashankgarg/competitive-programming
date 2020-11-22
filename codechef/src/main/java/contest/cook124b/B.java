package contest.cook124b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class B {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {

      String a = scan.scanString();
      String b = scan.scanString();

      DisjointSet odds = new DisjointSet();
      DisjointSet evens = new DisjointSet();

      for (int i = 0; i < a.length(); i++) {
        if (a.charAt(i) != b.charAt(i)) {
          if (i % 2 == 0) {
            evens.add(i);
          } else {
            odds.add(i);
          }
        }
      }

      int ans = odds.groups + evens.groups;
      print.printLine(Integer.toString(ans));
    });

    print.close();

  }

  static class Node {

    int val;
    Node parent;
    int rank;

    Node(int _val) {
      this.val = _val;
      this.parent = this;
      this.rank = 0;
    }
  }

  static class DisjointSet {

    private HashMap<Integer, Node> map;
    private int groups;

    DisjointSet() {
      map = new HashMap<>();
      groups = 0;
    }

    public void add(int index) {
      Node node = map.get(index);
      if (node == null) {
        groups++;
        node = new Node(index);
      }

      map.put(index, node);

      Node l = map.get(index - 2);

      if (l != null) {
        groups--;
        l = findSet(l);

        if (l.rank >= node.rank) {
          node.parent = l;
          l.rank += (l.rank == node.rank) ? 1 : 0;
        } else {
          l.parent = node;
        }
      }
    }

    private Node findSet(Node l) {
      if (l.parent == l) {
        return l;
      }
      l.parent = findSet(l.parent);
      return l.parent;
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
