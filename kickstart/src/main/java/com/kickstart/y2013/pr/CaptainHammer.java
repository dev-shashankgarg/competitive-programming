package com.kickstart.y2013.pr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

/**
 * Problem The Hamjet is a true marvel of aircraft engineering. It is a jet airplane with a single
 * engine so powerful that it burns all of its fuel instantly during takeoff. The Hamjet doesn't
 * have any wings because who needs them when the fuselage is made of a special Wonderflonium
 * isotope that makes it impervious to harm.
 * <p>
 * Piloting the Hamjet is a not a job for your typical, meek-bodied superhero. That's why the Hamjet
 * belongs to Captain Hammer, who is himself impervious to harm. The G-forces that the pilot endures
 * when taking a trip in the Hamjet are legen-dary.
 * <p>
 * The Hamjet takes off at an angle of θ degrees up and a speed of V meters per second. V is a fixed
 * value that is determined by the awesome power of the Hamjet engine and the capacity of its fuel
 * tank. The destination is D meters away. Your job is to program the Hamjet's computer to calculate
 * θ given V and D.
 * <p>
 * Fortunately, the Hamjet's Wondeflonium hull is impervious to air friction. Even more fortunately,
 * the Hamjet doesn't fly too far or too high, so you can assume that the Earth is flat, and that
 * the acceleration due to gravity is a constant 9.8 m/s2 down.
 * <p>
 * Input The first line of the input gives the number of test cases, T. T lines follow. Each line
 * will contain two positive integers -- V and D.
 * <p>
 * Output For each test case, output one line containing "Case #x: θ", where x is the case number
 * (starting from 1) and θ is in degrees up from the the horizontal. If there are several possible
 * answers, output the smallest positive one.
 * <p>
 * An answer will be considered correct if it is within 10-6 of the exact answer, in absolute or
 * relative error. See the FAQ for an explanation of what that means, and what formats of
 * floating-point numbers we accept.
 * <p>
 * Limits Time limit: 60 seconds. Memory limit: 1 GB. 1 ≤ T ≤ 4500; 1 ≤ V ≤ 300; 1 ≤ D ≤ 10000; It
 * is guaranteed that each test case will be solvable.
 * <p>
 * Sample
 * <p>
 * Input
 * <p>
 * Output
 * <p>
 * 3 98 980 98 490 299 1234
 * <p>
 * <p>
 * Case #1: 45.0000000 Case #2: 15.0000000 Case #3: 3.8870928
 */
public class CaptainHammer {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.rangeClosed(1, tests).forEach(test -> {

      int v = scan.scanInt();
      int s = scan.scanInt();

      double ans = solve(v, s);
      print.printLine(String.format("Case #%d: %.7f", test, ans));

    });

    print.close();
  }

  private static double solve(int v, int s) {

    double val = s * 9.8;
    val = val / (v * v);
    val = Math.asin(val > 1.0 ? (1.0) : Math.max(val, -1.0));

    val = val / 2.0;

    return Math.toDegrees(val);
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
