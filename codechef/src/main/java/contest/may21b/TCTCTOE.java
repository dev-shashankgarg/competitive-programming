package contest.may21b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class TCTCTOE {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      char[][] mat = scan.scan2dCharArray(3, 3);
      print.printLine(Integer.toString(solve(mat)));
    });

    print.close();

  }

  private static int solve(char[][] mat) {

    int xs = 0, os = 0, blanks = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (mat[i][j] == 'X') {
          xs++;
        } else if (mat[i][j] == 'O') {
          os++;
        } else {
          blanks++;
        }
      }
    }

    if (os > xs || Math.abs(os - xs) > 1 || (winner(mat, 'X') && winner(mat, 'O')) ||
        (winner(mat, 'O') && os != xs) || (winner(mat, 'X') && os == xs)) {
      return 3;
    }

    if (winner(mat, 'X') || winner(mat, 'O') || blanks == 0) {
      return 1;
    }

    return 2;
  }

  private static boolean winner(char[][] mat, char c) {
    return ((mat[0][0] == mat[1][1] && mat[1][1] == mat[2][2] && mat[2][2] == c) ||
        (mat[0][2] == mat[1][1] && mat[1][1] == mat[2][0] && mat[2][0] == c)) || rows(mat, c)
        || cols(mat, c);

  }

  private static boolean cols(char[][] mat, char c) {
    for (int j = 0; j < 3; j++) {
      int count = 0;
      for (int i = 0; i < 3; i++) {
        if (mat[i][j] == c) {
          count++;
        }
      }
      if (count == 3) {
        return true;
      }
    }
    return false;
  }

  private static boolean rows(char[][] mat, char c) {
    for (int i = 0; i < 3; i++) {
      int count = 0;
      for (int j = 0; j < 3; j++) {
        if (mat[i][j] == c) {
          count++;
        }
      }
      if (count == 3) {
        return true;
      }
    }
    return false;
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

    public Integer[] scan1dIntArray() {
      String[] s = this.scanString().split(" ");
      Integer[] arr = new Integer[s.length];
      for (int i = 0; i < s.length; i++) {
        arr[i] = Integer.parseInt(s[i]);
      }
      return arr;
    }

    public Integer[][] scan2dIntArray(int n, int m) {
      Integer[][] arr = new Integer[n][m];
      for (int i = 0; i < n; i++) {
        String[] s = this.scanString().split(" ");
        for (int j = 0; j < m; j++) {
          arr[i][j] = Integer.parseInt(s[j]);
        }
      }
      return arr;
    }

    public String[] scan1dStringArray() {
      return this.scanString().split(" ");
    }

    public String[][] scan2dStringArray(int n, int m) {
      String[][] arr = new String[n][m];
      for (int i = 0; i < n; i++) {
        String[] s = this.scanString().split(" ");
        for (int j = 0; j < m; j++) {
          arr[i][j] = s[j];
        }
      }
      return arr;
    }

    public char[] scan1dCharArray() {
      return this.scanString().toCharArray();
    }

    public char[][] scan2dCharArray(int n, int m) {
      char[][] arr = new char[n][m];
      for (int i = 0; i < n; i++) {
        char[] s = this.scanString().toCharArray();
        for (int j = 0; j < m; j++) {
          arr[i][j] = s[j];
        }
      }
      return arr;
    }

    public Long[] scan1dLongArray() {
      String[] s = this.scanString().split(" ");
      Long[] arr = new Long[s.length];
      for (int i = 0; i < s.length; i++) {
        arr[i] = Long.parseLong(s[i]);
      }
      return arr;
    }

    public Long[][] scan2dLongArray(int n, int m) {
      Long[][] arr = new Long[n][m];
      for (int i = 0; i < n; i++) {
        String[] s = this.scanString().split(" ");
        for (int j = 0; j < m; j++) {
          arr[i][j] = Long.parseLong(s[j]);
        }
      }
      return arr;
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
      if (n == '\n' || n == '\r' || n == '\t' || n == -1) {
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

    public void printLine(Integer[] arr) {
      StringJoiner sj = new StringJoiner(" ");
      for (Integer x : arr) {
        sj.add(Integer.toString(x));
      }
      printLine(sj.toString());
    }

    public void printLine(Integer[][] arr) {
      for (Integer[] x : arr) {
        StringJoiner sj = new StringJoiner(" ");
        for (Integer y : x) {
          sj.add(Integer.toString(y));
        }
        printLine(sj.toString());
      }
    }

    public void printLine(String[] arr) {
      StringJoiner sj = new StringJoiner(" ");
      for (String x : arr) {
        sj.add(x);
      }
      printLine(sj.toString());
    }

    public void printLine(String[][] arr) {
      for (String[] x : arr) {
        StringJoiner sj = new StringJoiner(" ");
        for (String y : x) {
          sj.add(y);
        }
        printLine(sj.toString());
      }
    }

    public void printLine(char[] arr) {
      StringJoiner sj = new StringJoiner(" ");
      for (char x : arr) {
        sj.add(Character.toString(x));
      }
      printLine(sj.toString());
    }

    public void printLine(char[][] arr) {
      for (char[] x : arr) {
        StringJoiner sj = new StringJoiner(" ");
        for (char y : x) {
          sj.add(Character.toString(y));
        }
        printLine(sj.toString());
      }
    }

    public void printLine(Long[] arr) {
      StringJoiner sj = new StringJoiner(" ");
      for (Long x : arr) {
        sj.add(Long.toString(x));
      }
      printLine(sj.toString());
    }

    public void printLine(Long[][] arr) {
      for (Long[] x : arr) {
        StringJoiner sj = new StringJoiner(" ");
        for (Long y : x) {
          sj.add(Long.toString(y));
        }
        printLine(sj.toString());
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
