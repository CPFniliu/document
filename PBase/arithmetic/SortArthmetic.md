# sort

[TOC]

## 排序算法简介

### 选择排序

1. 给定一组记录, 进行第一轮比较得到最小记录, 将此纪录与第一个记录位置进行交换
2. 排除第一个记录, 接下来按照步骤1进行

```java
/**
* Select sort
* @param orderArr
* @param orderDirect ture : Sort from small to large,
*/
public static void selectSort(int[] orderArr, boolean orderDirect) {
   int mx, mxidx;
   for (int i = 0, len = orderArr.length; i < len; i++) {
      mx = orderArr[i];
      mxidx = i;
      for (int j = i + 1; j < len; j++) {
            // 若orderDirext 为 true, 由小至大进行排序
            if ((orderArr[j] < mx) == orderDirect) {
//              if (orderArr[j] < mx) {
               mx = orderArr[j];
               mxidx = j;
            }
      }
      if (mxidx != i) {
            orderArr[mxidx] = orderArr[i];
            orderArr[i] = mx;
      }
   }
}
```

### 插入排序

起初第一个数据或前几个数据为一个有序数列, 后面新增的数据查到指定的位置

```java
   // 简化版
   public static void insertSort(int[] orderArr) {
      Objects.requireNonNull(orderArr);
      int j, tmp;
      int len = orderArr.length;
      for (int i = 1; i < len; i++) {
         // 保存代插入数据
         tmp = orderArr[i];
         j = i;
         // 找到数据中大于带插入数据的位置, 否则就将数据向左移动
         while (j >= 1 && orderArr[j - 1] > tmp) {
               orderArr[j] = orderArr[j - 1];
               j--;
         }
         orderArr[j] = tmp;
      }
   }


   /**
   * Select sort
   * 优化版
   * @param orderArr
   * @param orderDirect ture : Sort from small to large,
   */
   public static void insertSort(int[] orderArr, boolean orderDirect) {
      Objects.requireNonNull(orderArr);
      for (int i = 1, len = orderArr.length, j, tmp; i < len; i++) {
         tmp = orderArr[i];
         j = i;
         if (orderDirect) {
               while (j >= 1 && orderArr[j - 1] > tmp) {
                  orderArr[j] = orderArr[j - 1];
                  j--;
               }
         } else {
               while (j >= 1 && orderArr[j - 1] < tmp) {
                  orderArr[j] = orderArr[j - 1];
                  j--;
               }
         }
         orderArr[j] = tmp;
      }
   }
```

### 冒泡排序

java

```java
   /**
   * Select sort
   * Sort from small to large
   * @param orderArr
   */
   public static void bubbleSort(int[] orderArr) {
      int len = orderArr.length;
      // iplus 值保持比i大一
      int i;
      while (--len > 0) {
         for (i = 0; i < len; i++) {
               if (orderArr[i] > orderArr[i + 1]) {
                  int tmp = orderArr[i];
                  orderArr[i] = orderArr[i + 1];
                  orderArr[i + 1] = tmp;
               }
         }
      }
   }

   /**
   * Select sort
   * @param orderArr
   * @param orderDirect ture : Sort from small to large,
   */
   public static void bubbleSort(int[] orderArr, boolean orderDirect) {
      Objects.requireNonNull(orderArr);
      int len = orderArr.length;
      while (--len > 0) {
         for (int j = 0, jPlus; j < len; j++) {
               jPlus = j + 1;
               if (orderDirect ? orderArr[j] > orderArr[jPlus] : orderArr[j] < orderArr[jPlus]) {
                  int tmp = orderArr[j];
                  orderArr[j] = orderArr[jPlus];
                  orderArr[jPlus] = tmp;
               }
         }
      }
   }
```

python

```python
# 实现一个冒泡排序
def bubboSort(arr):
    len1 = len(arr) - 1
    i = 0
    while i < len1:
        if arr[i] > arr[i + 1]:
            tmp = arr[i]
            arr[i] = arr[i + 1]
            arr[i+1] = tmp
        i = i + 1
    len1 = len1 - 1
```

### 归并排序

### 快速排序

java

```java
   private static void $quickSort(int[] orderArr, int start, int end, boolean orderDirect) {
      Objects.requireNonNull(orderArr);
      if (start >= end) {
         return;
      }
      int i = start;
      int j = end;
      int tmp = orderArr[i];
      while (i < j) {
         while (i < j && orderArr[j] >= tmp) {
               j --;
         }
         if (i < j) {
               orderArr[i++] = orderArr[j];
         }
         while (i < j && orderArr[i] < tmp) {
               i ++;
         }
         if (i < j) {
               orderArr[j--] = orderArr[i];
         }
      }
      orderArr[i] = tmp;
      $quickSort(orderArr, start, i - 1, false);
      $quickSort(orderArr, i + 1, end, false);
   }


   /**
   * 快速排序
   * @param arr
   */
   public static void quickSort(int[] arr) {
      $quickSort(arr, 0, arr.length - 1, false);
   }
```

python

```python
# 实现一个快速排序
def quickSort(arr):
   _quickSort(arr, 0, len(arr)-1)

def _quickSort(arr, st, end):
   if st >= end:
      return
   i, j , tmp = st, end, arr[st]
   while i < j:
      while i < j and arr[j] >= tmp:
         j = j - 1
      if i < j:
         arr[i] = arr[j]
         i = i + 1
      while i < j and arr[i] < tmp:
         i = i + 1
      if i < j :
         arr[j] = arr[i]
         j = j -1
   arr[i] = tmp
   _quickSort(arr, st, i - 1)
   _quickSort(arr, i + 1, end)
```

### 堆排序

### 希尔排序

## 排序算法分析
