# mybatis 使用示例

[TOC]

## 配置相关

### pom 文件

   ```xml
   <dependencies>
      <dependency>
         <groupId>com.mchange</groupId>
         <artifactId>c3p0</artifactId>
      </dependency>
      <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
      </dependency>
      <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis</artifactId>
      </dependency>
      <dependency>
         <groupId>cglib</groupId>
         <artifactId>cglib</artifactId>
      </dependency>
   </dependencies>

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

### db.properties

   ```properties
   #mysql
   db.driver=com.mysql.jdbc.Driver
   db.url=jdbc:mysql://127.0.0.1:3306/frame4j?autoReconnect=true&useSSL=false
   db.user=root
   db.password=11111
   ```

### xml 配置文件 mybatis-config.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
         "http=//mybatis.org/dtd/mybatis-3-config.dtd">

   <configuration>
      <!-- 配置扫描文件 -->
      <properties resource="db.properties"/>

      <environments default="development">
         <environment id="development">
               <!-- 配置事务类型 -->
               <transactionManager type="JDBC"/>
               <!-- 配置数据源 -->
               <dataSource type="POOLED">
                  <property name="driver" value="${db.driver}"/>
                  <property name="url" value="${db.url}"/>
                  <property name="username" value="${db.user}"/>
                  <property name="password" value="${db.password}"/>
               </dataSource>
         </environment>
      </environments>

      <!-- 配置扫描路径 -->
      <mappers>
         <package name="cn.cpf.mybatis.IDao"/>
      </mappers>

   </configuration>
   ```

### MySqlSessionFactory.java

   ```java
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.*;

   import java.io.IOException;
   import java.io.InputStream;

   // 配置为饿汉式
   public class MySqlSessionFactory {

      private static SqlSessionFactory sqlSessionFactory = null;

      private MySqlSessionFactory(){}

      static {
            try {
               InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
               sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
         } catch (IOException e) {
               e.printStackTrace();
         }
      }

      public static SqlSession getInstance(){
         return sqlSessionFactory.openSession();
      }

      public static SqlSession getBatchInstance(){
         return sqlSessionFactory.openSession(ExecutorType.BATCH);
      }
   }
   ```

### 简单使用

   ```java
   public static void main(String[] args) {
      SqlSession instance = MySqlSessionFactory.getInstance();
      UserMapper mapper = instance.getMapper(UserMapper.class);
      User admin = mapper.selectByUsername("admin");
      System.out.println(admin);
      instance.close();
   }
   ```
