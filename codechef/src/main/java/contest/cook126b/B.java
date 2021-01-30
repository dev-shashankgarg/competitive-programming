package contest.cook126b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.StringJoiner;
import java.util.stream.IntStream;

//Does not give correct solution! TBC
class B {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      int n = scan.scanInt();
      int m = scan.scanInt();

      String[] arr = new String[n];
      for (int i = 0; i < n; i++) {
        arr[i] = scan.scanString();
      }

      if (n == 1 || m == 1) {

        String a = (n == 1) ? arr[0] : "";
        if (m == 1) {
          StringJoiner sj = new StringJoiner("");
          for (String s : arr) {
            sj.add(s);
          }
          a = sj.toString();
        }

        print.printLine(Integer.toString(solve(a)));

      } else {
        int ec = 0;
        int oc = 0;

        for (int i = 0; i < n; i++) {
          for (int j = 0; j < m; j++) {

            char c = arr[i].charAt(j);

            if (c == '.') {
              ec += ((i + j) % 2 == 0) ? 1 : 0;
              oc += ((i + j) % 2 == 1) ? 1 : 0;
            }

            if (c == '*') {
              ec += ((i + j) % 2 == 1) ? 1 : 0;
              oc += ((i + j) % 2 == 0) ? 1 : 0;
            }
          }
        }

        print.printLine(Integer.toString(Math.min(ec, oc)));
      }
    });

    print.close();

  }

  private static int solve(String a) {
    int ans = a.length();

    if (a.length() == 1) {
      if (a.charAt(0) == '*') {
        return 0;
      }
      return 1;
    }

    int ops = 0;
    for (int i = 0; i < a.length(); i++) {
      if (i % 2 == 0) {
        if (a.charAt(i) == '.') {
          ops++;
        }
      } else {
        if (a.charAt(i) == '*') {
          ops++;
        }
      }
    }
    ans = Math.min(ops, ans);
    ops = 0;
    for (int i = 0; i < a.length(); i++) {
      if (i % 2 == 0) {
        if (a.charAt(i) == '*') {
          ops++;
        }
      } else {
        if (a.charAt(i) == '.') {
          ops++;
        }
      }
    }

    ans = Math.min(ops, ans);

    if (a.length() % 2 == 0 && a.length() > 2) {
      int st = 1;
      while (st + 1 < a.length()) {
        ops = 0;
        for (int i = 0; i <= st + 1; i++) {
          if (i % 2 == 0) {
            if (a.charAt(i) == '.' && i != st + 1) {
              ops++;
            }
          } else {
            if (a.charAt(i) == '*') {
              ops++;
            }
          }
        }
        for (int i = st + 2; i < a.length(); i++) {
          if (i % 2 == 0) {
            if (a.charAt(i) == '*') {
              ops++;
            }
          } else {
            if (a.charAt(i) == '.') {
              ops++;
            }
          }
        }
        ans = Math.min(ops, ans);
        st += 2;
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
