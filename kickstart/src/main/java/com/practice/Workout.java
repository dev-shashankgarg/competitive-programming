package com.practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Workout {

  private static int k;
  private static int[] data;
  private static Integer[][] cache;


  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
      int tests = scanner.nextInt();
      IntStream.rangeClosed(1, tests).forEach(test -> {
        int points = scanner.nextInt();
        k = scanner.nextInt();
        data = new int[points - 1];
        cache = new Integer[data.length][k + 1];

        int a = scanner.nextInt();
        for (int i = 0; i < data.length; i++) {
          int b = scanner.nextInt();
          data[i] = b - a;
          a = b;
        }

        System.out.println(String.format("Case #%d: %d", test, solve(0, 0)));

      });


    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private static int solve(int current, int slotsAdded) {

    if (current == data.length) {
      return Integer.MIN_VALUE;
    }

    if (cache[current][slotsAdded] != null) {
      return cache[current][slotsAdded];
    }

    int slot = 1;
    int min = Math.max(data[current], solve(current + 1, slotsAdded));

    while (slot + slotsAdded <= k && data[current] > slot) {
      int reduce = (int) Math.ceil((double) (data[current]) / (slot + 1));
      min = Math.min(
          min, Math.max(
              reduce, solve(current + 1, slotsAdded + slot)));
      slot++;
    }

    return cache[current][slotsAdded] = min;
  }

}
