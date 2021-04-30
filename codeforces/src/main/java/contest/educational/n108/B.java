package contest.educational.n108;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class B {

  private static long n;
  private static long m;
  private static long k;
  private static int[][] directions = {{1, 0, 0, 1}, {0, 1, 1, 0}};
  private static Boolean[][][] cache;

  private static boolean valid(long i, long j) {
    return i >= 1 && i <= n && j >= 1 && j <= m;
  }

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      String[] in = scan.scanString().split(" ");
      n = Long.parseLong(in[0]);
      m = Long.parseLong(in[1]);
      k = Long.parseLong(in[2]);

      //cache = new Boolean[(int) n + 1][(int) m + 1][(int) k + 1];

      //boolean val = solve(1, 1, 0);
      boolean val = k == ((n * m) - 1);
      print.printLine(val ? "YES" : "NO");
    });

    print.close();
  }

  private static boolean solve(int i, int j, long cost) {
    if (i == n && j == m) {
      System.out.println(cost);
      return cost == k;
    }

    if (cost > k) {
      return false;
    }

    if (cache[i][j][(int) cost] != null) {
      return cache[i][j][(int) cost];
    }
    for (int[] d : directions) {
      int ni = i + d[0];
      int nj = j + d[1];
      if (valid(ni, nj)) {
        long nCost = cost + (long) i * d[2] + (long) j * d[3];
        boolean val = solve(ni, nj, nCost);
        if (val) {
          return cache[i][j][(int) cost] = true;
        }
      }
    }
    return cache[i][j][(int) cost] = false;
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