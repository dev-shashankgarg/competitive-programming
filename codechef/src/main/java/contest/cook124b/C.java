package contest.cook124b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.IntStream;

public class C {

  private static int an;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {

      int k = scan.scanInt();
      int x = scan.scanInt();
      an = Integer.MAX_VALUE;
      int ans = solve(k, x);
      print.printLine(Integer.toString(ans));
    });

    print.close();

  }

  private static int solve(int k, int x) {
    int min = k - 2 + 1 + x;
    HashMap<Integer, Integer> map = new HashMap<>();
    int i = 2;
    while (x > 1) {
      if (x % i == 0) {
        int val = map.getOrDefault(i, 1);
        map.put(i, val * i);
        x /= i;
      } else {
        i++;
      }
    }

    if (k >= map.values().size()) {
      int sum = 0;
      for (int y : map.values()) {
        sum += y;
      }
      sum += (k - map.values().size());

      min = Math.min(sum, min);
      return min;
    }

    int[] buckets = new int[k];
    Arrays.fill(buckets, 1);
    findMinBucket(buckets, new ArrayList<>(map.values()), 0);
    min = Math.min(an, min);

    return min;
  }

  private static void findMinBucket(int[] buckets, List<Integer> pri, int index) {
    if (index == pri.size()) {
      int sum = 0;
      for (int x : buckets) {
        sum += x;
      }
      an = Math.min(sum, an);
    } else {
      for (int k = 0; k < buckets.length; k++) {
        buckets[k] *= pri.get(index);
        findMinBucket(buckets, pri, index + 1);
        buckets[k] /= pri.get(index);
      }
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
