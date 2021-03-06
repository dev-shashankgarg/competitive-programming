package contest.div2.n685;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.stream.IntStream;

public class D {

  private static long distance;
  private static long euc_distance;

  private static long move;

  private static Map<Long, Boolean> cache;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {
      distance = scan.scanInt();
      euc_distance = (long) Math.pow(distance, 2);
      move = scan.scanInt();

      long max = distance;
      long x = 0, y = distance;

      for (int _x = 0; _x < distance; _x += move) {
        long y_sq = euc_distance - (long) Math.pow(_x, 2);

        long y_root = (long) Math.sqrt(y_sq);

        if (y_root * y_root == y_sq) {
          if (_x + y_root > max) {
            max = Math.max(max, _x + y_root);
            x = _x;
            y = y_root;
          }
        }
      }

      long steps = x / move + y / move;

      //print.printLine(x + " " + y);
      print.printLine(steps % 2 == 1 ? "Ashish" : "Utkarsh");

//      cache = new HashMap<>();
//
//      boolean aWin = solve(0, 0);
//      print.printLine(aWin ? "Ashish" : "Utkarsh");
    });

    print.close();

  }

  private static long key(long x, long y) {
    return x * (distance + 10) + y;
  }

  private static boolean solve(long x, long y) {

    long key = key(x, y);
    Boolean val = cache.get(key);
    if (val != null) {
      return val;
    }

    boolean win = false;
    if (canMove(x + move, y)) {
      win = !solve(x + move, y);
    }

    if (!win && canMove(x, y + move)) {
      win = !solve(x, y + move);
    }
    cache.put(key, win);
    return win;
  }

  private static boolean canMove(long x, long y) {
    return (long) (Math.pow(x, 2) + Math.pow(y, 2)) <= euc_distance;
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