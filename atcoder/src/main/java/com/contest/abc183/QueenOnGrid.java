package com.contest.abc183;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueenOnGrid {

  private static Integer[][] cache;
  private static int length;
  private static int width;

  public static void main(String[] args) {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String[] arr = reader.readLine().split(" ");
      length = Integer.parseInt(arr[0]);
      width = Integer.parseInt(arr[1]);
      cache = new Integer[length][width];

      for (int i = 0; i < length; i++) {
        String s = reader.readLine();
        for (int j = 0; j < width; j++) {
          char c = s.charAt(j);
          if (c == '#') {
            cache[i][j] = 0;
          }
        }
      }

      int answer = solve(0, 0);
      System.out.println(answer);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static int solve(int i, int j) {
    if (i + 1 == length && j + 1 == width) {
      return 1;
    }

    if (cache[i][j] != null) {
      return cache[i][j];
    }

    int sum = 0;
    int MOD = 1000000007;
    for (int nj = j + 1; nj < width; nj++) {

      int next = solve(i, nj);
      sum = (sum + next) % MOD;

      if (next == 0) {
        break;
      }
    }

    for (int ni = i + 1; ni < length; ni++) {
      int next = solve(ni, j);
      sum = (sum + next) % MOD;

      if (next == 0) {
        break;
      }
    }

    for (int ni = i + 1, nj = j + 1; ni < length && nj < width; ni++, nj++) {
      int next = solve(ni, nj);
      sum = (sum + next) % MOD;

      if (next == 0) {
        break;
      }
    }

    return cache[i][j] = sum % MOD;
  }

}