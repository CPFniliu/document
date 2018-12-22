#### propotypes 和 constructor 的区别



1. 对象是构造函数创建出来的



2. 获取原型的三种方式

### prototype 简介
每个函数都有一个prototype属性，这个属性是指向一个对象的引用，这个对象称为原型对象，原型对象包含函数实例共享的方法和属性，
也就是说将函数用作构造函数调用（使用new操作符调用）的时候，新创建的对象会从原型对象上继承属性和方法。

"\_\_proto\_\_"是每个对象都有的一个属性，而prototype是函数才会有的属性!!!



### Function
创建一个函数

   ```js
   // 创建一个普通函数，其内部的变量无法从外部进行访问
   function fn1() {
       // 创建的内部成员方法，可以通过其实例对象访问，不可通过函数名访问
       this.innerParam = "a inner param";
       this.innerFun = function () {
           console.log("called fn1.innerFun()");
       };
       var a  = 90;
       var fni = function () {
           console.log("this is a inner function in fn1");
       }
   }
   // 使用函数加 . 的形式为函数对象赋静态值，可以通过其函数名来调用，但无法通过其实例调用
   fn1.par1 = "a static param1";
   fn1.func1 = function () {
       console.log("this is a statis function in fn1");
   }

   // 创建一个实例化对象
   var fun1 = new fn1();

   fn1.func1();    // this is a statis function in fn1
   console.log(fn1.par1); // a static param1

   console.log(fun1.par1);  // undefined
   console.log(fun1.func1); // undefined


   console.log(fn1.innerParam); // undefined
   // fn1.innerFun(); // error

   console.log(fun1.innerParam); // a inner param
   fun1.innerFun(); // called fn1.innerFun()
   ```


   很多人见到了久违的new操作符，于是就叫Person为“类”，可是又没有关键字class的出现，觉得叫“类”有点勉强。于是退而求其次叫Person为类的构造函数。这些概念好像都没有错，之所以出现这样的情况，可能是因为大家都学习了传统的面向对象语言（c++，c#，java等），还有一种思维定势吧。为了让javascript也面向对象，要在javascript中找到与传统面向对象语言的影子。可是按照javascript的说法，function定义的这个Person就是一个Object(对象),而且还是一个很特殊的对象，这个使用function定义的对象与使用new操作符生成的对象之间有一个重要的区别。这个区别就是function定义的对象有一个prototype属性，使用new生成的对象就没有这个prototype属性。

   prototype属性又指向了一个prototype对象，注意prototype属性与prototype对象是两个不同的东西，要注意区别。在prototype对象中又有一个constructor属性，这个constructor属性同样指向一个constructor对象，而这个constructor对象恰恰就是这个function函数本身。

#### Objcet.creat() 方法
```js
   var a = {};
   var b = Object.create(a);
   console.log(b.__proto__ === a); // true
   console.log(b.constructor === a.constructor); // true

   var a = function() {}
   var b = Object.create(a);
   console.log(b.__proto__ === a);  // true
   console.log(b.constructor === a.constructor);  // true
   // 若是Object以一个函数创建的对象，因为这个对象的"__proto__"指向函数对象，因此这个对象会拥有这个函数的prototype属性
   console.log(b.prototype === a.prototype);  // true
```
