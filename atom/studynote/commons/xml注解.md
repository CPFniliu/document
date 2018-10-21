#### spring

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 扫描 spring frame 下 相应包 下管理的 Bean -->
    <context:component-scan base-package="cn.cpf.spring"></context:component-scan>

    <!-- 打开 AOP 配置 -->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

</beans>
```

相应注解关系 | xmlns | schema
-|-
context:component-scan | xmlns:context="http://www.springframework.org/schema/context" | http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
aop:aspectj-autoproxy | xmlns:aop="http://www.springframework.org/schema/aop" | http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
dubbo:application | xmlns:dubbo="http://code.alibabatech.com/schema/dubbo" | http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd"
