package numbertheory;

public class RabinMillerPrimality {

  static long pow(long n, long k, long m) {
    if (k == 0) {
      return 1;
    }

    long x = pow(n, k / 2, m);
    if ((k & 1) == 1) {
      return ((n % m * x % m) * x % m) % m;
    }
    return (x % m * x % m) % m;
  }

  private static boolean singleTest(long n) {
    if (n == 1) {
      return false;
    }
    if (n == 2) {
      return true;
    }

    long exp = n - 1;
    while ((exp & 1) == 0) {
      exp = exp >> 1;
    }

    long a = 2 + (long) (Math.random() * (n - 3));

    if (pow(a, exp, n) == 1) {
      return true;
    }

    while (exp < n - 1) {
      if ((n + pow(a, exp, n)) % n == n - 1) {
        return true;
      }
      exp = exp << 1;
    }

    return false;
  }

  static boolean isPrime(long n, long trials) {
    for (int i = 0; i < trials; i++) {
      if (!singleTest(n)) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.out.println(isPrime(5, 10));
    System.out.println(43 * 41);
  }

}
