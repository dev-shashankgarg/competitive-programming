package contest.div2.n685;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class C {

  private static int[] a;
  private static int[] b;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {

      int n = scan.scanInt();
      int k = scan.scanInt();

      a = new int[26];
      b = new int[26];

      String in = scan.scanString();
      for (char c : in.toCharArray()) {
        a[c - 'a']++;
      }

      in = scan.scanString();
      for (char c : in.toCharArray()) {
        b[c - 'a']++;
      }

      boolean convertible = true;
      int i = 0, j = 0;

      while (i < a.length && j < b.length) {
        if (a[i] == 0) {
          i++;
          continue;
        }

        if (b[j] == 0) {
          j++;
          continue;
        }

        if (i == j) {
          //wrong
          if (b[j] > a[i] || (a[i] - b[j]) % k != 0) {
            convertible = false;
            break;
          } else {
            if (a[i] != b[j]) {
              int diff = a[i] - b[j];
              if (i == 25) {
                convertible = false;
                break;
              } else {
                a[i + 1] += diff;
              }
            }
            i++;
            j++;
          }
        } else if (i < j) {
          if (a[i] % k != 0 || i == 25) {
            convertible = false;
            break;
          }

          a[i + 1] += a[i];
          i++;
        } else {
          convertible = false;
          break;
        }
      }

      while (i < a.length) {
        if (a[i] > 0) {
          convertible = false;
          break;
        }
        i++;
      }

      while (j < b.length) {
        if (b[j] > 0) {
          convertible = false;
          break;
        }
        j++;
      }

      print.printLine(convertible ? "Yes" : "No");
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