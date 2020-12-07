package contest.dec20b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class HXOR {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {
      int n = scan.scanInt();
      int x = scan.scanInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = scan.scanInt();
      }

      solve(arr, x);

      StringJoiner sj = new StringJoiner(" ");
      for (int y : arr) {
        sj.add(Integer.toString(y));
      }
      print.printLine(sj.toString());
    });

    print.close();

  }

  private static void solve(int[] arr, int x) {
    Set<Integer> set = new HashSet<>();
    int ops = 0;
    for (int i = 0; i < arr.length - 1; i++) {
      int num = 0;
      for (int j = 31; j >= 0; j--) {
        int bit = (arr[i] >> j) & 1;
        if (bit == 1) {
          if (set.contains(j)) {
            bit = 0;
            set.remove(j);
          } else if (ops < x) {
            bit = 0;
            set.add(j);
            ops++;
          }
        }
        num = (bit << j) | num;
      }
      arr[i] = num;
    }

    int num = arr[arr.length - 1];
    int val = 0;
    for (int j = 31; j >= 0; j--) {
      int bit = (num >> j) & 1;
      if (set.contains(j)) {
        bit = (bit + 1) % 2;
      }
      val = (bit << j) | val;
    }
    arr[arr.length - 1] = val;

    int rem = x - ops;

    if ((arr.length >= 3 && rem == 1) || (arr.length == 2 && rem % 2 == 1)) {
      arr[arr.length - 2] = 1;
      int bit = arr[arr.length - 1] & 1;
      bit = (bit + 1) % 2;
      int v = ((arr[arr.length - 1] >> 1) << 1) | bit;
      arr[arr.length - 1] = v;
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