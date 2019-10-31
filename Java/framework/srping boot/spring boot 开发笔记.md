# spring boot 开发笔记

## 流程

### 1. 生成spring boot项目

- IDEA 生成

- 官网下载文件

### 2. spring boot 配置

#### 2.2 properties

自定义Property
   在web开发过程中，我经常需要自定义一些配置文件，如何使用？

自定义配置类
   spring boot使用 application.properties 默认了很多配置。但需要自己添加一些配置的时候，我们应该怎么做呢。
若继续在application.properties中添加
如：

```properties
wisely2.name=wyf2
wisely2.gender=male2
```

定义配置类：


@ConfigurationProperties(prefix = "wisely2")
public class Wisely2Settings {
private String name;
private String gender;
public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public String getGender() {
return gender;
}
public void setGender(String gender) {
this.gender = gender;
}
} 
 
若新用新的配置文件
如我新建一个wisely.properties
wisely.name=wangyunfei
wisely.gender=male
 
需定义如下配置类

```java
@ConfigurationProperties(prefix = "wisely",locations = "classpath:config/wisely.properties")
public class WiselySettings {
   private String name;
   private String gender;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }
}
 ```

最后注意在spring Boot入口类加上@EnableConfigurationProperties
@SpringBootApplication
@EnableConfigurationProperties({WiselySettings.class,Wisely2Settings.class})
public class DemoApplication {

public static void main(String[] args) {
SpringApplication.run(DemoApplication.class, args);
}
}
 
使用定义的properties
@Controller
public class TestController {
@Autowired
WiselySettings wiselySettings;
@Autowired
Wisely2Settings wisely2Settings;

@RequestMapping("/test")
public @ResponseBody String test(){
System.out.println(wiselySettings.getGender()+"---"+wiselySettings.getName());
System.out.println(wisely2Settings.getGender()+"==="+wisely2Settings.getGender());
return "ok";
} 
}
#### 2.3 Filter

我们常常在项目中会使用filters用于录调用日志、排除有XSS威胁的字符、执行权限验证等等。Spring Boot自动添加了`OrderedCharacterEncodingFilter` 和 `HiddenHttpMethodFilter`，并且我们可以自定义Filter。

两个步骤：

1. 实现Filter接口，实现Filter方法 与传统的Filter 的实现过程一样,  实现Filter 过滤器 重写 init(), destroy(), doFilter(request,response,chainFilter);  

2. 写一个类 使用`@Configuration`注解，将自定义Filter加入过滤链(获取RemoteIpFIlter 对象   将自定义FIlter 在 `FilterRegistrationBean` 中注册);

   ```java
   package spring.demo.filter;

   import java.io.IOException;

   import javax.servlet.Filter;
   import javax.servlet.FilterChain;
   import javax.servlet.FilterConfig;
   import javax.servlet.ServletException;
   import javax.servlet.ServletRequest;
   import javax.servlet.ServletResponse;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   public class MyFilter implements Filter {

      @Override
      public void destroy() {
         // TODO Auto-generated method stub
         System.out.println("过滤器的销毁");
      }

      @Override
      public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain filterChain)
            throws IOException, ServletException {
         // TODO Auto-generated method stub

         HttpServletRequest request = (HttpServletRequest) arg0;
         HttpServletResponse response = (HttpServletResponse)arg1;
         System.out.println("this is MyFilter,url :"+request.getRequestURI());
         filterChain.doFilter(request, response);
      }

      @Override
      public void init(FilterConfig arg0) throws ServletException {
         // TODO Auto-generated method stub
         System.out.println("过滤器的初始化");
      }

   }
   ```

   ```java
   package spring.demo.filter;

   import org.apache.catalina.filters.RemoteIpFilter;
   import org.springframework.boot.web.servlet.FilterRegistrationBean;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;

   @Configuration
   public class WebConfiguration {
      @Bean
      public RemoteIpFilter remoteIpFilter() {
         return new RemoteIpFilter();
      }
      @Bean
      public FilterRegistrationBean testFilterRegistration() {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.setFilter(new MyFilter());
            registration.addUrlPatterns("/*");
            //registration.addInitParameter("paramName", "paramValue");
            registration.setName("MyFilter");
            registration.setOrder(1);
            return registration;
         }
   }
   ```
