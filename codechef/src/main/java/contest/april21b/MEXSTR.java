package contest.april21b;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

class MEXSTR {

  private static String[] map;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

//    long start = System.currentTimeMillis();
//    int ex = 1 << 20;
//    for (int i = 0; i <= ex; i++) {
//      String s = Integer.toBinaryString(i);
//      String expec = solve(s.replaceAll("^0*", "").toCharArray(), s.length());
//      String brut = solve2(s.toCharArray(), i * 2 + 5);
//
//      if (!expec.equals(brut)) {
//        print.printLine(
//            "Test String " + i + " || " + s + " : -> " + expec + " : -> " + brut);
//      }
//    }
//    long end = System.currentTimeMillis();
//    System.out.printf("Time Taken : %d", end - start);

    int t = scan.scanInt();
    IntStream.range(0, t).forEach(test -> {
      String n = scan.scanString();
      print.printLine(solve(n.replaceAll("^0*", "").toCharArray(), n.length()));
    });

    print.close();

  }

//  private static String non(char[] arr) {
//    StringBuilder sb = new StringBuilder();
//    int flag = 1;
//    int i = 0;
//
//    while (i < arr.length) {
//      if (arr[i] == (flag + '0')) {
//        i++;
//      } else {
//        sb.append(flag);
//        flag = (flag + 1) % 2;
//      }
//    }
//
//    sb.append(flag);
//    return sb.toString();
//  }

  private static String solve2(char[] arr, int i) {
    for (int x = 0; x <= i; x++) {
      char[] test = Integer.toBinaryString(x).toCharArray();
      boolean flag = true;
      int l = 0, r = 0;

      while (l < arr.length) {
        if (r == test.length) {
          flag = false;
          break;
        }
        if (test[r] == arr[l]) {
          l++;
          r++;
        } else {
          l++;
        }
      }

      if (flag && r != test.length) {
        return Integer.toBinaryString(x);
      }


    }
    return "";
  }

//  private static String solve(char[] n) {
//
//    if (n.length == 0) {
//      return "1";
//    }
//
//    TreeSet<Integer> zs = new TreeSet<>();
//    TreeSet<Integer> os = new TreeSet<>();
//
//    int[] z = new int[n.length];
//    int ix = -1;
//
//    for (int i = n.length - 1; i >= 0; i--) {
//
//      if (i == n.length - 1) {
//        z[i] = (n[i] == '1' ? 0 : 1);
//      } else {
//        z[i] = z[i + 1] + (n[i] == '0' ? 1 : 0);
//      }
//
//      if (n[i] == '0') {
//        zs.add(i);
//      } else {
//        os.add(i);
//        ix = i;
//      }
//    }
//
//    if (zs.size() == 0) {
//      return "0";
//    }
//    if (zs.size() == n.length) {
//      return "1";
//    }
//
//    StringBuilder sb = new StringBuilder();
//    sb.append("1");
//
//    while (true) {
//      Integer nz = zs.ceiling(ix + 1);
//      Integer no = os.ceiling(ix + 1);
//
//      if (nz == null) {
//        sb.append('0');
//        break;
//      }
//
//      if (no == null) {
//        sb.append('1');
//        break;
//      }
//
//      int b_0 = (nz == n.length - 1) ? 0 : Math.min(z[nz + 1], n.length - nz - 1 - z[nz + 1]);
//      int b_1 = (no == n.length - 1) ? 0 : Math.min(z[no + 1], n.length - no - 1 - z[no + 1]);
//
//      if (b_0 <= b_1) {
//        sb.append(0);
//        ix = nz;
//      } else {
//        sb.append(1);
//        ix = no;
//      }
//    }
//    return sb.toString();
//
//
//  }

//  private static String reverse(char[] arr) {
//    int l = 0, r = arr.length - 1;
//    while (l < r) {
//      char temp = arr[l];
//      arr[l++] = arr[r];
//      arr[r--] = temp;
//    }
//    return new String(arr);
//  }

