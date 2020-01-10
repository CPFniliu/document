

```xml

    <context:component-scan base-package="ats.ity" >
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    </context:component-scan>

    <tx:annotation-driven transaction-manager="transactionManager"/>
    <context:annotation-config/>

    <!-- =============================================================== -->
    <!-- shiro config -->
    <!-- =============================================================== -->
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>

    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <property name="loginUrl" value="/validate"/>
        <property name="successUrl" value="/index"/>
        <property name="filters">
            <util:map>
                <entry key="login" value-ref="loginFilter"/>
                <entry key="logout" value-ref="logoutFilter"/>
                <entry key="rememberAuth" value-ref="rememberAuthFilter"/>
            </util:map>
        </property>
        <!--&lt;!&ndash;anon匿名 login登录认证 user用户已登录 logout退出filter &ndash;&gt;-->
        <property name="filterChainDefinitions">
            <value>
                /ityOpenBusiness/** = anon
                /debug/** = anon
                /anon/** = anon
                /pcRegister/** = anon
                /static/** = anon
                /singleAgencyHallOpen/** = anon
                /validate = login
                /loginIty = login
                /logout = logout
                /login = anon
                /register = anon
                /weixinRegister/** = anon
                /qq/** = anon
                /getInstitutionAdminByUuid = anon
                /QRValidate = anon
                /admin/updateForgrtPassWd/** = anon
                /yanzhengma.jpg/** = anon
                /*.html = anon
                /etc/** = anon
                /*.map = anon
                /**/*.jpg = anon
                /jsp/** = anon
                /** = rememberAuth
            </value>
        </property>
    </bean>
    <!-- <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="realm" ref="authorizingRealm"/>
        <property name="cacheManager" ref="shiroEhcacheManager"/>
    </bean> -->
    <!-- <bean id="authorizingRealm" class="ats.ity.institution.spring.shiro.CustAuthorizingRealm"/>
    <bean id="loginFilter" class="ats.ity.institution.spring.shiro.LoginAuthenticationFilter"/>
    <bean id="logoutFilter" class="ats.ity.institution.spring.shiro.CustLogoutFilter"/>
    <bean id="rememberAuthFilter" class="ats.ity.institution.spring.shiro.RememberAuthenticationFilter"/> -->

    <!-- <bean id="shiroEhcacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
        <property name="cacheManagerConfigFile" value="classpath:ehcache-shiro.xml"/>
    </bean> -->

    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

    <!--扫描上下文，寻找所有的Advistor(通知器），将这些Advisor应用到所有符合切入点的Bean中。所以必须在lifecycleBeanPostProcessor创建之后创建，-->
    <!--<bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">-->
        <!--<property name="securityManager" ref="securityManager"/>-->
    <!--</bean>-->
    <!--<import resource="classpath:applicationContext-shiro.xml"/>-->

    <!-- =============================================================== -->
    <!-- Resources : it will load jdbc.properties and set values to dataSource -->
    <!-- =============================================================== -->
    <bean id="propertyConfigurer"
          class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <list>
                <value>classpath:jdbc.properties</value>
                <value>classpath:applicationConfig.properties</value>
                <value>classpath:environmentConfig.properties</value>
            </list>
        </property>
        <property name="fileEncoding" value="UTF-8"></property>
    </bean>

    <!-- =============================================================== -->
    <!-- Data Source -->
    <!-- =============================================================== -->

    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbcDriverClassName}"/>
        <property name="url" value="${jdbcUrl}"/>
        <property name="username" value="${jdbcUsername}"/>
        <property name="password" value="${jdbcPassword}"/>
        <property name="maxActive" value="${jdbcMaxActive}"/>
        <property name="maxIdle" value="${jdbcMaxIdle}"/>
        <property name="maxWait" value="${jdbcMaxWait}"/>
        <property name="validationQuery" value="${jdbcValidationQuery}"/>
        <property name="validationQueryTimeout" value="${jdbcValidationTimeOut}"/>
        <property name="testOnBorrow" value="true"/>
    </bean>

    <!-- 映射类 -->
    <bean id="BondTaskController" class="ats.ity.institution.controller.BondTaskController"/>

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations">
            <array>
                <value>classpath:ats/ity/common/dao/basic/*.xml</value>
                <value>classpath:ats/ity/common/dao/combine/*.xml</value>
                <value>classpath:ats/ity/common/webIm/dao/*.xml</value>
            </array>
        </property>
    </bean>

    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="ats.ity.common.dao.basic, ats.ity.common.dao.combine, ats.ity.common.webIm.dao"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>

    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--多theme -->
    <bean class="org.springframework.ui.context.support.ResourceBundleThemeSource" id="themeSource">
        <property name="basenamePrefix" value="theme."/>
    </bean>

    <bean id="themeResolver"
          class="org.springframework.web.servlet.theme.SessionThemeResolver">
        <property name="defaultThemeName" value="theme1"/>
    </bean>

    <!--指定所上传文件的总大小不能超过20M。注意maxUploadSize属性的限制不是针对单个文件，而是所有文件的容量之和-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="maxUploadSize" value="20000000"/>
        <property name="defaultEncoding" value="utf-8"></property>
    </bean>

    <!--线程池  -->
    <bean id="taskExecutor"
          class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
        <!-- 核心线程数，默认为1 -->
        <property name="corePoolSize" value="10"/>

        <!-- 最大线程数，默认为Integer.MAX_VALUE -->
        <property name="maxPoolSize" value="20"/>

        <!-- 队列最大长度，一般需要设置值>=notifyScheduledMainExecutor.maxNum；默认为Integer.MAX_VALUE
            <property name="queueCapacity" value="1000" /> -->

        <!-- 线程池维护线程所允许的空闲时间，默认为60s -->
        <property name="keepAliveSeconds" value="300"/>

        <!-- 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者 -->
        <property name="rejectedExecutionHandler">
            <!-- AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常 -->
            <!-- CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度 -->
            <!-- DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行 -->
            <!-- DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行 -->
            <bean class="java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy"/>
        </property>
    </bean>
```

```sql
create table qq (
    id varchar(12) not null primary key comment 'QQ号',
    nickname varchar(20) not null comment 'QQ 昵称'
) comment 'qq号';

create table qq_group (
    id varchar(12) primary key comment 'QQ群号',
    name varchar(50) not null comment 'QQ群名称',
    remark varchar(200) default '' comment '简介'
) comment 'QQ群表';

create table qq_group_member (
    id integer(9) not null primary key comment 'qq群成员主键',
    qq_id varchar(12) not null comment 'qq群号',
    qq_group_id varchar(12) not null comment 'QQ群号',
    nickname varchar(50) not null DEFAULT '' comment 'qq 群昵称',
    remark varchar(200)  default '' comment 'QQ 群简介'
) comment 'qq群成员表';

```














# 编译的时候跳过测试

方式1：用命令带上参数

`mvn install -Dmaven.test.skip=true`

方式2：在pom.xml里面配置

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
