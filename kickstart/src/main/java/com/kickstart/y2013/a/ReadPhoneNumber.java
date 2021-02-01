package com.kickstart.y2013.a;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReadPhoneNumber {

  private static Map<Character, String> numMap;
  private static Map<Integer, String> countMap;

  public static void main(String[] args) {
    Print print = new Print();
    Scan scan = new Scan();

    int tests = scan.scanInt();

    numMap = new HashMap<>();
    numMap.put('0', "zero");
    numMap.put('1', "one");
    numMap.put('2', "two");
    numMap.put('3', "three");
    numMap.put('4', "four");
    numMap.put('5', "five");
    numMap.put('6', "six");
    numMap.put('7', "seven");
    numMap.put('8', "eight");
    numMap.put('9', "nine");

    countMap = new HashMap<>();
    countMap.put(2, "double");
    countMap.put(3, "triple");
    countMap.put(4, "quadruple");
    countMap.put(5, "quintuple");
    countMap.put(6, "sextuple");
    countMap.put(7, "septuple");
    countMap.put(8, "octuple");
    countMap.put(9, "nonuple");
    countMap.put(10, "decuple");

    IntStream.rangeClosed(1, tests).forEach(test -> {

      String number = scan.scanString();
      String format = scan.scanString();

      print.printLine(String.format("Case #%d: %s", test, solve(number, format)));

    });

    print.close();
  }

  private static String solve(String number, String format) {
    List<Integer> groups = Arrays.stream(format.split("-")).map(Integer::parseInt)
        .collect(Collectors.toList());

    int k = 0;
    char[] arr = number.toCharArray();
    StringJoiner sj = new StringJoiner(" ");
    for (int len : groups) {
      sj.add(make(arr, k, k + len - 1));
      k += len;
    }

    return sj.toString();
  }

  private static String make(char[] arr, int start, int end) {
    Stack<Node> st = new Stack<>();
    StringJoiner sj = new StringJoiner(" ");
    for (int i = start; i <= end; i++) {
      if (st.isEmpty()) {
        st.push(new Node(arr[i], 1));
      } else if (st.peek().num == arr[i]) {
        st.peek().count += 1;
      } else {
        Node node = st.pop();
        sj.add(generate(node));
        i--;
      }
    }

    sj.add(generate(st.pop()));
    return sj.toString();
  }

  private static String generate(Node node) {
    if (countMap.containsKey(node.count)) {
      return String.format("%s %s", countMap.get(node.count), numMap.get(node.num));
    } else {
      StringJoiner sj = new StringJoiner(" ");
      for (int i = 0; i < node.count; i++) {
        sj.add(numMap.get(node.num));
      }
      return sj.toString();
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

  static class Node {

    char num;
    int count;

    public Node(char num, int count) {
      this.num = num;
      this.count = count;
    }
  }

}
