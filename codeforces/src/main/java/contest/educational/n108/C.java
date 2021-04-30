package contest.educational.n108;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class C {

  private static int n;
  private static long[] u;
  private static long[] sk;
  private static HashMap<Long, List<Long>> uniMap;

  private static long getSum(List<Long> li, int r) {
    if (r <= 0) {
      return 0;
    }
    return li.get(r - 1);
  }

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {

      n = scan.scanInt();
      u = new long[n];
      sk = new long[n];
      uniMap = new HashMap<>();

      String[] arr = scan.scanString().split(" ");
      for (int i = 0; i < arr.length; i++) {
        u[i] = Long.parseLong(arr[i]);
      }

      arr = scan.scanString().split(" ");
      for (int i = 0; i < arr.length; i++) {
        sk[i] = Long.parseLong(arr[i]);

        List<Long> li = uniMap.getOrDefault(u[i], new ArrayList<>());
        li.add(sk[i]);
        uniMap.put(u[i], li);
      }

      List<List<Long>> values = new ArrayList<>(uniMap.values());

      long[] ans = new long[n];
      for (int i = 0; i < values.size(); i++) {
        List<Long> li = values.get(i);
        if (li.size() > 0) {
          li.sort(Comparator.comparingLong(a -> a));
          for (int j = 1; j < li.size(); j++) {
            li.set(j, li.get(j) + li.get(j - 1));
          }
          for (int k = 0; k < li.size(); k++) {
            int mod = k + 1;
            ans[k] += li.get(li.size() - 1);
            int leftOver = li.size() % mod;
            ans[k] -= getSum(li, leftOver);
          }
        }
      }

      StringJoiner sj = new StringJoiner(" ");
      for (long x : ans) {
        sj.add(Long.toString(x));
      }
      print.printLine(sj.toString());

    });
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