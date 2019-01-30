# source

## BeanFactory

```java

// 使用Bean名称寻找对应的Bean，并返回Object对象。
Object getBean(String name) throws BeansException

// 使用Bean名称寻找对应的Bean，并判断其类型是否属于给定的类型，如果属于就返回其对象。如果找到的Bean不属于该类型，则抛出NotOfRequiredTypeException异常。
<T> T getBean(String name, Class<T> requiredType) throws BeansException

// 使用Bean类型寻找属于该类型的Bean，如果有，就返回其对象。
<T> T getBean(Class<T> requiredType) throws BeansException

// 使用Bean名称寻找对应的Bean，使用给定的构造函数参数或者工厂方法参数构造对象并返回，会重写Bean定义中的默认参数。
// 该方法只适用于prototype的Bean，默认作用域的Bean不能重写其参数。
Object getBean(String name, Object... args) throws BeansException

// 使用Bean类型寻找属于该类型的Bean，用给定的构造函数参数或工厂方法参数构造对象并返回，会重写Bean定义中的默认参数。
// PS：该方法只适用于prototype的Bean，默认作用域的Bean不能重写其参数。
<T> T getBean(Class<T> requiredType, Object... args) throws BeansException

// 提供对 bean 的检索，看看是否在 IOC 容器有这个名字的 bean
boolean containsBean(String name);

// 根据 bean 名字得到 bean 实例，并同时判断这个 bean 是不是单例
boolean isSingleton(String name) throws NoSuchBeanDefinitionException;
boolean isPrototype(String name) throws NoSuchBeanDefinitionException;
boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;
boolean isTypeMatch(String name, @Nullable Class<?> typeToMatch) throws NoSuchBeanDefinitionException;

// 得到 bean 实例的 Class 类型
@Nullable
Class<?> getType(String name) throws NoSuchBeanDefinitionException;
// 得到 bean 的别名，如果根据别名检索，那么其原名也会被检索出来
String[] getAliases(String name);

```
