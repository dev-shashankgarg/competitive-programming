package contest.dec20b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class POSPREFS {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {

      int n = scan.scanInt();
      int k = scan.scanInt();
      int[] arr = solve(n, k);

      StringJoiner sj = new StringJoiner(" ");
      for (int x : arr) {
        sj.add(Integer.toString(x));
      }
      print.printLine(sj.toString());
    });
    print.close();
  }

  private static void assert_arr(int[] arr, int k) {
    int[] pre = new int[arr.length];
    pre[0] = arr[0];
    for (int i = 1; i < arr.length; i++) {
      pre[i] = pre[i - 1] + arr[i];
    }

    int count = 0;
    for (int x : pre) {
      if (x > 0) {
        count++;
      }
    }

    if (k != count) {
      System.out.println("Failed");
    } else {
      System.out.println("Success");
    }


  }

  private static int[] solve(int n, int k) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = i + 1;
      if (i % 2 == 1) {
        arr[i] *= -1;
      }
    }

    int lp = (n % 2 == 0) ? n - 2 : n - 1;
    int ln = (n % 2 == 0) ? n - 1 : n - 2;

    int tp = (n % 2 == 1) ? (n / 2) + 1 : n / 2;

    if (k < tp) {
      while (k < tp) {
        arr[lp] *= -1;
        tp--;
        lp -= 2;
      }
    } else if (k > tp) {
      while (k > tp) {
        arr[ln] *= -1;
        tp++;
        ln -= 2;
      }
    }

    return arr;
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