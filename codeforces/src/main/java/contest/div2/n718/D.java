package contest.div2.n718;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.StringJoiner;

public class D {

  private static int n;
  private static int m;
  private static int k;

  private static int[][] ans;
  private static int[][] ei;
  private static int[][] ej;

  private static Integer[][][] cache;
  private static int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    n = scan.scanInt();
    m = scan.scanInt();
    k = scan.scanInt();

    ans = new int[n][m];
    ei = new int[n][m - 1];
    ej = new int[n - 1][m];

    for (int i = 0; i < ei.length; i++) {
      for (int j = 0; j < ei[i].length; j++) {
        ei[i][j] = scan.scanInt();
      }
    }

    for (int i = 0; i < ej.length; i++) {
      for (int j = 0; j < ej[i].length; j++) {
        ej[i][j] = scan.scanInt();
      }
    }

    if (k % 2 == 1) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          ans[i][j] = -1;
        }
      }
    } else {
      cache = new Integer[n][m][k / 2 + 1];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          ans[i][j] = 2 * solve(i, j, k / 2);
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
    print.close();

  }

  private static int solve(int i, int j, int left) {
    if (left == 0) {
      return 0;
    }

    if (cache[i][j][left] != null) {
      return cache[i][j][left];
    }
    int min = Integer.MAX_VALUE;
    for (int[] d : directions) {
      int ni = i + d[0];
      int nj = j + d[1];

      if (valid(ni, nj)) {
        min = Math.min(min, getV(i, j, ni, nj) + solve(ni, nj, left - 1));
      }
    }

    return cache[i][j][left] = min;
  }

  private static int getV(int i, int j, int ni, int nj) {
    if (i == ni) {
      return ei[i][Math.min(j, nj)];
    } else {
      return ej[Math.min(i, ni)][j];
    }
  }

  private static boolean valid(int i, int j) {
    return i >= 0 && i < n && j >= 0 && j < m;
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