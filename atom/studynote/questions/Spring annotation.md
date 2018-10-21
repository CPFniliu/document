```java
 @Controller("userAction")
 @Scope("prototype")
 public class UserAction extends BaseAction<User>{
     // 注入userService
     @Resource(name = "userService")
     private UserService userService;
 }

 @Service("userService")
 public class UserServiceImpl implements UserService {

 }
```

使用@Controller注解标识UserAction之后，就表示要把UserAction交给Spring容器管理，在Spring容器中会存在一个名字为"userAction"的action，这个名字是根据UserAction类名来取的。注意：如果@Controller不指定其value【@Controller】，则默认的bean名字为这个类的类名首字母小写，如果指定value【@Controller(value="UserAction")】或者【@Controller("UserAction")】，则使用value作为bean的名字。
这里的UserAction还使用了@Scope注解，@Scope("prototype")表示将Action的范围声明为原型，可以利用容器的scope="prototype"来保证每一个请求有一个单独的Action来处理，避免struts中Action的线程安全问题。spring 默认scope 是单例模式(scope="singleton")，这样只会创建一个Action对象，每次访问都是同一Action对象，数据不安全，struts2 是要求每次次访问都对应不同的Action，scope="prototype" 可以保证当有请求的时候都创建一个Action对象


### Spring注解
> - 组件类注解

annotation | 含义（要把当前类交给Spring容器管理） | value作用
-|-
@Component | 标准一个普通的spring Bean类。|
@Repository | 标注一个DAO组件类。
@Service | 标注一个业务逻辑组件类。
@Controller | 标注一个控制器组件类。| 表示在Spring容器中会存在一个名字为value的action，如果@Controller不指定其value【@Controller】，则默认的bean名字为这个类的类名首字母小写，

@Scope("prototype") | 表示将Action的范围声明为原型，可以利用容器的scope="prototype"来保证每一个请求有一个单独的Action来处理，避免struts中Action的线程安全问题。spring 默认scope 是单例模式(scope="singleton")，这样只会创建一个Action对象，每次访问都是同一Action对象，数据不安全，struts2 是要求每次次访问都对应不同的Action，scope="prototype" 可以保证当有请求的时候都创建一个Action对象

@Service("userService")注解是告诉Spring，当Spring要创建UserServiceImpl的的实例时，bean的名字必须叫做"userService"，这样当Action需要使用UserServiceImpl的的实例时,就可以由Spring创建好的"userService"，然后注入给Action：在Action只需要声明一个名字叫“userService”的变量来接收由Spring注入的"userService"即可，

@Component、@Repository、@Service、@Controller实质上属于同一类注解，用法相同，功能相同，区别在于标识组件的类型。@Component可以代替@Repository、@Service、@Controller

> - 装配bean时常用的注解
@Autowired：属于Spring 的org.springframework.beans.factory.annotation包下,可用于为类的属性、构造器、方法进行注值
@Resource：不属于spring的注解，而是来自于JSR-250位于java.annotation包下，使用该annotation为目标bean指定协作者Bean。
@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
