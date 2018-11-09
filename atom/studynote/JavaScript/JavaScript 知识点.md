### 掌握知识点
-
[let 和 var 区别](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Statements/let)

[js经典面试题](https://www.cnblogs.com/weven/p/7236111.html)

(https://www.jb51.net/article/77140.htm)

- js prototype & \_\_proto\_\_




let anies = arr.map((item, index) => index + 1);
equal
let anies = arr.map((item, index) => {return index + 1});

let anies = arr.map((item, index) => {index + 1}); // error

### && and ||
逻辑或返回第一个是 true 的操作数 或者 最后一个是 false的操作数
逻辑与返回第一个是 false 的操作数 或者 最后一个是 true的操作数
```js
// 逻辑与和逻辑或运算符会返回一个值，并且二者都是短路运算符：
console.log("0 || 1 = "+(0 || 1));  // 1
console.log("1 || 2 = "+(1 || 2));  // 1
console.log("0 && 1 = "+(0 && 1));  // 0
console.log("1 && 2 = "+(1 && 2));  // 2


// 逻辑与 && 返回第一个是 false 的操作数 或者 最后一个是 true的操作数, 如果某个操作数为 false，则该操作数之后的操作数都不会被计算
console.log(1 && 2 && 0); //0
console.log(1 && 0 && 1); //0
console.log(1 && 2 && 3); //3

// 逻辑或 || 返回第一个是 true 的操作数 或者 最后一个是 false的操作数, 如果某个操作数为 true，则该操作数之后的操作数都不会被计算
console.log(1 || 2 || 0); //1
console.log(0 || 2 || 1); //2
console.log(0 || 0 || false); //false

// 如果逻辑与和逻辑或作混合运算，则逻辑与的优先级高：
console.log(1 && 2 || 0); //2
console.log(0 || 2 && 1); //1
console.log(0 && 2 || 1); //1

// 在 JavaScript，常见的 false 值：
// 0, '0', +0, -0, false, '',null,undefined,null,NaN

// 要注意空数组([])和空对象({}):
[] == false // true
{} == false // Uncaught SyntaxError: Unexpected token ==
console.log([] == false) //true
console.log({} == false) //false

// [] 和 {} 转换为boolean都为true, 所以在 if 中，[] 和 {} 都表现为 true.
console.log(Boolean([])) //true
console.log(Boolean({})) //true
```
