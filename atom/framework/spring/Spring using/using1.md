# Bean 配置

## Bean 中 id 和 name的区别 

1. id属性命名必须满足XML的命名规范，因为id其实是XML中就做了限定的。总结起来就相当于一个Java变量的命名：不能以数字，符号打头，不能有空格，如123，?ad,"ab "等都是不规范的，Spring在初始化时就会报错，诸如:
`org.xml.sax.SAXParseException: Attribute value "?ab" of type ID must be a name.`

2. name属性则没有这些限定，你可以使用几乎任何的名称，如?ab,123等，但不能带空格，如"a b"," abc"，，这时，虽然初始化时不会报错，但在getBean()则会报出诸如以下的错误：  
`org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'a b' is defined`

3. 配置文件中不允许出现两个id相同的<bean>，否则在初始化时即会报错，如：
`org.xml.sax.SAXParseException: Attribute value "aa" of type ID must be unique within the document.`  

4. 但配置文件中允许出现两个name相同的<bean>，在用getBean()返回实例时，后面一个Bean被返回,应该是前面那个 <bean>被后面同名的 <bean>覆盖了。有鉴于此，为了避免不经意的同名覆盖的现象，尽量用id属性而不要用name属性。  

5. name属性可以用,隔开指定多个名字，如<bean name="b1,b2,b3">,相当于多个别名，这时通过getBean("a1") getBean("a2") getBean("a3")返回的都是同一个实例（假设是singleton的情况）  

6. 如果id和name都没有指定，则用类全名作为name，如<bean class="com.stamen.BeanLifeCycleImpl">,则你可以通过  
getBean("com.stamen.BeanLifeCycleImpl")返回该实例。  

7. 如果存在多个id和name都没有指定，且实例类都一样的<bean>，如:  
```xml
<bean class="com.stamen.BeanLifeCycleImpl"/>
<bean class="com.stamen.BeanLifeCycleImpl"/>
<bean class="com.stamen.BeanLifeCycleImpl"/>
```
则第一个bean通过getBean("com.stamen.BeanLifeCycleImpl")获得，  
第二个bean通过getBean("com.stamen.BeanLifeCycleImpl#1")获得，  
第三个bean通过getBean("com.stamen.BeanLifeCycleImpl#2")获得，以此类推。  

# [Bean 为Bean注入日期类型](https://blog.csdn.net/u013216156/article/details/78626440)

```xml
<bean id="yMd_hm" class="java.text.SimpleDateFormat">
    <constructor-arg value="yyyy-MM-dd hh:mm"></constructor-arg>
</bean>

<bean class="cn.cpf.test.beans.Student" id="xiaoming" name="student">
    <property name="birthday">
        <bean factory-bean="yMd_hm" factory-method="parse">
            <constructor-arg value="2018-12-5 15:48"></constructor-arg>
        </bean>
    </property>
</bean>
```
