package com.kickstart.y2021.b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class C {

  //private static List<Integer> primes;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

//    primes = new ArrayList<>();
//    int[] ss = new int[1_000_000];
//    ss[0] = 1;
//    ss[1] = 1;
//    for (int i = 2; i < ss.length; i++) {
//      if (ss[i] == 0) {
//        primes.add(i);
//        for (int j = i + i; j < ss.length; j += i) {
//          ss[j] = 1;
//        }
//      }
//    }

//    for (int i = 6; i <= 1_000_000_009; i++) {
//      long a = solve(i);
//      long b = solve2(i);
//
//      if (a != b) {
//        System.out.printf("Case %d -> Expected : %d , Actual : %d %n", i, b, a);
//      }
//    }

    int tests = scan.scanInt();
    IntStream.rangeClosed(1, tests).forEach(test -> {
      String s = scan.scanString();
      long ans = solve(Long.parseLong(s));
      print.printLine(String.format("Case #%d: %d", test, ans));

    });

    print.close();
  }

//  private static long solve2(int x) {
//    for (int i = 2; i < primes.size() - 1; i++) {
//      if (((long) primes.get(i) * (long) (primes.get(i - 1)) > x)) {
//        return (long) primes.get(i - 2) * (long) (primes.get(i - 1));
//      }
//    }
//    return -1;
//  }

  private static long solve(long num) {

    //4216282
    long start = (long) Math.ceil(Math.sqrt(num));
    long first = start;
    long second = start;

    while (first > 0 && !BigInteger.valueOf(first).isProbablePrime(100)) {
      first--;
    }

    if (second != first) {
      while (second > 0 && !BigInteger.valueOf(second).isProbablePrime(100)) {
        second++;
      }

      if (second * first <= num) {
        return second * first;
      }

    }
    second = first - 1;
    while (second > 0 && !BigInteger.valueOf(second).isProbablePrime(100)) {
      second--;
    }

    return second * first;
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
