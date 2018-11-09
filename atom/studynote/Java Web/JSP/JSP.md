>[jsp9大内置对象、4大作用域、session的生命周期](https://blog.csdn.net/CSDNgaoqingrui/article/details/48495121?utm_source=blogxgwz0)

#### jsp有四种属性范围：

属性 | 简介
-|-
page | 页面级别，显然只有在一个页面内可用。
request | 请求级别 服务器跳转，一次请求之后消失。
session | 会话级别 客户端跳转（服务器跳转），与浏览器有关，ie是在重新打开ie时才会不同。
application | 应用级别，当重启服务器时才会消失


#### 跳转方式
   1. 客户端跳转： 客户端跳转是服务器将请求结果返回给客户端，客户端再向服务器发起另一次请求。在客户端跳转的过程中，客户端一定要参与跳转的过程。客户端跳转后的url是最后一次请求的地址。
      客户端跳转能够进行网站外跳转。链接方式：<ahref = ""> </a>

   2. 表单提交
      <meta http-equiv = "refresh" content = "3; url =2.jsp">
      javascript:history.back();
      response.sendRedirect
      response.setHeader("refresh", "3; url ="request2.jsp");
      客户端跳转时的"/"表示的是服务器的根

   3. 服务器跳转（容器内跳转）：能够自动的在服务器内部进行页面的转换，所以对于客户端来说这是透明的。而request范围的属性会被传递到下一个页面。服务器跳转是第一个页面的地址。
      服务器跳转只能是站点内跳转。服务器跳转时的"/"代表本网站的根
      <jsp:forward page = " "/>
      pageContent.forward("request2.jsp");
      以"/"开始的路径代表绝对路径，反之代表相对路径


#### JSP中九大内置对象为：
JSP为简化页面的开发提供了一些内部的对象，在所有的JSP页面中都能使用这些内部对象。编写JSP的人员不需要对这些内部对象进行例化，只要调用其中的方法就能实现特定的功能。JSP主要有如下内置对象：

对象标识     | 对象含义 | 类型 | 作用域
-|-
request     | 请求对象      | javax.servlet.ServletRequest   | Request
response    | 响应对象      | javax.servlet.SrvletResponse   | Page
out         | 输出对象      | javax.servlet.jsp.JspWriter    | Page
pageContext | 页面上下文对象 | javax.servlet.jsp.PageContext  | Page
session     | 会话对象      | javax.servlet.http.HttpSession | Session
application | 应用程序对象  | javax.servlet.ServletContext    | Application
config      | 配置对象      | javax.servlet.ServletConfig    | Page
page        | 页面对象      | javax.lang.Object              | Page
exception   | 例外对象      | javax.lang.Throwable           | page

1. request对象:
客户端的请求信息被封装在request对象中，通过它才能了解到客户的需求，然后做出响应。它是HttpServletRequest类的实例。
2. response对象:
response对象包含了响应客户请求的有关信息，但在JSP中很少直接用到它。它是HttpServletResponse类的实例。
3. session对象:
session对象指的是客户端与服务器的一次会话，从客户连到服务器的一个WebApplication开始，直到客户端与服务器断开连接为止。它是HttpSession类的实例.
4. out对象:
out对象是JspWriter类的实例,是向客户端输出内容常用的对象
5. page对象:
page对象就是指向当前JSP页面本身，有点象类中的this指针，它是java.lang.Object类的实例
6. application对象:
application对象实现了用户间数据的共享，可存放全局变量。它开始于服务器的启动，直到服务器的关闭，在此期间，此对象将一直存在；这样在用户的前后连接或不同用户之间的连接中，可以对此对象的同一属性进行操作；在任何地方对此对象属性的操作，都将影响到其他用户对此的访问。服务器的启动和关闭决定了application对象的生命。它是ServletContext类的实例。
7. exception对象:
exception对象是一个例外对象，当一个页面在运行过程中发生了例外，就产生这个对象。如果一个JSP页面要应用此对象，就必须把isErrorPage设为true，否则无法编译。他实际上是java.lang.Throwable的对象
8. pageContext对象:
pageContext对象提供了对JSP页面内所有的对象及名字空间的访问，也就是说他可以访问到本页所在的SESSION，也可以取本页面所在的application的某一属性值，他相当于页面中所有功能的集大成者，它的本类名也叫pageContext。
9. config对象:
config对象是在一个Servlet初始化时，JSP引擎向它传递信息用的，此信息包括Servlet初始化时所要用到的参数（通过属性名和属性值构成）以及服务器的有关信息（通过传递一个ServletContext对象）


#### session简介
- Session是指一个终端用户与交互系统进行通信的时间间隔，通常指从注册进入系统到注销退出系统之间所经过的时间以及如果需要的话，可能还有一定的操作空间。
- 具体到Web中的Session指的就是用户在浏览某个网站时，从进入网站到浏览器关闭所经过的这段时间，也就是用户浏览这个网站所花费的时间。因此从上述的定义中我们可以看到，Session实际上是一个特定的时间概念。
- 需要注意的是，一个Session的概念需要包括特定的客户端，特定的服务器端以及不中断的操作时间。A用户和C服务器建立连接时所处的Session同B用户和C服务器建立连接时所处的Session是两个不同的Session。
- session的工作原理
 　　（1）当一个session第一次被启用时，一个唯一的标识被存储与本地的cookie中。
 　　（2）首先使用session_star()函数，PHP从session仓库中加载已经存储的session变量
 　　（3) 当执行PHP脚本时，通过使用session_register()函数注册session变量
 　　（4）当PHP脚本执行结束时，未被销毁的session变量会自动被保存在本地一定路径下的session库中，这个路径可以通过php.ini文件中的session.save_path指定，下次浏览网页时可以加载使用。

   三、在JSP中
Jsp的session是使用bean的一个生存期限,一般为page,session意思是在这个用户没有离开网站之前一直有效，如果无法判断用户何时离开，一般依据系统设定，tomcat中设定为30分钟.


#### Session的生命周期
　　以前在学习的时候没怎么注意，今天又回过头来仔细研究研究了一下Session的生命周期。

　　Session存储在服务器端，一般为了防止在服务器的内存中（为了高速存取），Sessinon在用户访问第一次访问服务器时创建，需要注意只有访问JSP、Servlet等程序时才会创建Session，只访问HTML、IMAGE等静态资源并不会创建Session，可调用request.getSession(true)强制生成Session。

Session什么时候失效？

　　1. 服务器会把长时间没有活动的Session从服务器内存中清除，此时Session便失效。Tomcat中Session的默认失效时间为20分钟。

　　2. 调用Session的invalidate方法。

　　Session对浏览器的要求：

　　虽然Session保存在服务器，对客户端是透明的，它的正常运行仍然需要客户端浏览器的支持。这是因为Session需要使用Cookie作为识别标志。HTTP协议是无状态的，Session不能依据HTTP连接来判断是否为同一客户，因此服务器向客户端浏览器发送一个名为JSESSIONID的Cookie，它的值为该Session的id（也就是HttpSession.getId()的返回值）。Session依据该Cookie来识别是否为同一用户。



　　该Cookie为服务器自动生成的，它的maxAge属性一般为-1，表示仅当前浏览器内有效，并且各浏览器窗口间不共享，关闭浏览器就会失效。因此同一机器的两个浏览器窗口访问服务器时，会生成两个不同的Session。但是由浏览器窗口内的链接、脚本等打开的新窗口（也就是说不是双击桌面浏览器图标等打开的窗口）除外。这类子窗口会共享父窗口的Cookie，因此会共享一个Session。



注意：新开的浏览器窗口会生成新的Session，但子窗口除外。子窗口会共用父窗口的Session。例如，在链接上右击，在弹出的快捷菜单中选择"在新窗口中打开"时，子窗口便可以访问父窗口的Session。



如果客户端浏览器将Cookie功能禁用，或者不支持Cookie怎么办？例如，绝大多数的手机浏览器都不支持Cookie。Java Web提供了另一种解决方案：URL地址重写。

　　URL地址重写是对客户端不支持Cookie的解决方案。URL地址重写的原理是将该用户Session的id信息重写到URL地址中。服务器能够解析重写后的URL获取Session的id。这样即使客户端不支持Cookie，也可以使用Session来记录用户状态。HttpServletResponse类提供了encodeURL(String url)实现URL地址重写，该方法会自动判断客户端是否支持Cookie。如果客户端支持Cookie，会将URL原封不动地输出来。如果客户端不支持Cookie，则会将用户Session的id重写到URL中。

注意：TOMCAT判断客户端浏览器是否支持Cookie的依据是请求中是否含有Cookie。尽管客户端可能会支持Cookie，但是由于第一次请求时不会携带任何Cookie（因为并无任何Cookie可以携带），URL地址重写后的地址中仍然会带有jsessionid。当第二次访问时服务器已经在浏览器中写入Cookie了，因此URL地址重写后的地址中就不会带有jsessionid了
