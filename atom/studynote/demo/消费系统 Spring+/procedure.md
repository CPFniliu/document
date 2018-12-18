# 1. 分析，建立数据库。

执行sql脚本

# 2. 集成springMVC

   1. 在pom.xml中添加相关依赖
    添加 spring-mvc，servlet-api，jstl。
    ```XML
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
    ```

   2. 在 src/main/resources 目录下添加 spring-mvc.xml 配置文件
    ```XML
        <!-- 使用注解来进行请求映射 -->
        <mvc:annotation-driven></mvc:annotation-driven>

        <!-- 视图解析器 -->
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <!-- 重定向时是否添加上下文路径 -->
            <property name="redirectContextRelative" value="true"></property>
            <property name="prefix" value="/WEB-INF/views/"></property>
            <property name="suffix" value=".jsp"></property>
        </bean>

        <!-- 扫描 -->
        <context:component-scan base-package="cn.cpf.vip.web.handler"></context:component-scan>
    ```

   3. 在 web.xml 中配置 spring-mvc 前端控制器 DispatcherServlet。
       1. 配置随服务器启动而初始化。
       2. 配置参数 contextConfigLocation,指向 spring-mvc 的前端控制器。
       3. 配置 servlet-mapping（仅仅处理 \***.do** 请求）。

   ```XML
    <!-- 配置前端控制器 -->
        <servlet>
            <description>spring-mvc配置前端控制器</description>
            <servlet-name>spring-mvc</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:spring-mvc.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
        </servlet>

        <servlet-mapping>
            <servlet-name>spring-mvc</servlet-name>
            <url-pattern>*.do</url-pattern>
        </servlet-mapping>
    ```

   4. web.xml 中配置请求和应答字段编码过滤器
    CharacterEncodeingFilter
    ```XML
        <!-- 配置过滤器 -->
        <filter>
            <description>请求和应答字符编码过滤器</description>
            <filter-name>encoding-filter</filter-name>
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        </filter>
        <filter-mapping>
            <filter-name>encoding-filter</filter-name>
            <servlet-name>spring-mvc</servlet-name>
        </filter-mapping>
    ```

   5. 配置 404，500 页面

### 3. 集成Spring
   1. 添加 Spring 依赖。
      集成springMVC 时已添加过，因此在此不在添加。
    ```XML
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-webmvc</artifactId>
          <version>4.3.13.RELEASE</version>
      </dependency>
    ```
   2. 新建并配置 spring-context.xml 文件。
      添加业务层扫描器
      `<context:component-scan base-package="cn.cpf.vip.function"></context:component-scan>`
   3. 在 web.xml 中配置 ContextLoaderListener 监听器，启动 Spring 容器。配置 contextConfigLocation，指定 spring-context.xml 文件路径。
    ```XML
    <!--配置监听器-->
    <listener>
        <description>spring监听器</description>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring-context.xml</param-value>
    </context-param>
    ```

### 4. 集成数据库连接池 c3p0
   1. 添加依赖。
   ```XML
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
   ```
   2. 新建 db.properties 文件.
    ```properties
     #mysql
     db.driver=com.mysql.jdbc.Driver
     db.url=jdbc:mysql://127.0.0.1:3306/cpfframe4j?autoReconnect=true&useSSL=false
     db.user=root
     db.password=cpfniliu4823
    ```
   3. 在 spring-context 中定义 c3p0 数据源 ComboPooledDataSource
    ```XML

    <context:property-placeholder location="classpath:db.properties"></context:property-placeholder>

    <!-- 配置数据源连接通 c3p0连接方式 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
       <property name="driverClass" value="${db.driver}"></property>
       <property name="jdbcUrl" value="${db.url}"></property>
       <property name="user" value="${db.user}"></property>
       <property name="password" value="${db.password}"></property>
       <property name="minPoolSize" value="1"></property>
       <property name="maxPoolSize" value="1"></property>
       <property name="initialPoolSize" value="1"></property>
       <property name="loginTimeout" value="30"></property>
       <property name="acquireIncrement" value="1"></property>
    </bean>
    ```

### 5. 声明式事务管理
   1. 添加依赖spring-jdbc, spring-tx
   ```XML
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>
    ```

   2. spring-context.xml
    ```XML
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:tx="http://www.springframework.org/schema/tx"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/tx
           http://www.springframework.org/schema/tx/spring-tx.xsd">

        <!--配置事务管理器，只想数据源-->
        <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <property name="dataSource" ref="dataSource"/>
        </bean>

        <!-- 使用annotation定义事务 -->
        <tx:annotation-driven transaction-manager="dataSourceTransactionManager" />
    </beans>
    ```

