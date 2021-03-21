package com.kickstart.y2021.a;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

class B {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    Scanner sc = new Scanner(System.in);

    int tests = Integer.parseInt(sc.nextLine());
    IntStream.rangeClosed(1, tests).forEach(test -> {

      String ss = sc.nextLine();
      String[] arr1 = ss.split(" ");

      int r = Integer.parseInt(arr1[0]);
      int c = Integer.parseInt(arr1[1]);

      int[][] arr = new int[r][c];
      for (int i = 0; i < r; i++) {
        String s = sc.nextLine();
        for (int j = 0, k = 0; j < s.length(); j += 2, k++) {
          int x = s.charAt(j) - '0';
          arr[i][k] = x;
        }
      }

      int ans = 0;

      ans += solve(arr, r, c, 0, 0);
      ans += solve(arr, r, c, 1, 0);
      ans += solve(arr, r, c, 1, 1);
      ans += solve(arr, r, c, 0, 1);

      print.printLine(String.format("Case #%d: %d", test, ans));

    });

    print.close();
  }

  private static int solve(int[][] arr, int r, int c, int id, int jd) {

    int[][] rsums = new int[r][c];
    int[][] csums = new int[r][c];

    //data prepare

    if (id == 0) {
      for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
          if (i == 0) {
            rsums[i][j] = arr[i][j];
          } else {
            rsums[i][j] = (arr[i][j] == 1 ? 1 + rsums[i - 1][j] : 0);
          }
        }
      }
    } else {
      for (int i = r - 1; i >= 0; i--) {
        for (int j = 0; j < c; j++) {
          if (i == r - 1) {
            rsums[i][j] = arr[i][j];
          } else {
            rsums[i][j] = (arr[i][j] == 1 ? 1 + rsums[i + 1][j] : 0);
          }
        }
      }
    }

    if (jd == 0) {
      for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
          if (j == 0) {
            csums[i][j] = arr[i][j];
          } else {
            csums[i][j] = (arr[i][j] == 1 ? 1 + csums[i][j - 1] : 0);
          }
        }
      }
    } else {
      for (int i = 0; i < r; i++) {
        for (int j = c - 1; j >= 0; j--) {
          if (j == c - 1) {
            csums[i][j] = arr[i][j];
          } else {
            csums[i][j] = (arr[i][j] == 1 ? 1 + csums[i][j + 1] : 0);
          }
        }
      }
    }

    //check all pivots
    int ans = 0;
    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {

        int ll1 = rsums[i][j];
        int sl1 = csums[i][j];

        if (ll1 % 2 == 1) {
          ll1--;
        }
        ll1 = Math.min(ll1, 2 * sl1);

        int p1 = ll1 / 2;
        int p2 = sl1;

        ans += Math.max(0, Math.min(p1, p2) - 1);

        int sl2 = rsums[i][j];
        int ll2 = csums[i][j];

        if (ll2 % 2 == 1) {
          ll2--;
        }
        ll2 = Math.min(ll2, 2 * sl2);

        p1 = ll2 / 2;
        p2 = sl2;

        ans += Math.max(0, Math.min(p1, p2) - 1);
      }
    }

    return ans;
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
