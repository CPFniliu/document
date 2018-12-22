# [Servlet访问URL映射配置](http://www.cnblogs.com/xdp-gacl/p/3760336.html)

由于客户端是通过URL地址访问web服务器中的资源，所以Servlet程序若想被外界访问，必须把servlet程序映射到一个URL地址上，这个工作在web.xml文件中使用\<servlet\> 元素和\<servlet-mapping\>元素完成。

\<servlet\> : \<servlet\>元素用于注册 Servlet，它包含有两个主要的子元素：\<servlet-name\>和\<servlet-class\>，分别用于设置Servlet的注册名称和Servlet的完整类名。

\<servlet-mapping\>元素用于映射一个已注册的Servlet的一个对外访问路径，它包含有两个子元素：\<servlet-name\>和\<url-pattern\>，分别用于指定Servlet的注册名称和Servlet的对外访问路径。例如：

```xml
   <servlet>
      <servlet-name>ServletDemo1</servlet-name>
      <servlet-class>gacl.servlet.study.ServletDemo1</servlet-class>
   </servlet>

   <servlet-mapping>
      <servlet-name>ServletDemo1</servlet-name>
      <url-pattern>/servlet/ServletDemo1</url-pattern>
   </servlet-mapping>
```