# method

## BeanFactory

### getBean

   `<T> T getBean(Class<T> requiredType) throws BeansException;`

   遍历所有bean，构建与给定'requiredType'参数匹配的bean集合。如果结果列表只包含一个bean，则返回它。如果列表包含零bean，则抛出NoSuchBeanDefinitionException。如果列表包含多个匹配的bean，则抛出特定于该问题的BeansException。