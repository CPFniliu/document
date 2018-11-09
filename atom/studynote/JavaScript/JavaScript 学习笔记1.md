JavaScript内置对象
   1.
   for in遍历的是数组的索引（即键名），而for of遍历的是数组元素值。

   for of遍历的只是数组内的元素，而不包括数组的原型属性method和索引name


1. js 对象（object & function）
   js中的对象分为两种：普通对象object和函数对象function。
   对象：一堆变量 + 一堆语句的集合
   js ：万物皆对象


   - 新建普通对象object
   ```js
   var obj1 = new fn1();
   var obj2 = {};
   var obj3 = new Object();
   var obj4 = Object();

   console.log(typeof obj1);//object
   console.log(typeof obj2);//object
   console.log(typeof obj3);//object
   console.log(typeof obj4);//object
   ```
   - 新建函数对象 function
   ```js
   function fn1(){};
   var fn2 = function(){};
   var fn3 = new Function();
   var fn4 = Function();

   console.log(fn1); // [Function: fn1]
   console.log(fn2); // [Function: fn2]
   console.log(fn3); // [Function: anonymous]
   console.log(fn4); // [Function: anonymous]

   console.log(typeof fn1);//function
   console.log(typeof fn2);//function
   console.log(typeof fn3);//function
   console.log(typeof fn4);//function
   ```
2. 对象的创建
```js
var obj = {
   name : "anme11",
   "peroper" : "perie",
   'x' : 0,
   "x" : 1,
   x : 2,  // 多个 x 实际上都是相同的属性名，都会转化为 "x" 字符串，同一个属性名，新的属性值回取代旧的属性值。
   "a space" : "this is a value contains space", // 属性名包含特殊字符（eg : 空格）的属性名需要加上引号
   1 : "this a number" // 数字类型的属性名最终都将被转换为字符型  '1'
}

obj.name  // 只有在对象中建立的时候属性名没有引号才能够用点 . 来调用,
obj["peroper"] // 属性名加引号或者是数字的必须使用[]来调用,
obj[1] //

```






> 对象是由属性和值组成， 左边属性名必然会转换为 string

#### 闭包
```js
var add = (function() {
	var counter = 0;
    return function() {counter += 1; return counter;}
})()

function exec(){
	console.log(add());
}
```

#### delete 删除属性
`delete obj.name`

```js
var person = {
   name : "小明",
   age : 34,
   getGender : function(){
      console.log('fjjdjjfjd');
   }
}

var age = person.getGender;
delete person.getGender; // 只会删除掉person对象和 getGender 之间的关系，并不会直接销毁 getGender()
age();
```
