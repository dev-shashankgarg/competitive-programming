package contest.march21b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class COLGLF4 {

  private static int n;
  private static int a;
  private static int b;
  private static int c;

  private static int e;
  private static int h;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      n = scan.scanInt();
      e = scan.scanInt();
      h = scan.scanInt();
      a = scan.scanInt();
      b = scan.scanInt();
      c = scan.scanInt();

      e = Math.min(e, 2 * n);
      h = Math.min(h, 3 * n);

      //long ans = valid(e, h, n) ? solve(e, h, 0) : -1;
      //print.printLine(Long.toString(solve2(e, h)));
    });

    print.close();
  }

//  private static long solve2(int e, int h) {
//    if (c <= a && c <= b) {
//      long x = Math.min(e, h);
//      e -= x;
//      h -= x;
//
//      if (x >= n) {
//        possible = true;
//        return (long) c * n;
//      } else {
//        long csf = c * x;
//        long rem = n - x;
//
//        if (e != 0) {
//          if ((e / 2) >= rem) {
//            return csf + rem * a;
//          }
//        } else {
//          if ((h / 3) >= rem) {
//            return csf + rem * b;
//          }
//        }
//        return -1;
//      }
//    } else if (c >= a && c >= b) {
//      int x = bs(0, Math.min(e, h));
//      long csf = (long) x * c;
//
//      e -= x;
//      h -= x;
//      n -= x;
//
//      if (a <= b) {
//        int max_a = e / 2;
//        if (max_a >= n) {
//          possible = true;
//          return csf + (long) n * a;
//        } else {
//          int max_b = h / 3;
//          if (max_a + max_b < n) {
//            return -1;
//          } else {
//            possible = true;
//            return csf + (long) max_a * a + (long) (n - max_a) * b;
//          }
//        }
//      } else {
//        int max_b = h / 3;
//        if (max_b >= n) {
//          possible = true;
//          return csf + (long) n * b;
//        } else {
//          int max_a = e / 2;
//          if (max_a + max_b < n) {
//            return -1;
//          } else {
//            possible = true;
//            return csf + (long) max_b * b + (long) (n - max_b) * a;
//          }
//        }
//      }
//
//
//    }
//
//    return -1;
//  }

//  private static long solve2(int e, int h) {
//    int shared = bs(0, Math.min(e, h));
//    if (shared < 0) {
//      return -1;
//    }
//    shared = Math.min(shared, n);
//    int csf = shared * c;
//    e -= shared;
//    h -= shared;
//    n -= shared;
//
//    if (n == 0) {
//      return csf;
//    }
//
//    if (c <= a && c <= b) {
//      int x = Math.min(e, h);
//      if (x >= n) {
//        return csf + (long) n * c;
//      } else {
//        e -= x;
//        h -= x;
//        n -= x;
//        csf += x * c;
//        if (e > 0) {
//          return csf + (long) a * n;
//        } else {
//          return csf + (long) b * n;
//        }
//      }
//    } else if (c >= a && c >= b) {
//      if (a <= b) {
//        int x = e / 2;
//        if (x >= n) {
//          return csf + (long) n * a;
//        } else {
//          n -= x;
//          return csf + (long) x * a + (long) n * b;
//        }
//      } else {
//        int x = h / 3;
//        if (x >= n) {
//          return csf + (long) n * b;
//        } else {
//          n -= x;
//          return csf + (long) x * b + (long) n * a;
//        }
//      }
//    }
//
//
//  }

  private static int bs(int l, int r) {

    if (l > r) {
      return -1;
    }

    int mid = l + (r - l) / 2;

    if (val(mid) && (mid == 0 || !val(mid - 1))) {
      return mid;
    } else if (!val(mid)) {
      return bs(mid + 1, r);
    }

    return bs(l, mid - 1);
  }

  private static boolean val(int i) {
    return i + ((e - i) / 2) + ((h - i) / 3) >= n;
  }

//  private static long solve(int e, int h, int index) {
//    if (index == n) {
//      possible = true;
//      return 0;
//    } else {
//      long sum = Integer.MAX_VALUE;
//      if (e >= 2 && valid(e - 2, h, n - index - 1)) {
//        sum = Math.min(sum, a + solve(e - 2, h, index + 1));
//      }
//      if (h >= 3 && valid(e, h - 3, n - index - 1)) {
//        sum = Math.min(sum, b + solve(e, h - 3, index + 1));
//      }
//      if (e >= 1 && h >= 1 && valid(e - 1, h - 1, n - index - 1)) {
//        sum = Math.min(sum, c + solve(e - 1, h - 1, index + 1));
//      }
//
//      return sum;
//    }
//  }

  private static boolean valid(int e, int h, int n) {
    int x = Math.min(e, h);
    return (x + (e - x) / 2 + (h - x) / 3) >= n;
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
