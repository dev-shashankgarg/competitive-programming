## Static java template for frequent modular operations


```java
static class MO {

    //MOD Operations

    static long add(long a, long b, long MOD) {
      return (a % MOD + b % MOD) % MOD;
    }

    static long multiply(long a, long b, long MOD) {
      return (a % MOD * b % MOD) % MOD;
    }

    static long subtract(long a, long b, long MOD) {
      return ((a % MOD - b % MOD) % MOD + MOD) % MOD;
    }

    static long inverse(long a, long MOD) {
      return pow(a, MOD - 2, MOD);
    }

    static long divide(long a, long b, long MOD) {
      return multiply(a, inverse(b, MOD), MOD);
    }

    static long pow(long a, long n, long MOD) {
      if (n == 0) {
        return 1;
      }
      long x = pow(a, n / 2, MOD);
      if (n % 2 == 1) {
        return multiply(multiply(x, x, MOD), a, MOD);
      } else {
        return multiply(x, x, MOD);
      }
    }

  }
```