### 6. 集成 MyBatis
   1. 添加依赖 mybatis,mybatis-spring,pagehelper,cglib.
   ```XML
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.2</version>
        </dependency>
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>3.2.4</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.1.7</version>
        </dependency>
    ```
   2. 编写 mybatis 配置文件, 集成分页插件.
      ```XML
      <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http=//mybatis.org/dtd/mybatis-3-config.dtd">

        <configuration>
            <settings>
                <!--该配置影响的所有映射器中配置的缓存的全局开关 values(true | false)	default: true-->
                <setting name="cacheEnabled" value="true"/>
                <!--lazyLoadingEnabled	延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态	true | false	false-->
                <setting name="lazyLoadingEnabled" value="true"/>
                <!--defaultStatementTimeout	设置超时时间，它决定驱动等待数据库响应的秒数。	Any positive integer	Not Set (null)-->
                <setting name="defaultStatementTimeout" value="30"/>
                <!--mapUnderscoreToCamelCase	是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。	true | false	False-->
                <setting name="mapUnderscoreToCamelCase" value="true"/>
                <!--proxyFactory	指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。	CGLIB | JAVASSIST	JAVASSIST (MyBatis 3.3 or above)-->
                <setting name="proxyFactory" value="CGLIB"/>
            </settings>

            <!--配置pageHelper拦截器分页插件-->
            <plugins>
                <plugin interceptor="com.github.pagehelper.PageInterceptor">
                    <!-- 使用下面的方式配置参数-->
                    <!-- 配置分页插件使用哪种方言，此处配置mysql-->
                    <property name="helperDialect" value="mysql"/>
                    <!--offsetAsPageNum：默认值为 false，该参数对使用 RowBounds 作为分页参数时有效。
                    当该参数设置为 true 时，会将 RowBounds 中的 offset 参数当成 pageNum 使用，
                    可以用页码和页面大小两个参数进行分页。-->
                    <property name="offsetAsPageNum" value="false"/>
                    <!--rowBoundsWithCount：默认值为false，该参数对使用 RowBounds 作为分页参数时有效。
                    当该参数设置为true时，使用 RowBounds 分页会进行 count 查询-->
                    <property name="rowBoundsWithCount" value="false"/>
                    <!--pageSizeZero：默认值为 false，当该参数设置为 true 时，
                    如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果
                    （相当于没有执行分页查询，但是返回结果仍然是 Page 类型）-->
                    <property name="pageSizeZero" value="true"/>
                    <!--reasonable：分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页，
                    pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询-->
                    <property name="reasonable" value="false"/>
                    <!--supportMethodsArguments：支持通过 Mapper 接口参数来传递分页参数，默认值false，
                    分页插件会从查询方法的参数值中，自动根据上面 params 配置的字段中取值，
                    查找到合适的值时就会自动分页。 使用方法可以参考测试代码中的
                    com.github.pagehelper.test.basic 包下的 ArgumentsMapTest 和 ArgumentsObjTest-->
                    <property name="supportMethodsArguments" value="false"/>
                    <!-- always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page -->
                    <property name="returnPageInfo" value="none"/>
                </plugin>
            </plugins>

        </configuration>

      ```
   3. 在 spring-context.xml 中配置 SqlSessionFactoryBean.
      1. 指定数据源。
      2. 指定 mybatis 配置文件路径。
      3. 指定 mapper 文件路径。

    ```XML
    <bean class="org.mybatis.spring.SqlSessionFactoryBean">
      <!-- 指定数据源 -->
      <property name="dataSource" ref="dataSource"/>
      <!-- 指定 mybatis 配置文件路径 -->
      <property name="configLocation" value="classpath:mybatis-config.xml"/>
      <!-- 指定 mapper 文件路径 -->
      <property name="mapperLocations">
          <list>
              <value>classpath:mapper/*.xml</value>
          </list>
      </property>
    </bean>
    ```
   4. 在 spring-context.xml 配置扫描 mapper 生成 dao（MapperScannerConfigurer）
      1. 指定 SqlSessionFactoryBean。
      2. 指定要扫描的包。

      ```XML
      <!--扫描生成所有的 dao 层对象-->
      <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <!-- 指定要扫描的包 -->
          <property name="basePackage" value="cn.cpf.vip.dao"/>
          <property name="sqlSessionTemplateBeanName" value="sqlSessionFactory"/>
      </bean>
      ```
