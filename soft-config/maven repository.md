# mavem pom 文件

[TOC]

## website

   https://search.maven.org/

## pom 相关资源

### 依赖

#### spring

```xml
```

#### 数据库

##### jdbc

   ```xml
   <!-- mysql -->
   <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.47</version>
         <scope>runtime</scope>
   </dependency>
   ```

##### mybatis

   ```xml
   <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis</artifactId>
         <version>3.4.6</version>
   </dependency>
   <!-- spring 配置 相关 -->
   <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis-spring</artifactId>
         <version>1.3.2</version>
   </dependency>
   ```

##### 连接池

   ```xml
   <!-- 数据库连接池 cp30 -->
   <dependency>
         <groupId>com.mchange</groupId>
         <artifactId>c3p0</artifactId>
         <version>0.9.5.2</version>
   </dependency>
   ```

   ```xml
   <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.12</version>
   </dependency>
   ```

#### utils

   ```xml
   ```

#### plugins

##### dubbo

   ```xml
   <dependency>
         <groupId>com.alibaba</groupId>
         <artifactId>dubbo</artifactId>
         <version>2.6.5</version>
         <exclusions>
            <exclusion>
               <artifactId>spring</artifactId>
               <groupId>org.springframework</groupId>
            </exclusion>
         </exclusions>
   </dependency>
   <dependency>
         <groupId>org.javassist</groupId>
         <artifactId>javassist</artifactId>
         <version>3.20.0-GA</version>
   </dependency>
   ```

##### zookeeper

   ```xml
   <dependency>
         <groupId>com.101tec</groupId>
         <artifactId>zkclient</artifactId>
         <version>0.10</version>
   </dependency>
   ```

##### cglib

   ```xml
   <dependency>
         <groupId>cglib</groupId>
         <artifactId>cglib</artifactId>
         <version>3.2.9</version>
   </dependency>
   ```

##### log4j

   ```xml
   <!-- log4j -->
   <dependency>
         <groupId>ch.qos.logback</groupId>
         <artifactId>logback-classic</artifactId>
         <version>1.2.3</version>
   </dependency>
   <dependency>
         <groupId>org.slf4j</groupId>
         <artifactId>slf4j-api</artifactId>
         <version>1.7.25</version>
   </dependency>
   <dependency>
         <groupId>org.slf4j</groupId>
         <artifactId>jul-to-slf4j</artifactId>
         <version>1.7.25</version>
   </dependency>
   <dependency>
         <groupId>org.slf4j</groupId>
         <artifactId>log4j-over-slf4j</artifactId>
         <version>1.7.25</version>
   </dependency>
   <dependency>
         <groupId>org.slf4j</groupId>
         <artifactId>jcl-over-slf4j</artifactId>
         <version>1.7.25</version>
   </dependency>
   <dependency>
         <groupId>commons-logging</groupId>
         <artifactId>commons-logging</artifactId>
         <version>1.2</version>
   </dependency>
   ```

#### test

   ```xml
   <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>4.12</version>
         <scope>test</scope>
   </dependency>
   ```

### h
