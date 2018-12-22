# spring 配置

   pom.xml

   ```xml
    <properties>
        <spring-version>5.1.3.RELEASE</spring-version>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-core</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-context</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-context-support</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-expression</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-beans</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-web</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-jdbc</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-tx</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-aspects</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-aop</artifactId>
         <version>${spring-version}</version>
   </dependency>
   <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-webmvc</artifactId>
         <version>${spring-version}</version>
   </dependency>
   ```

   spring 中的依赖
   spring-core 包含 spring-jcl
   spring-expression, spring-beans 包含 spring-core
   spring-aop, spring-web, spring-tx 包含 spring-beans, spring-core
   spring-context 还包含 spring-beans, spring-core, spring-aop, spring-expression
   spring-context-support 包含 spring-context
   spring-jdbc 包含 spring-tx
   spring-webmvc 包含 spring-web, spring-context,

## 集成

### 持久层

#### 集成数据源

##### c3p0

   pom.xml

   ```xml
   <dependency>
         <groupId>com.mchange</groupId>
         <artifactId>c3p0</artifactId>
         <version>0.9.5.2</version>
   </dependency>
   ```

   application.properties

   ```properties
   #mysql
   db.driver=com.mysql.jdbc.Driver
   db.url=jdbc:mysql://127.0.0.1:3306/cpfframe4j?autoReconnect=true&useSSL=false
   db.user=root
   db.password=cpfniliu4823
   db.minPoolSize=2
   db.maxPoolSize=5
   db.initialPoolSize=1
   db.loginTimeout=30
   db.acquireIncrement=1
   ```

   spring-context.xml

   ```xml
   <context:property-placeholder location="classpath:application.properties"></context:property-placeholder>

   <!-- 配置数据源连接通 c3p0连接方式 -->
   <bean id="userDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
      <property name="driverClass" value="${db.driver}"></property>
      <property name="jdbcUrl" value="${db.url}"></property>
      <property name="user" value="${db.user}"></property>
      <property name="password" value="${db.password}"></property>
      <property name="minPoolSize" value="${db.minPoolSize}"></property>
      <property name="maxPoolSize" value="${db.maxPoolSize}"></property>
      <property name="initialPoolSize" value="${db.initialPoolSize}"></property>
      <property name="loginTimeout" value="${db.loginTimeout}"></property>
      <property name="acquireIncrement" value="${db.acquireIncrement}"></property>
   </bean>
   ```

##### druid

   pom.xml

   ```xml
   <dependency>
         <groupId>com.alibaba</groupId>
         <artifactId>druid</artifactId>
         <version>1.1.12</version>
   </dependency>
   ```

   application.properties

   ```props
   datasource.user.url=jdbc:mysql://127.0.0.1:3306/cpfframe4j?autoReconnect=true&useSSL=false
   datasource.user.username=root
   datasource.user.password=cpfniliu4823
   datasource.user.initialSize=2
   datasource.user.minIdle=1
   datasource.user.maxActive=20
   datasource.user.maxWait=60000
   datasource.user.timeBetweenEvictionRunsMillis=60000
   datasource.user.minEvictableIdleTimeMillis=300000
   datasource.user.maxPoolPreparedStatementPerConnectionSize=20
   datasource.user.validationQuery=SELECT 'x'
   datasource.user.testWhileIdle=true
   datasource.user.testOnBorrow=false
   datasource.user.testOnReturn=false
   datasource.user.poolPreparedStatements=false
   datasource.user.filters=stat,config
   datasource.user.connectionProperties=config.decrypt=false
   ```

   spring-context.xml

   ```xml
   <context:property-placeholder location="classpath:application.properties"></context:property-placeholder>

   <bean id="userDataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
      <property name="url" value="${datasource.user.url}"/>
      <property name="username" value="${datasource.user.username}"/>
      <property name="password" value="${datasource.user.password}"/>
      <property name="initialSize" value="${datasource.user.initialSize}"/>
      <property name="minIdle" value="${datasource.user.minIdle}"/>
      <property name="maxActive" value="${datasource.user.maxActive}"/>
      <property name="maxWait" value="${datasource.user.maxWait}"/>
      <property name="timeBetweenEvictionRunsMillis" value="${datasource.user.timeBetweenEvictionRunsMillis}"/>
      <property name="minEvictableIdleTimeMillis" value="${datasource.user.minEvictableIdleTimeMillis}"/>
      <property name="maxPoolPreparedStatementPerConnectionSize" value="${datasource.user.maxPoolPreparedStatementPerConnectionSize}"/>
      <property name="validationQuery" value="${datasource.user.validationQuery}"/>
      <property name="testWhileIdle" value="${datasource.user.testWhileIdle}"/>
      <property name="testOnBorrow" value="${datasource.user.testOnBorrow}"/>
      <property name="testOnReturn" value="${datasource.user.testOnReturn}"/>
      <property name="poolPreparedStatements" value="${datasource.user.poolPreparedStatements}"/>
      <property name="connectionProperties" value="${datasource.user.connectionProperties}"/>
      <property name="filters" value="${datasource.user.filters}"/>
   </bean>
   ```

#### 集成持久层框架

##### 集成mybatis

   pom.xml

   ```xml
   <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.47</version>
         <scope>runtime</scope>
   </dependency>
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
         <version>3.2.9</version>
   </dependency>

   <build>
      <!--告诉 maven, 在打包时, 要打包 resource 下的文件-->
      <resources>
         <resource>
               <directory>src/main/resources</directory>
               <includes>
                  <include>**/*.properties</include>
                  <include>**/*.xml</include>
               </includes>
               <filtering>false</filtering>
         </resource>
         <resource>
               <directory>src/main/java</directory>
               <includes>
                  <include>**/*.xml</include>
               </includes>
               <filtering>false</filtering>
         </resource>
      </resources>
   </build>
   ```

   spring-context.xml

   ```xml
   <!-- 配置sessionfactory -->
   <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <!--配置数据源-->
      <property name="dataSource" ref="userDataSource" />
      <property name="typeAliasesPackage" value="cn.cpf.userservice.entity" />
      <!-- 自动扫描 mapping.xml 文件 -->
      <property name="mapperLocations" value="classpath*:cn/cpf/mybatis/inter/*Mapper.xml" />
   </bean>

   <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
      <!--DAO接口所在包名，Spring会自动查找其下的类-->
      <property name="basePackage" value="cn.cpf.mybatis.inter" />
      <!-- sqlSessionFactoryBeanName 用 value 而不用 ref, 如果用 ref 指定 sqlSessionFactory 很可能会因为初始化MyBatis时，jdbc.properties文件还没被加载进来，dataSource的属性值没有被替换，就开始构造sqlSessionFactory类，而加载失败-->
      <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
   </bean>
   ```

   note :
   1. sqlSessionFactoryBeanName 用 value 而不用 ref
      如果用 ref 指定 sqlSessionFactory 很可能会因为初始化MyBatis时，jdbc.properties文件还没被加载进来，dataSource的属性值没有被替换，就开始构造sqlSessionFactory类，而加载失败
   2. (待确认)扫描 自动扫描 mapping.xml 文件时, 要在 pom.xml 文件加上 build:resources:resource 告诉 maven 打包时将xml 文件也打进去

### 集成中间件

#### dubbo

#### zookeeper

      集成dubbo和zookeeper 看相应笔记

