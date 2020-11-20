package contest.educational.n98;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class D {

  private static Map<Long, Long> cache;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();
    long n = scan.scanInt();

    long MOD = 998244353;
    cache = new HashMap<>();

    long[] data = new long[Math.max((int) n + 1, 3)];
    data[1] = 1;
    data[2] = 1;

    for (int i = 3; i < data.length; i++) {
      data[i] = (data[i - 1] % MOD + data[i - 2] % MOD) % MOD;
    }

    long nFib = data[(int) n];
    long two_n = MO.pow(2, n, MOD);

    long ans = MO.divide(nFib, two_n, MOD);
    print.printLine(Long.toString(ans));
    print.close();
  }

  private static long fib(long n, long mod) {
    if (n == 1 || n == 2) {
      return 1;
    }

    if (cache.get(n) != null) {
      return cache.get(n);
    }

    long x = (fib(n - 1, mod) + fib(n - 2, mod)) % mod;
    cache.put(n, x);
    return x;
  }

  static class MO {

    //MOD Operations

    static long add(long a, long b, long MOD) {
      return (a % MOD + b % MOD) % MOD;
    }

    static long multiply(long a, long b, long MOD) {
      return (a % MOD * b % MOD) % MOD;
    }

    static long subtract(long a, long b, long MOD) {
      return ((a % MOD - b % MOD) % MOD + MOD) % MOD;
    }

    static long inverse(long a, long MOD) {
      return pow(a, MOD - 2, MOD);
    }

    static long divide(long a, long b, long MOD) {
      return multiply(a, inverse(b, MOD), MOD);
    }

    static long pow(long a, long n, long MOD) {
      if (n == 0) {
        return 1;
      }
      long x = pow(a, n / 2, MOD);
      if (n % 2 == 1) {
        return multiply(multiply(x, x, MOD), a, MOD);
      } else {
        return multiply(x, x, MOD);
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