//  private static String solve(char[] n) {
//    Stack<Integer> os = new Stack<>();
//    Stack<Integer> zs = new Stack<>();
//
//    for (int i = 0; i < n.length; i++) {
//      if (n[i] == '0') {
//        zs.push(i);
//      } else {
//        os.push(i);
//      }
//    }
//
//    // base simple cases
//    if (zs.size() == 0) {
//      return "0";
//    }
//    if (zs.size() == n.length) {
//      return "1";
//    }
//
//    StringBuilder sb = new StringBuilder();
//    int ix;
//    ix = zs.pop();
//    sb.append('0');
//    while (ix > 0) {
//
//      while (!zs.isEmpty() && zs.peek() >= ix) {
//        zs.pop();
//      }
//
//      while (!os.isEmpty() && os.peek() >= ix) {
//        os.pop();
//      }
//
//      if (zs.isEmpty()) {
//        sb.append('0');
//        break;
//      }
//
//      int o_i = os.peek();
//      int z_i = zs.peek();
//
//      if (o_i < z_i) {
//        sb.append('1');
//        ix = o_i;
//      } else {
//        sb.append('0');
//        ix = z_i;
//      }
//    }
//
//    sb.append('1');
//    return reverse(sb.toString().toCharArray());
//  }

  private static String solve(char[] n, int total) {
    if (n.length == 0) {
      return "1";
    }

    Integer[] zs_a = new Integer[n.length + 1];
    Integer[] os_a = new Integer[n.length + 1];
    int count = 0;

    int i = 0;
    int j = 0;
    while (j < zs_a.length && i < n.length) {
      if (n[i] != '1') {
        while (j <= i) {
          zs_a[j++] = i;
        }
      }
      i++;
    }

    i = 0;
    j = 0;
    while (j < os_a.length && i < n.length) {
      if (n[i] != '0') {
        count++;
        while (j <= i) {
          os_a[j++] = i;
        }
      }
      i++;
    }

    if (count == n.length) {
      if (count == total) {
        return "0";
      }
      return "10";
    }

    map = new String[n.length + 1];
    return solve(os_a, zs_a, 0, n);

  }

  private static String solve(Integer[] os, Integer[] zs, int i, char[] n) {

    if (map[i] != null) {
      return map[i];
    }

    char c = n[i];
    Integer next = (c == '1') ? zs[i] : os[i];

    int j = i;
    String s2 = "";
    while (j < n.length && n[j] == c) {
      s2 = s2.concat(Character.toString(n[j]));
      j++;
    }

    String s1 = "";
    if (c != '0' || j - i != 1) {
      if (next == null) {
        s1 = (c == '1' ? "10" : "01");
      } else {
        s1 = Character.toString(c).concat(solve(os, zs, next, n));
      }

      if (j - i == 2 && c == '1') {
        map[i] = s1;
        return s1;
      }
    }

    if (j - i < 3) {
      next = (c == '1') ? os[j] : zs[j];
      if (next == null) {
        s2 = s2.concat(c == '1' ? "1" : "0");
      } else {
        s2 = s2.concat(solve(os, zs, next, n));
      }
    } else {
      return map[i] = s1;
    }

    if (j - i == 1 && c == '0') {
      map[i] = s2;
      return s2;
    }

    if (s1.length() < s2.length()) {
      //System.out.printf("for %d , %s character ->  %s wins and %d index %n", j - i, c, "s1", i);
      return map[i] = s1;
    } else if (s2.length() < s1.length()) {
      //System.out.printf("for %d , %s character ->  %s wins and %d index %n", j - i, c, "s2", i);
      return map[i] = s2;
    } else {
      if (s1.compareTo(s2) < 0) {
        //System.out.printf("for %d , %s character ->  %s wins and %d index %n", j - i, c, "s1", i);
        return map[i] = s1;
      }
      //System.out.printf("for %d , %s character ->  %s wins and %d index %n", j - i, c, "s2", i);
      return map[i] = s2;
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
