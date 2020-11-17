package contest.div2.n684;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.IntStream;

public class C {

  private static int[][] data;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {
      int n = scan.scanInt();
      int m = scan.scanInt();

      data = new int[n + 1][m + 1];

      for (int i = 1; i <= n; i++) {
        String in = scan.scanString();
        for (int j = 1; j <= in.length(); j++) {
          int c = in.charAt(j - 1) - 48;
          data[i][j] = c;
        }
      }
      List<Obj> moves = new ArrayList<>();

      for (int i = 1; i < data.length; i++) {
        for (int j = 1; j < data[i].length; j++) {
          if (data[i][j] == 1) {
            data[i][j] = 0;
            if (i != data.length - 1 && j != data[i].length - 1) {
              data[i + 1][j] = flip(i + 1, j);
              data[i + 1][j + 1] = flip(i + 1, j + 1);
              moves.add(new Obj(i, j, i + 1, j, i + 1, j + 1));
            } else if (i == data.length - 1 && j == data[i].length - 1) {
              moves.add(new Obj(i, j, i, j - 1, i - 1, j - 1));
              moves.add(new Obj(i, j, i, j - 1, i - 1, j));
              moves.add(new Obj(i, j, i - 1, j, i - 1, j - 1));
            } else if (i == data.length - 1) {
              data[i][j + 1] = flip(i, j + 1);
              moves.add(new Obj(i, j, i - 1, j, i - 1, j + 1));
              moves.add(new Obj(i, j + 1, i - 1, j, i - 1, j + 1));
            } else if (j == data[i].length - 1) {
              data[i + 1][j] = flip(i + 1, j);
              data[i + 1][j - 1] = flip(i + 1, j - 1);
              moves.add(new Obj(i, j, i + 1, j, i + 1, j - 1));
            }
          }
        }
      }

      print.printLine(Integer.toString(moves.size()));
      for (Obj obj : moves) {
        print.printLine(
            String.format("%d %d %d %d %d %d", obj.x1, obj.y1, obj.x2, obj.y2, obj.x3, obj.y3));
      }
    });

    print.close();

  }

  private static int flip(int i, int j) {
    return (data[i][j] + 1) % 2;
  }

  static class Obj {

    int x1;
    int y1;
    int x2;
    int y2;
    int x3;
    int y3;

    public Obj(int x1, int y1, int x2, int y2, int x3, int y3) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.x3 = x3;
      this.y3 = y3;
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