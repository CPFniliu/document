# ajax

## introduction

   AJAX(Asynchronous JavaScript and XML)不是新的编程语言，而是一种使用现有标准的新方法。
   AJAX 最大的优点是在**不重新加载整个页面的情况下，可以与服务器交换数据并更新部分网页内容**。

## using

### example

   ```html
   <!DOCTYPE html>
   <html>
   <head>
   <meta charset="utf-8">
   <script>
   function loadXMLDoc()
   {
      // 创建对象
      var xmlhttp;
      if (window.XMLHttpRequest) {
         //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
         xmlhttp = new XMLHttpRequest();
      } else {
         // IE6, IE5 浏览器执行代码
         xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
      }
      // 设置回调函数
      xmlhttp.onreadystatechange=function()
      {
         if (xmlhttp.readyState==4 && xmlhttp.status==200)
         {
            document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
         }
      }
      // 设置连接信息并发送请求
      xmlhttp.open("GET","/try/ajax/ajax_info.txt",true);
      xmlhttp.send();
   }
   </script>
   </head>
   <body>

   <div id="myDiv"><h2>使用 AJAX 修改该文本内容</h2></div>
   <button type="button" onclick="loadXMLDoc()">修改内容</button>

   </body>
   </html>
   ```

### XHR 创建对象

```js

XMLHttpRequest 对象
// 所有现代浏览器均支持 XMLHttpRequest 对象（IE5 和 IE6 使用 ActiveXObject）。XMLHttpRequest 用于在后台与服务器交换数据。这意味着可以在不重新加载整个网页的情况下，对网页的某部分进行更新。老版本的 Internet Explorer （IE5 和 IE6）使用 ActiveX 对象：
var xmlhttp;
if (window.XMLHttpRequest) {
    //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
    xmlhttp = new XMLHttpRequest();
} else {
    // IE6, IE5 浏览器执行代码
    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}
```

### XHR 请求

```js
// 如需将请求发送到服务器，我们使用 XMLHttpRequest 对象的 open() 和 send() 方法：
xmlhttp.open("GET", "/try/ajax/demo_get.txt", true);
// 将请求发送到服务器。
xmlhttp.send();
```

open(method,url,async); 规定请求的类型、URL 以及是否异步处理请求。

   open 方法 | means
   -|-
   method | 请求的类型；GET 或 POST
   url | 文件在服务器上的位置
   async | true（异步）或 false（同步）

send(string); 将请求发送到服务器。
   string：仅用于 POST 请求

GET 还是 POST
   > 与 POST 相比，GET 更简单也更快，并且在大部分情况下都能用。
   >
   > 然而，在以下情况中，请使用 POST 请求：
   >
   > - 无法使用缓存文件（更新服务器上的文件或数据库）
   > - 向服务器发送大量数据（POST 没有数据量限制）
   > - 发送包含未知字符的用户输入时，POST 比 GET 更稳定也更可靠

在上面的例子中，您可能得到的是缓存的结果。为了避免这种情况，请向 URL 添加一个唯一的 ID：

   ```js
   xmlhttp.open("GET","/try/ajax/demo_get.php?t=" + Math.random(),true);
   xmlhttp.send();
   ```

如果需要像 HTML 表单那样 POST 数据，请使用 setRequestHeader() 来添加 HTTP 头。然后在 send() 方法中规定您希望发送的数据：

   ```js
   xmlhttp.open("POST", "/try/ajax/demo_post2.php", true);
   // 向请求添加 HTTP 头。header: 规定头的名称, value: 规定头的值
   xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   xmlhttp.send("fname=Henry&lname=Ford");
   ```

同步请求和异步请求
   XMLHttpRequest 对象如果要用于 AJAX 的话，其 open() 方法的 async 参数必须设置为 true：当使用 async=true 时，请规定在响应处于 onreadystatechange 事件中的就绪状态时执行的函数：
   我们不推荐使用 async=false，但是对于一些小型的请求，也是可以的。JavaScript 会等到服务器响应就绪才继续执行。如果服务器繁忙或缓慢，应用程序会挂起或停止。
   注意：当您使用 async=false 时，请不要编写 onreadystatechange 函数 - 把代码放到 send() 语句后面即可：

### XHR 响应

如需获得来自服务器的响应，请使用 XMLHttpRequest 对象的 responseText 或 responseXML 属性。

属性 | 描述
-|-
responseText | 获得字符串形式的响应数据。如果来自服务器的响应并非 XML，请使用 responseText 属性。
responseXML | 获得 XML 形式的响应数据。

responseText 属性
   如果来自服务器的响应并非 XML，请使用 responseText 属性。
   responseText 属性返回字符串形式的响应，因此您可以这样使用：
   `document.getElementById("myDiv").innerHTML=xmlhttp.responseText;`

responseXML 属性
   如果来自服务器的响应是 XML，而且需要作为 XML 对象进行解析，请使用 responseXML 属性：

   ```js
   xmlDoc=xmlhttp.responseXML;
   txt="";
   x=xmlDoc.getElementsByTagName("ARTIST");
   for (i=0;i<x.length;i++)
   {
      txt=txt + x[i].childNodes[0].nodeValue + "<br>";
   }
   document.getElementById("myDiv").innerHTML=txt;
   ```

### onreadystatechange 事件

当请求被发送到服务器时，我们需要执行一些基于响应的任务。
每当 readyState 改变时，就会触发 onreadystatechange 事件。
readyState 属性存有 XMLHttpRequest 的状态信息。

   ```js
   xmlhttp.onreadystatechange=function()
   {
      if (xmlhttp.readyState==4 && xmlhttp.status==200)
      {
         document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
      }
   }
   ```

下面是 XMLHttpRequest 对象的三个重要的属性：

   属性 | 描述
   -|-
   onreadystatechange | 存储函数（或函数名），每当 readyState 属性改变时，就会调用该函数。
   readyState | 存有 XMLHttpRequest 的状态.
   status | 200: "OK", 404: 未找到页面

onreadystatechange 事件被触发 4 次（0 - 4）, 分别是： 0-1、1-2、2-3、3-4，对应着 readyState 的每个变化。

   readyState | means
   -|-
   0 | 请求未初始化（还没有调用 open()）。
   1 | 服务器连接已经建立，但是还没有发送（还没有调用 send()）。
   2 | 请求已发送，正在处理中（通常现在可以从响应中获取内容头）。
   3 | 请求在处理中；通常响应中已有部分数据可用了，但是服务器还没有完成响应的生成。
   4 | 响应已完成；您可以获取并使用服务器的响应了。

> 用 window.alert 跟踪 xmlHttp.readystate 的变化，发现于原来它运行的机制是这样的。
> 首先创建一个 xmlHttpRequest 的对象之后 xmlHttp.readyState 的值是 0 了，
> 然后xmlHttp.onreadystatechange = handlestatechange 没有运行。
> 紧接着是 open()，这个函数发生了之后 xmlHttp.readyState 的值是 1 了，那么就会有一个断点在 Open() 函数处断开，保留现场，
> 紧接着又返回到 xmlHttp.onreadystatechange = handlestatechange 运行，然后再执行 Send() 函数，这个函数发生了之后 xmlHttp.readyState 的值是 2 了，
> 接着又返回到 xmlHttp.onreadystatechange = handlestatechange 运行，以此类推。
