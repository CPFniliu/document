# mybatis 批量执行

## mysql

前提条件, 可能造成失败的原因

   1. 首先jdbc需要加上 `allowMutiQueries=true`
   2. 确保spring开启了aop支持 
        `<aop:config proxy-target-class="true"/>`.

   > 若无此项可能会造成程序能够正常运行但是实际却无法走批量处理, 导致执行特别慢

方案一

1. 在全局配置文件applcationContext.xml中加入

    ```xml
        <!-- 配置一个可以批量执行的sqlSession -->
        <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
            <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
            <constructor-arg name="executorType" value="BATCH"></constructor-arg>
        </bean>
    ```

2. 在serviceImpl中加入

    ```java
        @Autowired
        private SqlSession sqlSession;

        //批量保存员工
        @Override
        public Integer batchEmp() {
            // TODO Auto-generated method stub
                //批量保存执行前时间
                long start=System.currentTimeMillis();
                EmployeeMapper mapper=    sqlSession.getMapper(EmployeeMapper.class);
                for (int i = 0; i < 10000; i++) {
                    mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0,5),"b","1"));
                }
                long end=  System.currentTimeMillis();
                long time2= end-start;
                //批量保存执行后的时间
                System.out.println("执行时长"+time2);
            return (int) time2;
        }
    ```

方案二

1. 在serviceImpl中加入`SqlSessionFactory sqlSessionFactory`, 每次使用的时候直接 `openSession(ExecutorType.BATCH)`

    ```java
        @Autowired
        private SqlSessionFactory sqlSessionFactory;

        @Override
        public Integer batchEmp() {
            SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)
        }
    ```

方案三

1. 使用mybatis里面的 foreach, 在sql角度上使用批量处理. 这种方式更快, 但没有上面两种方案简单
