package contest.educational.n108;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;

public class D {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

//    int t = scan.scanInt();
//    IntStream.range(0, t).forEach(test -> {
//
//    });

    int n = scan.scanInt();
    long[] a = new long[n];
    long[] b = new long[n];

    long sum = 0;

    String[] arr = scan.scanString().split(" ");
    for (int i = 0; i < arr.length; i++) {
      a[i] = Long.parseLong(arr[i]);
    }

    arr = scan.scanString().split(" ");
    for (int i = 0; i < arr.length; i++) {
      b[i] = Long.parseLong(arr[i]);
      sum += (b[i] * a[i]);
    }

    long[][] dp = new long[n][n];
    // 0 1 2 3 4 5

    for (int gap = 1; gap < n; gap++) {
      for (int i = 0; i + gap < n; i++) {
        int j = i + gap;
        long diff = (a[j] * b[i] + a[i] * b[j]) - (a[i] * b[i] + a[j] * b[j]);
        if (j - i <= 2) {
          dp[i][j] = diff;
        } else {
          dp[i][j] = diff + dp[i + 1][j - 1];
        }
      }
    }

    long max = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        max = Math.max(max, dp[i][j]);
      }
    }

    print.printLine(Long.toString(max + sum));

    print.close();

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