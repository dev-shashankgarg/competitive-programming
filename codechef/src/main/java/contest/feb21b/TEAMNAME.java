package contest.feb21b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class TEAMNAME {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {
      int n = scan.scanInt();
      String[] arr = new String[n];
      for (int i = 0; i < n; i++) {
        arr[i] = scan.scanString();
      }

      long ans = solve(arr);
      print.printLine(Long.toString(ans));

    });

    print.close();

  }

  private static long solve(String[] arr) {
    Map<String, Set<Character>> sufMap = new HashMap<>();

    Map<Character, List<String>> chMap = new HashMap<>();

    HashSet<Character> chSet = new HashSet<>();

    for (String s : arr) {
      String ss = s.substring(1);
      char c = s.charAt(0);
      chSet.add(c);

      Set<Character> set = sufMap.getOrDefault(ss, new HashSet<>());
      set.add(c);
      sufMap.put(ss, set);

      List<String> li = chMap.getOrDefault(c, new ArrayList<>());
      li.add(s);
      chMap.put(c, li);

    }

    HashMap<Character, Map<Character, List<String>>> mainMap = new HashMap<>();
    for (char ch : chSet) {
      HashMap<Character, List<String>> map = new HashMap<>();

      for (String word : chMap.get(ch)) {
        String ss = word.substring(1);
        for (int i = 0; i < 26; i++) {
          char cc = (char) (i + 'a');
          if (!sufMap.get(ss).contains(cc)) {
            List<String> li = map.getOrDefault(cc, new ArrayList<>());
            li.add(word);
            map.put(cc, li);
          }
        }
      }

      mainMap.put(ch, map);
    }

    long ans = 0;

    for (String word : arr) {
      char a = word.charAt(0);
      String s1 = word.substring(1);
      for (char c : chSet) {
        if (!sufMap.get(s1).contains(c)) {
          ans += mainMap.get(c).getOrDefault(a, new ArrayList<>()).size();
        }
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
