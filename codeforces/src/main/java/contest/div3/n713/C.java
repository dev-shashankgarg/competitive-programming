package contest.div3.n713;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class C {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      int a = scan.scanInt();
      int b = scan.scanInt();
      char[] arr = scan.scanString().toCharArray();

      String ans = solve(arr, a, b);
      print.printLine(ans);
    });

    print.close();

  }

  private static String solve(char[] arr, int a, int b) {
    int i = 0, j = arr.length - 1;

    while (i <= j) {
      if (arr[i] == arr[j]) {
        if (arr[i] == '0') {
          a -= (i == j ? 1 : 2);
        } else if (arr[j] == '1') {
          b -= (i == j ? 1 : 2);
        }
      } else if (arr[i] != arr[j] && arr[i] != '?' && arr[j] != '?') {
        return "-1";
      } else if (arr[i] == '0' || arr[j] == '0') {
        arr[i] = '0';
        arr[j] = '0';
        a -= 2;
      } else {
        arr[i] = '1';
        arr[j] = '1';
        b -= 2;
      }

      if (a < 0 || b < 0) {
        return "-1";
      }

      i++;
      j--;
    }

    i = 0;
    j = arr.length - 1;

    while (i <= j) {

      if (arr[i] == '?') {
        if (a > b) {
          a -= (i == j ? 1 : 2);
          arr[i] = '0';
          arr[j] = '0';
        } else {
          b -= (i == j ? 1 : 2);
          arr[i] = '1';
          arr[j] = '1';
        }
      }

      if (a < 0 || b < 0) {
        return "-1";
      }

      i++;
      j--;
    }

    return new String(arr);
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
