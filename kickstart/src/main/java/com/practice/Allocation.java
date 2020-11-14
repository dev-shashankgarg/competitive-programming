package com.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Allocation {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      int tests = scanner.nextInt();
      for (int t = 1; t <= tests; t++) {
        int houses = scanner.nextInt();
        int purse = scanner.nextInt();
        List<Integer> li = new ArrayList<>();

        while (houses-- > 0) {
          li.add(scanner.nextInt());
        }

        Collections.sort(li);

        int i = 0;
        int count = 0;
        while (i < li.size() && purse - li.get(i) >= 0) {
          purse -= li.get(i);
          count++;
          i++;
        }

        System.out.println(String.format("Case #%d: %d", t, count));

      }

    } catch (Exception e) {
    }
  }

}
