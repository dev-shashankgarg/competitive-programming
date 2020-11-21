package contest.div2.n685;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class B {

  private static int[] data;
  private static int[] prefix;
  private static int queries;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {
      int n = scan.scanInt();
      queries = scan.scanInt();
      data = new int[n];
      prefix = new int[n];
      String in = scan.scanString();
      for (int i = 0; i < n; i++) {
        data[i] = (in.charAt(i) == '0') ? 0 : 1;
      }
      preCompute();

      for (int i = 0; i < queries; i++) {
        int lq = scan.scanInt() - 1;
        int rq = scan.scanInt() - 1;

        boolean found = false;

        if (data[lq] == 1) {
          if (hasOne(0, lq - 1)) {
            found = true;
          }
        } else {
          if (hasZero(0, lq - 1)) {
            found = true;
          }
        }

        if (data[rq] == 1) {
          if (hasOne(rq + 1, data.length - 1)) {
            found = true;
          }
        } else {
          if (hasZero(rq + 1, data.length - 1)) {
            found = true;
          }
        }

        print.printLine(found ? "YES" : "NO");
      }
    });

    print.close();


  }

  private static void preCompute() {
    prefix[0] = data[0];
    for (int i = 1; i < data.length; i++) {
      prefix[i] = prefix[i - 1] + data[i];
    }
  }

  private static boolean hasOne(int l, int r) {

    if (l > r) {
      return false;
    }

    if (l < 0 || r >= data.length) {
      return false;
    }

    if (l == 0) {
      return prefix[r] > 0;
    }

    return (prefix[r] - prefix[l - 1] > 0);
  }

  private static boolean hasZero(int l, int r) {

    if (l > r) {
      return false;
    }

    if (l < 0 || r >= data.length) {
      return false;
    }

    if (l == 0) {
      return prefix[r] != (r + 1);
    }

    int ones = prefix[r] - prefix[l - 1];
    return ones != (r - l + 1);
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