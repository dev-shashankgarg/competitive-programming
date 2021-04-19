package contest.april21b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class KAVGMAT {

  private static int n;
  private static int m;
  private static long k;
  private static long[][] data;
  private static long[][] pre;

  private static boolean valid(int si, int sj, int ei, int ej) {
    return si >= 0 && sj >= 0 && ei < n && ej < m;
  }

  private static long sum(int si, int sj, int ei, int ej) {
    long val = pre[ei][ej];
    if (si != 0) {
      val -= pre[si - 1][ej];
    }

    if (sj != 0) {
      val -= pre[ei][sj - 1];
    }

    if (si != 0 && sj != 0) {
      val += pre[si - 1][sj - 1];
    }

    return val;
  }

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = Integer.parseInt(scan.scanString());
    IntStream.range(0, t).forEach(test -> {
      String[] params = scan.scanString().split(" ");
      n = Integer.parseInt(params[0]);
      m = Integer.parseInt(params[1]);
      k = Long.parseLong(params[2]);

      data = new long[n][m];
      for (int i = 0; i < n; i++) {
        String[] s = scan.scanString().split(" ");
        for (int j = 0; j < s.length; j++) {
          data[i][j] = Long.parseLong(s[j]);
        }
      }

      print.printLine(Long.toString(solve()));
    });

    print.close();

  }

  private static long solve() {
    pre = new long[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (j == 0) {
          pre[i][j] = data[i][j];
        } else {
          pre[i][j] = pre[i][j - 1] + data[i][j];
        }
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (i == 0) {
          pre[i][j] = pre[i][j];
        } else {
          pre[i][j] = pre[i - 1][j] + pre[i][j];
        }
      }
    }

    long ans = 0;
    for (int size = 0; size < Math.min(n, m); size++) {
      ans += solve(size, size, n - 1, m - 1, (long) (size + 1) * (size + 1));
    }

    return ans;
  }

  private static long solve(int li, int lj, int hi, int hj, long qty) {
    if (!valid(li, lj, hi, hj)) {
      return 0;
    }

    int length = (int) Math.sqrt(qty) - 1;

    int ci = li, cj = hj;
    long ans = 0;
    while (ci <= hi && cj >= lj) {

      double val = val(ci - length, cj - length, ci, cj, qty);
      if (val < k) {
        ci++;
      } else if (val >= k && (cj != lj
          && val(ci - length, cj - length - 1, ci, cj - 1, qty) >= k)) {
        cj--;
      } else {
        ans = (long) (hi - ci + 1) * (hj - cj + 1) + solve(ci + 1, lj, hi, cj - 1, qty);
        break;
      }
    }
    return ans;
  }

  private static double val(int si, int sj, int ei, int ej, long qty) {
    return sum(si, sj, ei, ej) / (double) qty;
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
