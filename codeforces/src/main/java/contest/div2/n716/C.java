package contest.div2.n716;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.StringJoiner;
import java.util.TreeMap;

public class C {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

//    for (int i = 2; i <= 20; i += 1) {
//      brute_force(i);
//    }

    int n = scan.scanInt();
    solve(print, n);
    print.close();


  }

  private static void brute_force(int i) {
    int[] arr = new int[i - 1];
    for (int j = 1; j <= arr.length; j++) {
      arr[j - 1] = j;
    }
    TreeMap<Integer, String> m = new TreeMap<>();

    int k = arr.length + 1;
    for (int l = (1 << k) - 1; l >= 0; l--) {
      long v = 1;
      for (int g = 0; g < arr.length; g++) {
        if (((l >> g) & 1) == 1) {
          v = (v * arr[g]) % k;
        }
      }

      if (v == 1) {
        int count = 0;
        StringJoiner sj = new StringJoiner(" ");
        for (int g = 0; g < arr.length; g++) {
          if (((l >> g) & 1) == 1) {
            count++;
            sj.add(Integer.toString(arr[g]));
          }
        }
        m.put(count, sj.toString());
      }
    }

    System.out.printf("for num = %d , max length is : %d %n", i, m.lastKey());
    System.out.println(m.get(m.lastKey()));
  }

  private static void solve(Print print, int n) {

    int co = 1;
    StringJoiner sj = new StringJoiner(" ");
    sj.add("1");
    for (int i = 2; i < n; i++) {
      if (gcd(i, n) == 1) {
        co++;
        sj.add(Integer.toString(i));
      }
    }

    print.printLine(Integer.toString(co));
    print.printLine(sj.toString());


  }

  static int gcd(int a, int b) {
    while (b != 0) {
      int t = a;
      a = b;
      b = t % b;
    }
    return a;
  }

  static int modInverse(int a, int m) {
    int m0 = m;
    int y = 0, x = 1;

    if (m == 1) {
      return 0;
    }

    while (a > 1) {
      // q is quotient
      int q = a / m;

      int t = m;

      // m is remainder now, process
      // same as Euclid's algo
      m = a % m;
      a = t;
      t = y;

      // Update x and y
      y = x - q * y;
      x = t;
    }

    // Make x positive
    if (x < 0) {
      x += m0;
    }

    return x;
  }

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
