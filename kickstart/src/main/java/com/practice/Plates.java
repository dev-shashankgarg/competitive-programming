package com.practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Plates {

  private static Map<Integer, List<Integer>> plates;
  private static int stackSize;
  private static int totPlates;
  private static int numRows;
  private static Integer[][] cache;

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
      int tests = scanner.nextInt();
      IntStream.range(0, tests).forEach(test -> {
        numRows = scanner.nextInt();
        stackSize = scanner.nextInt();
        totPlates = scanner.nextInt();

        plates = new HashMap<>();
        cache = new Integer[numRows][totPlates];

        for (int i = 0; i < numRows; i++) {
          for (int j = 0; j < stackSize; j++) {
            int plate = scanner.nextInt();

            List<Integer> li = plates.getOrDefault(i, new ArrayList<>());
            li.add(plate);
            plates.put(i, li);
          }
        }

        System.out.println(String.format("Case #%d: %d", test + 1, solve(0, 0)));

      });
    }
  }

  private static int solve(int current, int chosen) {
    if (current == numRows || chosen == totPlates) {
      return 0;
    }

    if (cache[current][chosen] != null) {
      return cache[current][chosen];
    }

    int max = solve(current + 1, chosen);
    int sum = 0;
    int selected = 1;
    for (int plate : plates.get(current)) {
      if (chosen + selected > totPlates) {
        break;
      }
      sum += plate;
      max = Math.max(max, sum + solve(current + 1, chosen + selected));
      selected++;
    }

    return cache[current][chosen] = max;
  }

}
