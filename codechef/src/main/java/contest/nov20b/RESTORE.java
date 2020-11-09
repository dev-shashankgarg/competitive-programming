package contest.nov20b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class RESTORE {

  private static List<Integer> primes;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    init();

    int tests = scan.scanInt();
    for (int i = 0; i < tests; i++) {
      int size = scan.scanInt();
      int[] arr = new int[size];

      for (int j = 0; j < arr.length; j++) {
        arr[j] = scan.scanInt();
      }

      int[] result = solve(arr);
      StringJoiner sj = new StringJoiner(" ");
      for (int x : result) {
        sj.add(Integer.toString(x));
      }
      print.printLine(sj.toString());
    }
    print.close();
  }

  private static void init() {
    int MAX = 4000000;

    primes = new ArrayList<>();

    int[] sieve = new int[MAX];

    sieve[0] = 1;
    sieve[1] = 1;

    for (int i = 2; i < sieve.length; i++) {
      if (sieve[i] == 0) {
        primes.add(i);
        for (int j = 2 * i; j < sieve.length; j += i) {
          sieve[j] = 1;
        }
      }
    }
  }

  private static int[] solve(int[] arr) {

    int[] result = new int[arr.length];

    Map<Integer, Integer> map = new HashMap<>();
    for (int x : arr) {
      map.put(x, map.getOrDefault(x, 0) + 1);
    }

    Map<Integer, Integer> alloc = new HashMap<>();
    int j = 0;

    for (int i = arr.length - 1; i >= 0; i--) {
      int val = arr[i];
      int prime;
      if (alloc.containsKey(val)) {
        prime = alloc.get(val);
      } else {
        prime = primes.get(j);
        j++;
        alloc.put(val, prime);
      }

      result[i] = prime;
    }

    return result;
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