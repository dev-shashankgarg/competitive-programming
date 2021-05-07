package contest.div2.n720;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class A {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      Long[] longs = scan.scan1dLongArray();
      long a = longs[0];
      long b = longs[1];

      if (a % (a * b) == 0) {
        print.printLine("NO");
      } else {
        long x = (b - 1) * (a);
        long y = (b + 1) * (a);
        long z = 2 * a * b;

        print.printLine("YES");
        print.printLine(new Long[]{x, y, z});

      }
    });

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

    public Integer[] scan1dIntArray() {
      String[] s = this.scanString().split(" ");
      Integer[] arr = new Integer[s.length];
      for (int i = 0; i < s.length; i++) {
        arr[i] = Integer.parseInt(s[i]);
      }
      return arr;
    }

    public Integer[][] scan2dIntArray(int n, int m) {
      Integer[][] arr = new Integer[n][m];
      for (int i = 0; i < n; i++) {
        String[] s = this.scanString().split(" ");
        for (int j = 0; j < m; j++) {
          arr[i][j] = Integer.parseInt(s[i]);
        }
      }
      return arr;
    }

    public String[] scan1dStringArray() {
      return this.scanString().split(" ");
    }

    public String[][] scan2dStringArray(int n, int m) {
      String[][] arr = new String[n][m];
      for (int i = 0; i < n; i++) {
        String[] s = this.scanString().split(" ");
        for (int j = 0; j < m; j++) {
          arr[i][j] = s[i];
        }
      }
      return arr;
    }

    public Long[] scan1dLongArray() {
      String[] s = this.scanString().split(" ");
      Long[] arr = new Long[s.length];
      for (int i = 0; i < s.length; i++) {
        arr[i] = Long.parseLong(s[i]);
      }
      return arr;
    }

    public Long[][] scan2dLongArray(int n, int m) {
      Long[][] arr = new Long[n][m];
      for (int i = 0; i < n; i++) {
        String[] s = this.scanString().split(" ");
        for (int j = 0; j < m; j++) {
          arr[i][j] = Long.parseLong(s[i]);
        }
      }
      return arr;
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

    public void printLine(Integer[] arr) {
      StringJoiner sj = new StringJoiner(" ");
      for (Integer x : arr) {
        sj.add(Integer.toString(x));
      }
      printLine(sj.toString());
    }

    public void printLine(Integer[][] arr) {
      for (Integer[] x : arr) {
        StringJoiner sj = new StringJoiner(" ");
        for (Integer y : x) {
          sj.add(Integer.toString(y));
        }
        printLine(sj.toString());
      }
    }

    public void printLine(String[] arr) {
      StringJoiner sj = new StringJoiner(" ");
      for (String x : arr) {
        sj.add(x);
      }
      printLine(sj.toString());
    }

    public void printLine(String[][] arr) {
      for (String[] x : arr) {
        StringJoiner sj = new StringJoiner(" ");
        for (String y : x) {
          sj.add(y);
        }
        printLine(sj.toString());
      }
    }

    public void printLine(Long[] arr) {
      StringJoiner sj = new StringJoiner(" ");
      for (Long x : arr) {
        sj.add(Long.toString(x));
      }
      printLine(sj.toString());
    }

    public void printLine(Long[][] arr) {
      for (Long[] x : arr) {
        StringJoiner sj = new StringJoiner(" ");
        for (Long y : x) {
          sj.add(Long.toString(y));
        }
        printLine(sj.toString());
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
