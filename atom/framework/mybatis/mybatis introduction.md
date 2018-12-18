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

### introduction
1. What is Mybatis?
   - Mybatis is a first class persistence framework with support for custom SQL, stored procedures and advanced mappings.
   - Mybatis eliminates almost all of the JDBC code and manual setting of parameters and retrieval of results.
   - MyBatis can use simple XML or Annotations for configuration and map primitives, Map interfaces and Java POJO(Plain Old Java Objects) to database records.

2. mybatis 作用域和生命周期

相关| Scope
-|-
SqlSessionFactoryBuilder | method
SqlSessionFactory | application
SqlSession | request/method （可以认为是线程级）
Mapper | method

##### Mapper 的xml 和 annotation形式
1. 两者相互兼容， 但是不能有同样的id，否则会报错。
