# Apache Shiro

[TOC]

## basic knowledge

### intro

Apache Shiro是一个功能强大且易于使用的Java安全框架，提供了认证，授权，加密，和会话管理

如同 Spring security 一样都是是一个权限安全框架，但是与Spring Security相比，在于他使用了和比较简洁易懂的认证和授权方式。

### 三大核心组件：

#### Subject ：当前用户的操作

Subject：即当前用户，在权限管理的应用程序里往往需要知道谁能够操作什么，谁拥有操作该程序的权利，shiro中则需要通过Subject来提供基础的当前用户信息，Subject 不仅仅代表某个用户，也可以是第三方进程、后台帐户（Daemon Account）或其他类似事物。

#### SecurityManager：用于管理所有的Subject

SecurityManager：即所有Subject的管理者，这是Shiro框架的核心组件，可以把他看做是一个Shiro框架的全局管理组件，用于调度各种Shiro框架的服务。

#### Realms：用于进行权限信息的验证

Realms：Realms则是用户的信息认证器和用户的权限人证器，我们需要自己来实现Realms来自定义的管理我们自己系统内部的权限规则。

### Authentication 和 Authorization

在shiro的用户权限认证过程中其通过两个方法来实现：

1、Authentication：是验证用户身份的过程。

2、Authorization：是授权访问控制，用于对用户进行的操作进行人证授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。








4、其他组件：

除了以上几个组件外，Shiro还有几个其他组件：

1、SessionManager ：Shiro为任何应用提供了一个会话编程范式。

2、CacheManager :对Shiro的其他组件提供缓存支持。 





## usage

springrain使用shiro控制权限，配置filterChainDefinitions结合数据库校验权限。

我们在web.xml中配置一个全局过滤器，也就是在springrain配置的是一个spring bean的“shiroFilter“，在这个bean中可以根据访问路径在配置不同的过滤器，其中shiro默认自带的过滤器如下：

   Filter Name | Class
   -|-
   anon | org.apache.shiro.web.filter.authc.AnonymousFilter
   authc | org.apache.shiro.web.filter.authc.FormAuthenticationFilter
   authcBasic | org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
   logout | org.apache.shiro.web.filter.authc.LogoutFilter
   noSessionCreation | org.apache.shiro.web.filter.session.NoSessionCreationFilter
   perms | org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
   port | org.apache.shiro.web.filter.authz.PortFilter
   rest | org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
   roles | org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
   ssl | org.apache.shiro.web.filter.authz.SslFilter
   user | org.apache.shiro.web.filter.authc.UserFilter

我们平时使用就是anno，任何人都可以访问；authc：必须是登录之后才能进行访问，不包括remember me；user：登录用户才可以访问，包含remember me；perms：指定过滤规则，这个一般是扩展使用，不会使用原生的；其中filterChainDefinitions 就是指定过滤规则的，一般公共配置使用配置文件，例如jss css img这些资源文件是不拦截的，相关业务的url配置到数据库，有过滤器查询数据库进行权限判断。
--------------------- 
作者：前行的道路 
来源：CSDN 
原文：https://blog.csdn.net/fanfanzk1314/article/details/72780923 
版权声明：本文为博主原创文章，转载请附上博文链接！

