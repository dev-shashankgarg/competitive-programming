package com.kickstart.y2013.pr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;


/**
 * Problem Moist has a hobby -- collecting figure skating trading cards. His card collection has
 * been growing, and it is now too large to keep in one disorganized pile. Moist needs to sort the
 * cards in alphabetical order, so that he can find the cards that he wants on short notice whenever
 * it is necessary.
 * <p>
 * The problem is -- Moist can't actually pick up the cards because they keep sliding out his hands,
 * and the sweat causes permanent damage. Some of the cards are rather expensive, mind you. To
 * facilitate the sorting, Moist has convinced Dr. Horrible to build him a sorting robot. However,
 * in his rather horrible style, Dr. Horrible has decided to make the sorting robot charge Moist a
 * fee of $1 whenever it has to move a trading card during the sorting process.
 * <p>
 * Moist has figured out that the robot's sorting mechanism is very primitive. It scans the deck of
 * cards from top to bottom. Whenever it finds a card that is lexicographically smaller than the
 * previous card, it moves that card to its correct place in the stack above. This operation costs
 * $1, and the robot resumes scanning down towards the bottom of the deck, moving cards one by one
 * until the entire deck is sorted in lexicographical order from top to bottom.
 * <p>
 * As wet luck would have it, Moist is almost broke, but keeping his trading cards in order is the
 * only remaining joy in his miserable life. He needs to know how much it would cost him to use the
 * robot to sort his deck of cards.
 * <p>
 * Input The first line of the input gives the number of test cases, T. T test cases follow. Each
 * one starts with a line containing a single integer, N. The next N lines each contain the name of
 * a figure skater, in order from the top of the deck to the bottom.
 * <p>
 * Output For each test case, output one line containing "Case #x: y", where x is the case number
 * (starting from 1) and y is the number of dollars it would cost Moist to use the robot to sort his
 * deck of trading cards.
 * <p>
 * Limits Time limit: 60 seconds per test set. Memory limit: 1 GB. 1 ≤ T ≤ 100. Each name will
 * consist of only letters and the space character. Each name will contain at most 100 characters.
 * No name with start or end with a space. No name will appear more than once in the same test case.
 * Lexicographically, the space character comes first, then come the upper case letters, then the
 * lower case letters.
 * <p>
 * Small dataset 1 ≤ N ≤ 10.
 * <p>
 * Large dataset 1 ≤ N ≤ 100.
 * <p>
 * Sample
 * <p>
 * Input
 * <p>
 * Output
 * <p>
 * 2 2 Oksana Baiul Michelle Kwan 3 Elvis Stojko Evgeni Plushenko Kristi Yamaguchi Case #1: 1 Case
 * #2: 0
 */
public class Moist {

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    System.out.println("abc".compareTo("abcd"));

    Scanner sc = new Scanner(System.in);

    int tests = Integer.parseInt(sc.nextLine());
    IntStream.rangeClosed(1, tests).forEach(test -> {

      int n = Integer.parseInt(sc.nextLine());
      String[] arr = new String[n];
      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextLine();
      }

      int ans = solve(arr);

      print.printLine(String.format("Case #%d: %d", test, ans));
    });

    print.close();
  }

  private static int solve(String[] arr) {
    int ans = 0;
    int k = 0;
    for (int i = 1; i < arr.length; i++) {
      if (isGreater(arr[i], arr[k])) {
        k = i;
      } else {
        ans++;
      }
    }

    return ans;
  }

  private static boolean isGreater(String a, String b) {

    return a.compareTo(b) > 0;

//    for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
//      char a_c = a.charAt(i);
//      char b_c = b.charAt(i);
//
//      if (a_c == b_c) {
//        continue;
//      }
//
//      if (isLowerCase(a_c) && isLowerCase(b_c)) {
//        return a_c > b_c;
//      }
//
//      if (isUpperCase(a_c) && isUpperCase(b_c)) {
//        return a_c > b_c;
//      }
//
//      if (a_c == ' ' || b_c == ' ') {
//        return a_c == ' ';
//      }
//
//      if (isUpperCase(a_c)) {
//        a_c = (char) (a_c + 32);
//        return a_c > b_c;
//      }
//
//      if (isLowerCase(a_c)) {
//        a_c = (char) (a_c - 32);
//        return a_c > b_c;
//      }
//    }
//
//    return a.length() > b.length();
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