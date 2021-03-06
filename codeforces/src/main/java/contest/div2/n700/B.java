package contest.div2.n700;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.stream.IntStream;


public class B {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();
    IntStream.range(0, tests).forEach(test -> {

      Mammal hero = new Mammal();
      hero.power = scan.scanInt();
      hero.health = scan.scanInt();

      int n = scan.scanInt();
      Mammal[] monsters = new Mammal[n];
      for (int i = 0; i < n; i++) {
        monsters[i] = new Mammal(scan.scanInt());
      }

      for (int i = 0; i < n; i++) {
        monsters[i].health = scan.scanInt();
        long fights = Math.max(1, (long) Math.ceil((double) monsters[i].health / hero.power));
        monsters[i].minEnergy = (fights - 1) * monsters[i].power;
      }

      boolean ans = solve(hero, monsters);
      print.printLine(ans ? "YES" : "NO");
    });

    print.close();

  }

  private static boolean solve(Mammal hero, Mammal[] monsters) {
    long health = hero.health;
    Arrays.sort(monsters, Comparator.comparingLong(n -> n.power)
    );

    long healthRequired = 0;
    for (Mammal m : monsters) {
      healthRequired += m.minEnergy;
    }

    if (healthRequired >= health) {
      return false;
    }

    health -= healthRequired;

    for (int i = 0; i < monsters.length; i++) {
      if (i == monsters.length - 1) {
        return health > 0;
      } else {
        health -= monsters[i].power;
        if (health <= 0) {
          return false;
        }
      }
    }

    return false;
  }

  static class Mammal {

    private int health;
    private int power;
    private long minEnergy;

    public Mammal(int power) {
      this.power = power;
    }

    public Mammal() {
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