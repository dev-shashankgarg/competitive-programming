package contest.div2.n718;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class B {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(t -> {
      int n = scan.scanInt();
      int m = scan.scanInt();

      List<Point> li = new ArrayList<>();
      HashMap<Integer, Queue<Point>> map = new HashMap<>();

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          Point p = new Point(i, scan.scanInt());
          li.add(p);
          Queue<Point> q = map.getOrDefault(i, new LinkedList<>());
          q.add(p);
          map.put(i, q);
        }
      }

      li.sort(Comparator.comparingInt(p -> p.val));
      int[][] ans = new int[n][m];

      for (int i = 0; i < m; i++) {
        Point p = li.get(i);
        p.taken = true;
        ans[p.index][i] = p.val;
      }

      for (int i = 0; i < n; i++) {
        Queue<Point> q = map.get(i);
        for (int j = 0; j < m; j++) {
          if (ans[i][j] == 0) {
            Point p = q.poll();
            while (p.taken) {
              p = q.poll();
            }
            ans[i][j] = p.val;
          }
        }
      }

      for (int i = 0; i < n; i++) {
        StringJoiner sj = new StringJoiner(" ");
        for (int j = 0; j < m; j++) {
          sj.add(Integer.toString(ans[i][j]));
        }
        print.printLine(sj.toString());
      }

    });

    print.close();

  }

  static class Point {

    int index;
    int val;
    boolean taken;

    public Point(int index, int val) {
      this.index = index;
      this.val = val;
      this.taken = false;
    }

    @Override
    public String toString() {
      return "Point{" +
          "index=" + index +
          ", val=" + val +
          ", taken=" + taken +
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
