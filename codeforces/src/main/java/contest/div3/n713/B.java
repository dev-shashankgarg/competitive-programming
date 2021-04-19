package contest.div3.n713;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class B {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      int n = scan.scanInt();
      char[][] arr = new char[n][n];

      for (int i = 0; i < n; i++) {
        char[] x = scan.scanString().toCharArray();
        System.arraycopy(x, 0, arr[i], 0, x.length);
      }
      solve(arr, n);
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          print.print(Character.toString(arr[i][j]));
        }
        print.printLine("");
      }

    });

    print.close();

  }

  private static void solve(char[][] arr, int n) {
    int fi = -1, fj = -1, si = -1, sj = -1;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (arr[i][j] == '*') {
          if (fi == -1) {
            fi = i;
            fj = j;
          } else {
            si = i;
            sj = j;
          }
        }
      }
    }

    if (fi != si && fj != sj) {
      arr[fi][sj] = '*';
      arr[si][fj] = '*';
    } else if (fi == si) {
      int ii = (n + fi - 1) % n;
      arr[ii][sj] = '*';
      arr[ii][fj] = '*';
    } else {
      int jj = (n + fj - 1) % n;
      arr[si][jj] = '*';
      arr[fi][jj] = '*';
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