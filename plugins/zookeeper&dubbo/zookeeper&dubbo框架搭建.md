## zookeeper & dubbo 框架搭建

1. 建立一个父maven项目parent,
   pom.xml 中加入
+ 建立一个Controller Module项目
   ###### 建立一个TestControl.class, 父pom parent中添加相关引用

  - parent项目中添加依赖
   ```
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>
   ```
  - 类代码   
      ```
      @RestController
      public class TestController {

          private ITestService testService;

          @RequestMapping("/getData")
          public String getData(String name) {
              return testService.getData(name);
          }
      }
      ```

+ 建立一个Service Module
    3.1 建立一个ITestService接口
    3.2 执行 run maven install 命令, 将service模块打包至本地仓库
    3.3 在controller模块pom.xml中加入service模块的依赖, TestController类中添加ITestServic接口的引用, 并完善 TestController 类, 如类代码所示.
