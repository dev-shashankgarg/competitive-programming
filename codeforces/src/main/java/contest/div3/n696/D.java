package contest.div3.n696;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class D {

  private static TreeSet<Long> primes;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int[] sieve = new int[1000000];
    primes = new TreeSet<>();
    sieve[0] = 1;
    sieve[1] = 1;
    for (int i = 2; i < sieve.length; i++) {
      if (sieve[i] == 0) {
        primes.add((long) i);
        for (int j = 2 * i; j < sieve.length; j += i) {
          sieve[j] = 1;
        }
      }
    }

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {
      long n = Long.parseLong(scan.scanString());
      if (isPrime(n)) {
        print.printLine("1");
        print.printLine(Long.toString(n));
      } else {
        long[] arr = solve(n);
        print.printLine(Integer.toString(arr.length));
        StringJoiner sj = new StringJoiner(" ");
        for (long x : arr) {
          sj.add(Long.toString(x));
        }
        print.printLine(sj.toString());
      }
    });

    print.close();

  }

  private static long[] solve(long n) {
    int max = 0;
    Map<Long, Integer> map = new HashMap<>();
    for (long x : primes) {
      if (n > 1) {
        if (n % x == 0) {
          while (n % x == 0) {
            map.put(x, map.getOrDefault(x, 0) + 1);
            max = Math.max(max, map.get(x));
            n = n / x;
          }
        }
      } else {
        break;
      }
    }

    if (n > 1) {
      if (isPrime(n)) {
        map.put(n, map.getOrDefault(n, 0) + 1);
        max = Math.max(max, map.get(n));
      } else {
        long factor = pollRho(n);
        map.put(factor, map.getOrDefault(factor, 0) + 1);
        map.put(n / factor, map.getOrDefault(n / factor, 0) + 1);
        max = Math.max(max, map.get(factor));
        max = Math.max(max, map.get(n / factor));
      }
    }

    long[] arr = new long[max];
    Arrays.fill(arr, 1);
    for (long key : map.keySet()) {
      for (int j = max - 1; j >= max - map.get(key); j--) {
        arr[j] *= key;
      }
    }

    return arr;
  }

  private static boolean isPrime(long n) {
    for (int i = 2; i <= Math.sqrt(n); i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }

  public static long pollRho(long num) {
    long x = 2, y = 2, d = 1;
    while (d == 1) {
      x = g(x, num);
      y = g(g(y, num), num);
      d = gcd((x - y), num);
    }

    if (d == num) {
      return num;
    }
    return d;
  }


  public static int gcd(long a, long b) {
    int gcd = 0;
    for (int i = 1; i <= a || i <= b; i++) {
      if (a % i == 0 && b % i == 0) {
        gcd = i;
      }
    }
    return gcd;
  }

  public static long g(long x, long num) {
    return ((x * x) - 1) % num;
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