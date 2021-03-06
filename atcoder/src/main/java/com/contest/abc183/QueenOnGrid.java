package com.contest.abc183;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.InputMismatchException;

public class QueenOnGrid {

  private static int length;
  private static int width;
  private static HashSet<Integer> walls;
  private static int[][] dp;
  private static int[][] preRow;
  private static int[][] preCol;
  private static int[][] preDia;
  private static int MOD = 1_000_000_007;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    length = scan.scanInt();
    width = scan.scanInt();

    walls = new HashSet<>();

    for (int i = 0; i < length; i++) {
      String input = scan.scanString();
      for (int j = 0; j < input.length(); j++) {
        if (input.charAt(j) == '#') {
          walls.add(i * width + j);
        }
      }
    }

    dp = new int[length + 1][width + 1];
    preRow = new int[length + 1][width + 1];
    preCol = new int[length + 1][width + 1];
    preDia = new int[length + 1][width + 1];

    dp[1][1] = preCol[1][1] = preDia[1][1] = preRow[1][1] = 1;

    for (int i = 1; i < dp.length; i++) {
      for (int j = 1; j < dp[i].length; j++) {
        if (i == 1 && j == 1) {
          continue;
        }

        if (walls.contains((i - 1) * width + j - 1)) {
          continue;
        }

        dp[i][j] = (preRow[i][j - 1] + (preDia[i - 1][j - 1] + preCol[i - 1][j]) % MOD) % MOD;
        preRow[i][j] = preCol[i][j] = preDia[i][j] = dp[i][j];

        preRow[i][j] = (preRow[i][j] + preRow[i][j - 1]) % MOD;
        preCol[i][j] = (preCol[i][j] + preCol[i - 1][j]) % MOD;
        preDia[i][j] = (preDia[i][j] + preDia[i - 1][j - 1]) % MOD;
      }
    }

    print.printLine(Integer.toString(dp[length][width]));
    print.close();

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