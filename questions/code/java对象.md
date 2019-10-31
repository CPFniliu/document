# 

## 编程知识

1. 下面输出结果是什么

   ```java
   public void test() {
      String s1 = new StringBuilder("12").append("ab").toString();
      System.out.println(s1.intern() == s1);
      String s2 = new StringBuilder().append("1234").toString();
      System.out.println(s2.intern() == s2);
      String s3 = new StringBuilder("ja").append("va").toString();
      System.out.println(s3 == s3.intern());
   }
   ```





## answer

1. true, false, false

   > tip
   > 1. StringBuilder和SpringBuffer的toString()方法最终都是return一个new String()对象
   > 2. String的intern()方法是查找常量池里面是否有同样的字符串, 若有则返回常量池里面的字符串, 若没有则返回其本身.
   > 3. 使用双引号创建的字符串存在常量池里面, 使用new String()创建的字符串存在堆里面.

   s1指向的是堆里的String对象, 同时常量池里面没有和s1相同的值, 故返回的是s1本身
   s2里面有个`"1234"`, 直接定义出来了, 其intern()方法指向的是常量池里面的字符串对象



