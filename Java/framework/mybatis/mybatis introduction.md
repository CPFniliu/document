### 学习思路
1. 认识mybatis
2. 使用mybatis
   1. 方式
      - 编程式
      - 集成式
   2. 两种方式对比
      两种Sql配置方式
         1. XML式
         2. 注解式
   3. 业务开发使用顺序
      1. 分析业务
      2. 定义表结构
      3. generator 生成我们所需要的类
   4. Scope
      1. SqlSessionFactory
      2. SqlSessionFactory
      3. SqlSession
      4. Mapper
3. 配置文件
   1. configuration 认识mybatis-config.xml
      1. environment
      2. type handle
      3. plugin
   2. mapper.xml
4. Best practise
   1. 分页
   2. 批量操作
   3. 联合查询

## mybatis 简介

### introduction

1. What is Mybatis?
   - Mybatis is a first class persistence framework with support for custom SQL, stored procedures and advanced mappings.
   - Mybatis eliminates almost all of the JDBC code and manual setting of parameters and retrieval of results.
   - MyBatis can use simple XML or Annotations for configuration and map primitives, Map interfaces and Java POJO(Plain Old Java Objects) to database records.

### 原理 principle

mybatis通过配置文件创建sqlsessionFactory，sqlsessionFactory根据配置文件，配置文件来源于两个方面: 一个是xml，一个是Java中的注解，获取sqlSession。SQLSession包含了执行sql语句的所有方法，可以通过SQLSession直接运行映射的sql语句，完成对数据的增删改查和事物的提交工作，用完之后关闭SQLSession。

### 工作的流程 

mapper接口：

接口的全类名是xml文件中namespace的值。

接口中的方法名是xml文件中mapperstatement的id值。
接口中方法的参数就是传递给sql的参数
mapper接口是没有实现类的，当调用一个方法时，接口的全类名定位一个配置文件，接口的方法名定位这个配置文件中的一个mapperStatment，所以说mapper的方法名是不能重载的，因为mapperStatment的保存和寻找策略。
mapper接口的工作原理是，mybatis会使用jdk动态代理方式为mapper接口创建proxy对象，代理对象会拦截接口中的方法，转而执行mapperStatment所代表的sql语句，然后将执行的结果封装返回。

### mybatis解决的问题

1. 用xml管理sql语句，让Java代码和sql语句分离，使得代码更易维护。
2. 解决了sql语句参数不定的问题。xml中可以通过where条件决定sql语句的条件参数。mybatis将Java对象映射到sql语句，通过statement的parameterType定义输入参数的类型。
3. mybatis自动将结果集封装成Java对象， 通过statement的resultType定义输出的类型。避免了因sql变化，对结果集处理麻烦的问题。

### mybatis 作用域和生命周期

相关| Scope
-|-
SqlSessionFactoryBuilder | method
SqlSessionFactory | application
SqlSession | request/method （可以认为是线程级）
Mapper | method

### Mapper 的xml 和 annotation形式

1. 两者相互兼容， 但是不能有同样的id，否则会报错。
