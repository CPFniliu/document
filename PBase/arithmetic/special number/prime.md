# prime

## 简单方式

### 判断一个数是否是质数(1层优化)

1. 判断一个数是否是质数
   - 仅判断除2以外的偶数
   - 判断的

   ```java
   /**
   * 判断一个数是否是质数(简单优化)
   *
   * @param n 需要判断的数
   * @return
   */
   public static boolean isPrime1(int n) {
      if (n < 2) {
         return false;
      }
      if (n == 2) {
         return true;
      }
      int sqrt = (int) Math.sqrt(n);
      for (int i = 3; i <= sqrt; i+= 2) {
         if (n % i == 0) {
               return false;
         }
      }
      return true;
   }
   ```

### 判断一个数是否是质数(二层优化)

```java
public static boolean isprime(int n) {
   if (n <= 1) {
      return false;
   }
   if (n <= 3) { // 2, 3
      return true;
   }
   int remainder = n % 6;
   if (remainder != 1 && remainder != 5) {
      return false;
   }
   if (n % 3 == 0) {
      return false;
   }
   for (int i = 5, j = 7, sqrt = (int) Math.sqrt(n); i <= sqrt || j <= sqrt; i += 6, j += 6) {
      if (n % i == 0 || n % j == 0) {
            return false;
      }
   }
   return true;
}
```

### 判断一个数是否是质数(二层优化扩展)

```java
public static boolean isprime(int n) {
   if (n <= 1) {
      return false;
   }
   if (n <= 3) { // 2, 3
      return true;
   }
   int remainder = n % 6;
   if (remainder != 1 && remainder != 5) {
      return false;
   }
   if (n % 3 == 0) {
      return false;
   }
   for (int i = 5, j = 7, sqrt = (int) Math.sqrt(n); i <= sqrt || j <= sqrt; i += 6, j += 6) {
      if (n % i == 0 || n % j == 0) {
            return false;
      }
   }
   return true;
}
```

### 判断一个数是否是质数(三层优化)

通过质数表

### 筛子方法, 输出 100000000 以内的所有质数

   ```java
   /**
   * 输出一定范围内的质数
   */
   @Test
   public void main() {
      int number = 10000;
      boolean[] arr = new boolean[number];
      for (int i = 2; i < number; i++) {
         if (arr[i]) {
               continue;
         }
         for (int j = i << 1; j < number; j = j + i) {
               arr[j] = true;
         }
      }
      for (int i = 2; i < number; i++) {
         if (!arr[i]) {
               System.out.print(i + " ");
         }
      }
   }
   ```

### 一个8核心CPU, 要求以最快速度输出 100000000 以内的所有质数

```java
import java.util.concurrent.atomic.AtomicInteger;
public class PrimeNumber
{
    // 给定8个线程, 要求最快输出质数
    public static void main(String[] args){
        Runnable runnable = new primeRunnable();
        for (int i = 0; i < 8; i++) {
            new Thread(runnable).start();
        }
    }


   private static class primeRunnable implements Runnable {
      // 求得质数范围最大值
      private static final int len = 100000;
      // 筛子数组, 初始化全为false, 若不为质数则置为true, 最后输出为false的下标位置
      private static final boolean[] arr = new boolean[len];
      // 用于操作筛子数组
      private static AtomicInteger curDispose = new AtomicInteger(2);
      // 用于输出
      private static AtomicInteger curPrint = new AtomicInteger(2);

      @Override
      public void run() {
         // 操作数组
         for (int i = curDispose.getAndIncrement(); i < len; i = curDispose.getAndIncrement()) {
               if (arr[i]) {
                  continue;
               }
               for (int j = i << 1; j < len; j += i) {
                  arr[j] = true;
               }
         }
         // 打印数组
         for (int i = curPrint.getAndIncrement(); i < len; i = curPrint.getAndIncrement()) {
               if (!arr[i]) {
                  System.out.print(i + " ");
               }
         }
      }
   }
}
```