# dubbo


## dubbo 配置数据加密

## dubbo框架中运行spring容器

   运行spring容器的方式有三种
   1：使用tomcat、jetty等servlet容器运行
   2：自己写一个Main方法运行
   3：使用dubbo框架提供的Main方法运行
   前面两种有一定的局限性，很多缺点，具体就不一一列举，有兴趣的同学可以研究一下，今天就给大家讲如何实现第三种方式

### dubbo框架中Main方法运行spring容器

   优点：由框架本身提供，可实现优雅关机

   1. spring路径存放路径
     按照dubbo官网解说，要使得Main能正确的启用Spring容器，要求spring配置文件存放至
      classpath*:META-INF/spring/*.xml路径底下

## 使用

[jdbc.properties](/soft-config/resources files/template/jdbc)

```props
# 服务名
application.name=user-service
# 服务端口号
dubbo.service.provider.port=20880
dubbo.service.provider.threads=200
# 缓存位置
dubbo.cache.dir=/logs/cache

# 服务发布zookeeper地址
dubbo.zk.servers=192.168.198.128:2181
dubbo.zk.group=cpf-lottery-dubbo

# 管理人
dubbo.application.owner=cpfsingjie
dubbo.protocol.accesslog=/logs/dubbo-user.log
```

xml 文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans.xsd
      http://code.alibabatech.com/schema/dubbo
      http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

      <!--提供方信息-->
      <dubbo:application name="${application.name}" owner="${dubbo.application.owner}"/>
      <!-- 中介 注册中心 : zookeeper  redis-->
      <dubbo:registry protocol="zookeeper" address="${dubbo.zk.servers}" group="${dubbo.zk.group}" file="${dubbo.cache.dir}/user-service.cache"/>
      <!-- 第三步：设置dubbo的端口号     192.168.40.88:20880/接口  -->
      <dubbo:protocol name="dubbo" port="${dubbo.service.provider.port}" accesslog="${dubbo.protocol.accesslog}"/>


      <!-- 第四步：设置服务提供方 提供的接口 也可以配置其他文件-->
      <dubbo:service interface="cn.cpf.userservice.api.IUserCoreService" ref="userCoreService"/>

</beans>
```