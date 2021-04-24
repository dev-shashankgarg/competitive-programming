package contest.div2.n717;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;

public class C {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int n = scan.scanInt();
    int[] data = new int[n];

    int sum = 0;
    for (int i = 0; i < n; i++) {
      data[i] = scan.scanInt();
      sum += data[i];
    }

    if (sum % 2 == 1 || !knapsack(data, sum / 2)) {
      print.printLine("0");
    } else {
      print.printLine("1");
      while (true) {
        int odd = -1;
        for (int i = 0; i < n; i++) {
          if (data[i] % 2 == 0) {
            data[i] = data[i] / 2;
          } else {
            odd = i + 1;
            break;
          }
        }

        if (odd != -1) {
          print.printLine(Integer.toString(odd));
          break;
        }
      }
    }
    print.close();


  }

  private static boolean knapsack(int[] data, int target) {
    int[][] dp = new int[data.length + 1][target + 1];

    for (int i = 1; i < dp.length; i++) {
      int val = data[i - 1];
      for (int j = 1; j < dp[i].length; j++) {
        if (val > j) {
          dp[i][j] = dp[i - 1][j];
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], val + dp[i - 1][j - val]);
        }
      }
    }

    return target == dp[data.length][target];
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
