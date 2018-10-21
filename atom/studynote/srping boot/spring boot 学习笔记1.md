### spring boot 学习笔记

1. 简介
   1.1 简化Spring开发的一个框架.
   1.2 spring boot整合了几乎所有的框架.
   1.3 设计目的是用来简化新Spring应用的初始搭建以及开发过程。
   1.4 奉行的宗旨: 废除掉所有配置文件, 让开发变得简单纯粹, 核心 :  尽量实现零配置,
   1.5 Spring Boot 并不是对 Spring 功能上的增强，而是提供了一种快速使用 Spring 的方式。

2. 优点
　　创建独立的 Spring 应用程序
　　嵌入的 Tomcat，无需部署 WAR 文件
　　简化 Maven 配置
　　自动配置 Spring
　　提供生产就绪型功能，如指标，健康检查和外部配置
　　开箱即用，没有代码生成，也无需 XML 配置。



1.1 建立一个quick state maven项目
1.2 配置一个父pom
1.2.1 建立一个maven项目(quickStart)
1.2.2 去掉相关配置信息,pom.xml文件的打包类型改为pom类型


注解

annotation | 含义
-|-
@Controller | 代表当前类为控制器
@EnableAutoConfiguration| 代表自动注解配置
@RequestMapping("/") | 代表映射路径
@ResponseBody | 代表返回数据以字符串或者是json的格式返回, 没有该注解则默认跳转返回字符串代表的url
@SpringBootApplication | @EnableAutoConfiguration + @ComponentScan("当前package路径") + 其他配置
@RestController | 代表其中所有方法都以字符串或者是json的格式返回


#### 配置路径访问
